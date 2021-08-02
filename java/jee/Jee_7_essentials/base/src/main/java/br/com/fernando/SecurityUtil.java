package br.com.fernando;

import static br.com.fernando.Util.TRUST_STORE_PATH_FILE;
import static java.math.BigInteger.ONE;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class SecurityUtil {

    public static void createTrustStoreFile(final Certificate clientCertificate) {

        try (final InputStream in = new FileInputStream(TRUST_STORE_PATH_FILE)) {

            final KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(in, "changeit".toCharArray());

            keyStore.setCertificateEntry("arquillianClientTestCert", clientCertificate);

            keyStore.store(new FileOutputStream(TRUST_STORE_PATH_FILE), "changeit".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static X509Certificate createSelfSignedCertificate(KeyPair keys) {
        try {

            Provider provider = new BouncyCastleProvider();

            Security.addProvider(provider);

            return new JcaX509CertificateConverter() //
                    .setProvider(provider) //
                    .getCertificate( //
                            new X509v3CertificateBuilder( //
                                    new X500Name("CN=lfoo, OU=bar, O=kaz, L=zak, ST=lak, C=UK"), //
                                    ONE, //
                                    new Date(), //
                                    new Date(new Date().getTime() + (1000 * 60 * 60 * 24)), //
                                    new X500Name("CN=lfoo, OU=bar, O=kaz, L=zak, ST=lak, C=UK"), //
                                    SubjectPublicKeyInfo.getInstance(keys.getPublic().getEncoded()) //
                            ).build( //
                                    new JcaContentSignerBuilder("SHA256WithRSA") //
                                            .setProvider(provider) //
                                            .build(keys.getPrivate())));//

        } catch (CertificateException | OperatorCreationException e) {
            throw new IllegalStateException(e);
        }
    }

    public static URL getHostFromCertificate(X509Certificate[] serverCertificateChain, URL existingURL) {
        try {
            URL httpsUrl = new URL( //
                    existingURL.getProtocol(), //
                    getHostFromCertificate(serverCertificateChain), //
                    existingURL.getPort(), //
                    existingURL.getFile() //
            );

            System.out.println("Changing base URL from " + existingURL + " into " + httpsUrl + "\n");

            return httpsUrl;

        } catch (MalformedURLException e) {
            System.out.println("Failure creating HTTPS URL");
            e.printStackTrace();

            System.out.println("FAILED to get CN. Using existing URL: " + existingURL);

            return existingURL;
        }
    }

    public static KeyPair generateRandomRSAKeys() {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
            keyPairGenerator.initialize(2048);

            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String createTempJKSKeyStore(PrivateKey privateKey, X509Certificate certificate) {
        try {
            Path tmpKeyStorePath = Files.createTempFile("trustStore", ".jks");

            createJKSKeyStore(tmpKeyStorePath, "changeit".toCharArray(), privateKey, certificate);

            return tmpKeyStorePath.toString();

        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static void createJKSKeyStore(Path path, char[] password, PrivateKey privateKey, X509Certificate certificate) {
        try {
            KeyStore keyStore = KeyStore.getInstance("jks");
            keyStore.load(null, null);

            keyStore.setEntry("omniKey", new PrivateKeyEntry(privateKey, new Certificate[]{ certificate }), new PasswordProtection(password));

            keyStore.store(new FileOutputStream(path.toFile()), password);
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static X509Certificate[] getCertificateChainFromServer(String host, int port, int timeout) {

        InterceptingX509TrustManager interceptingTrustManager = new InterceptingX509TrustManager();

        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{ interceptingTrustManager }, null);

            try (SSLSocket socket = (SSLSocket) context.getSocketFactory().createSocket(host, port)) {
                socket.setSoTimeout(timeout);
                socket.startHandshake();
            }

        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            e.printStackTrace();
        }

        if (interceptingTrustManager.getX509ServerCertificates().isEmpty()) {
            return null;
        }

        return interceptingTrustManager.getX509ServerCertificates().get(0);
    }

    public static String createTempJKSTrustStore(X509Certificate[] certificates) {
        try {
            Path tmpTrustStorePath = Files.createTempFile("trustStore", ".jks");

            createJKSTrustStore(tmpTrustStorePath, "changeit".toCharArray(), certificates);

            return tmpTrustStorePath.toString();
        } catch (IOException cause) {
            throw new IllegalStateException(cause);
        }
    }

    public static void createJKSTrustStore(Path path, char[] password, X509Certificate[] certificates) {
        try {
            KeyStore trustStore = KeyStore.getInstance("jks");
            trustStore.load(null, null);

            for (int i = 0; i < certificates.length; i++) {
                trustStore.setCertificateEntry("omniCert" + i, certificates[i]);
            }

            trustStore.store(new FileOutputStream(path.toFile()), password);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getHostFromCertificate(X509Certificate[] serverCertificateChain) {
        String[] names = serverCertificateChain[0].getIssuerX500Principal().getName().split(",");

        if (ArrayUtils.isEmpty(names)) {
            throw new IllegalStateException("No CN name found");
        }

        // In the X.500 distinguished name using the format defined in RFC 2253, CN is the first
        // element and represents the host
        String cn = names[0];

        return cn.substring(cn.indexOf('=') + 1).trim();
    }

    public static URL toContainerHttps(URL url) {

        if ("https".equals(url.getProtocol())) {
            return url;
        }

        String javaEEServer = System.getProperty("javaEEServer");

        // String protocol, String host, int port, String file

        if ("glassfish-remote".equals(javaEEServer) || "payara-remote".equals(javaEEServer)) {

            try {
                URL httpsUrl = new URL("https", url.getHost(), 8181, url.getFile());

                System.out.println("Changing base URL from " + url + " into " + httpsUrl);

                return httpsUrl;

            } catch (MalformedURLException e) {
                System.out.println("Failure creating HTTPS URL");
                e.printStackTrace();
            }

        } else {
            if (javaEEServer == null) {
                System.out.println("javaEEServer not specified");
            } else {
                System.out.println(javaEEServer + " not supported");
            }
        }

        return null;
    }

    public static class InterceptingX509TrustManager implements X509TrustManager {

        private List<X509Certificate[]> x509ClientCertificates = new ArrayList<>();

        private List<X509Certificate[]> x509ServerCertificates = new ArrayList<>();

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            x509ClientCertificates.add(chain);
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            x509ServerCertificates.add(chain);
        }

        /**
         * Returns the client certificates that have been collected
         * 
         * @return the client certificates
         */
        public List<X509Certificate[]> getX509ClientCertificates() {
            return x509ClientCertificates;
        }

        /**
         * Returns the server certificates that have been collected
         * 
         * @return the server certificates
         */
        public List<X509Certificate[]> getX509ServerCertificates() {
            return x509ServerCertificates;
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }
}
