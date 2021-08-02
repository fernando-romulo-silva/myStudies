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
import java.math.BigInteger;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Set;

import javax.security.auth.x500.X500Principal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Security07ClientcertJCE {

    // ==================================================================================================================================================================
    @WebServlet(urlPatterns = { "/BouncyServlet" })
    public static class BouncyServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            BouncyCastleProvider provider = new BouncyCastleProvider();
            provider.put("CertificateFactory.X.509", MyJCECertificateFactory.class.getName());

            // Installs the JCE provider, return -1 if the provider was not added because it is  already installed.
            int pos = Security.insertProviderAt(provider, 1);

            // Returns the position of the JCE provider, this should be 1.
            response.getWriter().print("pos:" + pos);
        }

        @Override
        protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        }
    }

    @WebServlet(urlPatterns = { "/SecureServlet" })
    public static class SecureServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            response.getWriter().print("principal " + request.getUserPrincipal() + " in role g1:" + request.isUserInRole("g1"));
        }

    }

    public static class MyJCECertificateFactory extends CertificateFactory {

        @Override
        public Certificate engineGenerateCertificate(InputStream in) throws CertificateException {
            Certificate certificate = super.engineGenerateCertificate(in);

            if (certificate instanceof X509Certificate == false) {
                return certificate;
            }

            return new MyJCEX509Certificate((X509Certificate) certificate);
        }

    }

    public static class MyJCEX509Certificate extends X509Certificate {

        private final X509Certificate certificate;

        public MyJCEX509Certificate(X509Certificate certificate) {
            this.certificate = certificate;
        }

        @Override
        public X500Principal getSubjectX500Principal() {

            X500Principal principal = certificate.getSubjectX500Principal();

            if ("C=UK,ST=lak,L=zak,O=kaz,OU=bar,CN=lfoo".equals(principal.getName())) {
                return new X500Principal("CN=user01");
            }

            return principal;
        }

        @Override
        public Principal getSubjectDN() {

            Principal principal = certificate.getSubjectDN();

            if ("CN=lfoo,OU=bar,O=kaz,L=zak,ST=lak,C=UK".equals(principal.getName())) {
                // Doesn't have to be X500 but keep it for simplicity
                return new X500Principal("CN=user01");
            }

            return principal;
        }

        @Override
        public boolean hasUnsupportedCriticalExtension() {
            return certificate.hasUnsupportedCriticalExtension();
        }

        @Override
        public Set<String> getCriticalExtensionOIDs() {
            return certificate.getCriticalExtensionOIDs();
        }

        @Override
        public Set<String> getNonCriticalExtensionOIDs() {
            return certificate.getCriticalExtensionOIDs();
        }

        @Override
        public byte[] getExtensionValue(String oid) {
            return certificate.getExtensionValue(oid);
        }

        @Override
        public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
            certificate.checkValidity();

        }

        @Override
        public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
            certificate.checkValidity(date);
        }

        @Override
        public int getVersion() {
            return certificate.getVersion();
        }

        @Override
        public BigInteger getSerialNumber() {
            return certificate.getSerialNumber();
        }

        @Override
        public Principal getIssuerDN() {
            return certificate.getIssuerDN();
        }

        @Override
        public Date getNotBefore() {
            return certificate.getNotBefore();
        }

        @Override
        public Date getNotAfter() {
            return certificate.getNotAfter();
        }

        @Override
        public byte[] getTBSCertificate() throws CertificateEncodingException {
            return certificate.getTBSCertificate();
        }

        @Override
        public byte[] getSignature() {
            return certificate.getSignature();
        }

        @Override
        public String getSigAlgName() {
            return certificate.getSigAlgName();
        }

        @Override
        public String getSigAlgOID() {
            return certificate.getSigAlgOID();
        }

        @Override
        public byte[] getSigAlgParams() {
            return certificate.getSigAlgParams();
        }

        @Override
        public boolean[] getIssuerUniqueID() {
            return certificate.getIssuerUniqueID();
        }

        @Override
        public boolean[] getSubjectUniqueID() {
            return certificate.getSubjectUniqueID();
        }

        @Override
        public boolean[] getKeyUsage() {
            return certificate.getKeyUsage();
        }

        @Override
        public int getBasicConstraints() {
            return certificate.getBasicConstraints();
        }

        @Override
        public byte[] getEncoded() throws CertificateEncodingException {
            return certificate.getEncoded();
        }

        @Override
        public void verify(PublicKey key) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
            certificate.verify(key);

        }

        @Override
        public void verify(PublicKey key, String sigProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
            certificate.verify(key, sigProvider);

        }

        @Override
        public String toString() {
            return certificate.toString();
        }

        @Override
        public PublicKey getPublicKey() {
            return certificate.getPublicKey();
        }
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(SecureServlet.class, BouncyServlet.class, MyJCECertificateFactory.class, MyJCEX509Certificate.class);

            // web inf files
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter02_servlet/part07_security/web_Security07ClientcertJCE.xml"));
            war.addWebInfFiles(EmbeddedResource.add("glassfish-web.xml", "src/main/resources/chapter02_servlet/part07_security/glassfish-web07ClientcertJCE.xml"));
            // export
            final File warFile = war.exportToFile(APP_FILE_TARGET);

            // Only test TLS v1.2 for now
            System.setProperty("jdk.tls.client.protocols", "TLSv1.2");

            // enable SSL Debug
            // System.setProperty("javax.net.debug", "ssl:handshake");

            // Enable to get detailed logging about the SSL handshake on the server
            // embeddedJeeServer.addContainerSystemProperty("javax.net.debug", "ssl:handshake");

            URL baseHttps = new URL("https://localhost:" + HTTPS_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME);

            // ============================================================================================
            // Generate a Private/Public key pair for the client
            final KeyPair keyPair = generateRandomRSAKeys();
            // Create a certificate containing the public key and signed with the private key
            final X509Certificate certificate = createSelfSignedCertificate(keyPair);
            // =============================================================================================

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
            // Create a new local key store containing the client private key and the certificate
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
                clientTrustStorePathFile = null;
                System.out.println("Could not obtain certificates from server. Continuing without custom truststore");
            }

            final HttpHost targetHost = new HttpHost("localhost", HTTPS_PORT, "https");

            final HttpClient httpClient = HttpClientBuilder.create().setSSLContext(createSslContex(clientKeyStorePathFile, clientTrustStorePathFile, SSL_PASSWORD)).build();
            final HttpResponse responseA = httpClient.execute(targetHost, new HttpGet(baseHttps.toString() + "/SecureServlet"));
            System.out.println(responseA);

            final HttpResponse responseB = httpClient.execute(targetHost, new HttpDelete(baseHttps.toString() + "/BouncyServlet"));
            System.out.println(responseB);

            System.out.println("====================================================================================================================================");

            // access the browser
            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
