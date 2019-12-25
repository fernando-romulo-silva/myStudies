package br.com.fernando.chapter02_servlet.part07_security;

import static br.com.fernando.SecurityUtil.createSelfSignedCertificate;
import static br.com.fernando.SecurityUtil.createTempJKSKeyStore;
import static br.com.fernando.SecurityUtil.createTempJKSTrustStore;
import static br.com.fernando.SecurityUtil.generateRandomRSAKeys;
import static br.com.fernando.SecurityUtil.getCertificateChainFromServer;
import static br.com.fernando.SecurityUtil.getHostFromCertificate;
import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.CERTIFICATE_ALIAS;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTPS_PORT;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.KEY_STORE_PATH_FILE;
import static br.com.fernando.Util.SSL_PASSWORD;
import static br.com.fernando.Util.TRUST_STORE_PATH_FILE;
import static br.com.fernando.Util.createSslContex;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Security06Clientcert {

    // ==================================================================================================================================================================
    @WebServlet(urlPatterns = { "/SecureServlet" })
    public static class SecureServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.getWriter().print("my GET");
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.getWriter().print("my POST");
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(SecureServlet.class);

            // web inf files
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter02_servlet/part07_security/web_Security06Clientcert.xml"));
            war.addWebInfFiles(EmbeddedResource.add("glassfish-web.xml", "src/main/resources/chapter02_servlet/part07_security/glassfish-web06Clientcert.xml"));
            // export
            final File warFile = war.exportToFile(APP_FILE_TARGET);

            // Only test TLS v1.2 for now
            System.setProperty("jdk.tls.client.protocols", "TLSv1.2");

            // enable SSL Debug
            // System.setProperty("javax.net.debug", "ssl:handshake");

            // Enable to get detailed logging about the SSL handshake on the server
            // embeddedJeeServer.addContainerSystemProperty("javax.net.debug", "ssl:handshake");

            URL baseHttps = new URL("https://localhost:" + HTTPS_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/SecureServlet");

            // ============================================================================================
            // Generate a Private/Public key pair for the client
            final KeyPair keyPair = generateRandomRSAKeys();
            // Create a certificate containing the client public key and signed with the private key
            final X509Certificate certificate = createSelfSignedCertificate(keyPair);
            //
            // =============================================================================================
            // Store the certificate on the server
            //  
            try (final InputStream in = new FileInputStream(TRUST_STORE_PATH_FILE)) {
                final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(in, "changeit".toCharArray());

                keyStore.setCertificateEntry("arquillianClientTestCert", certificate);

                keyStore.store(new FileOutputStream(TRUST_STORE_PATH_FILE), SSL_PASSWORD.toCharArray());
            } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
                throw new IllegalStateException(e);
            }

            embeddedJeeServer.start(HTTP_PORT, HTTPS_PORT, CERTIFICATE_ALIAS, SSL_PASSWORD, KEY_STORE_PATH_FILE, SSL_PASSWORD, TRUST_STORE_PATH_FILE);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // ------------------------------------------------------------------------------------------------------------------------------
            //
            // Create a new local key store containing the private key and the certificate
            final String clientKeyStorePathFile = createTempJKSKeyStore(keyPair.getPrivate(), certificate);

            final String clientTrustStorePathFile;

            // get certficate from server
            final X509Certificate[] serverCertificateChain = getCertificateChainFromServer("localhost", HTTPS_PORT, 15000);

            if (!ArrayUtils.isEmpty(serverCertificateChain)) {

                System.out.println("Obtained certificate from server. Storing it in client trust store");

                clientTrustStorePathFile = createTempJKSTrustStore(serverCertificateChain);

                System.out.println("Reading trust store from: " + clientTrustStorePathFile);

                // If the use.cnHost property is we try to extract the host from the server
                // certificate and use exactly that host for our requests.
                // This is needed if a server is listening to multiple host names, for instance
                // localhost and example.com. If the certificate is for example.com, we can't
                // localhost for the request, as that will not be accepted.
                if (System.getProperty("use.cnHost") != null) {
                    System.out.println("use.cnHost set. Trying to grab CN from certificate and use as host for requests.");
                    baseHttps = getHostFromCertificate(serverCertificateChain, baseHttps);
                }
            } else {
                System.out.println("Could not obtain certificates from server. Continuing without custom truststore");
                clientTrustStorePathFile = null;
            }

            final HttpHost targetHost = new HttpHost("localhost", HTTPS_PORT, "https");

            final HttpClient httpClient = HttpClientBuilder.create().setSSLContext(createSslContex(clientKeyStorePathFile, clientTrustStorePathFile, SSL_PASSWORD)).build();
            final HttpResponse response = httpClient.execute(targetHost, new HttpGet(baseHttps.toString()));

            System.out.println(response);

            System.out.println("====================================================================================================================================");

            // access the browser
            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
