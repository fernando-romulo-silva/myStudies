package org.nandao.cap09.p03UsingTheExtendedSSLSessionInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AlgorithmConstraints;
import java.security.AlgorithmParameters;
import java.security.CryptoPrimitive;
import java.security.Key;
import java.security.interfaces.RSAKey;
import java.util.Set;

import javax.net.ssl.ExtendedSSLSession;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import org.nandao.cap06.p01ManagingSimpleFiles.Test;

// The javax.net.ssl package provides a series of classes used to effect secure
// socket communication. Improvements introduced in Java 7 include the addition of the
// ExtendedSSLSession interface, which can be used to determine the specific local and peer
// supported signature algorithms that are used. In addition, when an SSLSession is created,
// an endpoint identification algorithm can be used to ensure that the host computer's address
// matches that of the certificate. This algorithm is accessible through the SSLParameters class.
public class TestServer {

    // keytool -genkey -keystore mySrvKeystore -keyalg RSA
    // keytool -exportcert -alias YOUR_ALIAS -keystore YOUR_KEY_STORE -file ${JAVA_HOME}/jre/lib/security/cacerts
    // keytool -exportcert -keystore mySrvKeystore -file C:/vb/Java/jdk1.7.0_51/jre/lib/security/cacerts

    public static class SimpleConstraints implements AlgorithmConstraints {

        public boolean permits(Set<CryptoPrimitive> primitives, String algorithm, AlgorithmParameters parameters) {
            return permits(primitives, algorithm, null, parameters);
        }

        public boolean permits(Set<CryptoPrimitive> primitives, Key key) {
            return permits(primitives, null, key, null);
        }

        public boolean permits(Set<CryptoPrimitive> primitives, String algorithm, Key key, AlgorithmParameters parameters) {
            if (algorithm == null)
                algorithm = key.getAlgorithm();
            if (algorithm.indexOf("RSA") == -1)
                return false;
            if (key != null) {
                RSAKey rsaKey = (RSAKey) key;
                int size = rsaKey.getModulus().bitLength();
                if (size < 2048)
                    return false;
            }
            return true;
        }
    }

    public static void main(String[] args) throws Exception {

        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        System.setProperty("javax.net.ssl.trustStore", pathSource + "mySrvKeystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");

        SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(9999);
        System.out.println("Waiting for a client ...");
        SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();

        SSLParameters parameters = sslSocket.getSSLParameters();
        parameters.setAlgorithmConstraints(new SimpleConstraints());
        String endPoint = parameters.getEndpointIdentificationAlgorithm();

        System.out.println("End Point: " + endPoint);

        System.out.println("Local Supported Signature  Algorithms");

        if (sslSocket.getSession() instanceof ExtendedSSLSession) {

            final ExtendedSSLSession extendedSSLSession = (ExtendedSSLSession) sslSocket.getSession();

            String algorithms[] = extendedSSLSession.getLocalSupportedSignatureAlgorithms();

            for (String algorithm : algorithms) {
                System.out.println("Algorithm: " + algorithm);
            }
        }

        System.out.println("Peer Supported Signature Algorithms");

        if (sslSocket.getSession() instanceof ExtendedSSLSession) {

            final String algorithms[] = ((ExtendedSSLSession) sslSocket.getSession()).getPeerSupportedSignatureAlgorithms();

            for (String algorithm : algorithms) {
                System.out.println("Algorithm: " + algorithm);
            }
        }

        InputStream inputstream = sslSocket.getInputStream();
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

        String stringline = null;
        while ((stringline = bufferedreader.readLine()) != null) {
            System.out.println(stringline);
            System.out.flush();
        }
    }

}
