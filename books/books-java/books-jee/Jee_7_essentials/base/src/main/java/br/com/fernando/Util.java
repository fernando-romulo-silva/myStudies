package br.com.fernando;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.KeyStore;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;

public class Util {

    private static final String TARGET_GLASSFISH = "target/glassfish";

    private static final String CONFIG = "/domains/domain1/config/";

    private static final int BUFFER_SIZE = 4096;

    public static final int HTTP_PORT = 18080;

    public static final int HTTPS_PORT = 19090;

    public static final String APP_FILE_TARGET = "target";

    public static final String EMBEDDED_JEE_TEST_APP_NAME = "embeddedJeeContainerTest";

    public static final String EMBEDDED_JEE_TEST_DATA_SOURCE_NAME = EMBEDDED_JEE_TEST_APP_NAME + "DS";

    public static final String CERTIFICATE_ALIAS = "s1as";

    public static final String KEY_STORE_PATH_FILE = TARGET_GLASSFISH + CONFIG + "keystore.jks";

    public static final String TRUST_STORE_PATH_FILE = TARGET_GLASSFISH + CONFIG + "cacerts.jks";

    public static final String SSL_PASSWORD = "changeit";

    public static final String HSQLDB_JDBC_DRIVER = "org.hsqldb.jdbcDriver";

    public static final String HSQLDB_XA_JDBC_DRIVER = "org.hsqldb.jdbc.pool.JDBCXADataSource";

    public static final String DATA_BASE_SERVER_LOGIN = "root";

    public static final String DATA_BASE_SERVER_PASSWORD = "root";

    public static final int DATA_BASE_SERVER_PORT = 2527;

    // ==================================================================================================================================================================
    public static void startVariables() throws Exception {

        System.setProperty(MyEmbeddedJeeContainer.EMBEDDED_JEE_INSTALL_ROOT, TARGET_GLASSFISH);

        unzip("src/main/resources/glassfish.zip", "target");
    }

    private static int unzip(final String zipFilePath, final String destDirectory) {
        final File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        try (final ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));) {
            ZipEntry entry = zipIn.getNextEntry();
            // iterates over entries in the zip file
            while (entry != null) {
                final String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // if the entry is a file, extracts it
                    try (final BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));) {
                        final byte[] bytesIn = new byte[BUFFER_SIZE];
                        int read = 0;
                        while ((read = zipIn.read(bytesIn)) != -1) {
                            bos.write(bytesIn, 0, read);
                        }
                    }
                } else {
                    // if the entry is a directory, make the directory
                    final File dir = new File(filePath);
                    dir.mkdir();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }

        return 0;
    }

    // ==================================================================================================================================================================
    public static SSLContext createSslContex(final String keyStorePathFile, final String trustStorePathFile, final String password) throws Exception {
        // First initialize the key and trust material
        final KeyStore ksKeys = KeyStore.getInstance(KeyStore.getDefaultType());
        ksKeys.load(new FileInputStream(keyStorePathFile), password.toCharArray());

        final KeyStore ksTrust = KeyStore.getInstance("JKS");
        ksTrust.load(new FileInputStream(trustStorePathFile), password.toCharArray());

        final KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ksKeys, password.toCharArray());

        final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ksTrust);

        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return sslContext;
    }

    // ==================================================================================================================================================================
    public static void downVariables() {

        File[] files = { //
                new File(APP_FILE_TARGET + "/" + EMBEDDED_JEE_TEST_APP_NAME + ".ear"), //
                new File(APP_FILE_TARGET + "/" + EMBEDDED_JEE_TEST_APP_NAME + ".war"), //
                new File(APP_FILE_TARGET + "/contextInitializer.jar"), //
                new File(TARGET_GLASSFISH), //
        };

        try {
            for (final File file : files) {
                if (file != null && file.exists()) {
                    if (file.isDirectory()) {
                        deleteFolder(Paths.get(file.getAbsolutePath()));
                    } else {
                        Files.delete(file.toPath());
                    }
                }
            }

            deleteFolder(new File(TARGET_GLASSFISH).toPath());

        } catch (final Exception ex) {
            System.out.println(ex);
        }
    }

    private static int deleteFolder(final Path directory) {

        try {

            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }

        return 0;
    }

    // ==================================================================================================================================================================
    public static void executeRequestHttp(final Class<? extends HttpRequestBase> clazz, final String url) throws IOException, ClientProtocolException {

        final HttpClient httpClient = HttpClientBuilder.create().build();

        final HttpRequestBase request = createRequest(clazz, url);

        final HttpResponse response = httpClient.execute(request);

        System.out.println(response.getStatusLine());
    }

    public static void executeRequestHttp(final String name, final String password, final Class<? extends HttpRequestBase> clazz, final String url) throws IOException, ClientProtocolException {

        final HttpClientContext context = createLoginBasicContext(name, password, HTTP_PORT, "http");

        final HttpClient httpClient = HttpClientBuilder.create().build();

        final HttpRequestBase request = createRequest(clazz, url);

        final HttpResponse response = httpClient.execute(request, context);

        System.out.println("name: " + name + ", return code: " + response.getStatusLine());
    }

    public static void executeRequestHttps(final String name, final String password, final Class<? extends HttpRequestBase> clazz, final String url) throws Exception {

        final HttpClientContext context = createLoginBasicContext(name, password, HTTPS_PORT, "https");

        final HttpClient httpClient = HttpClientBuilder.create().setSSLContext(createSslContex(KEY_STORE_PATH_FILE, TRUST_STORE_PATH_FILE, SSL_PASSWORD)).build();

        final HttpRequestBase request = createRequest(clazz, url);

        final HttpResponse response = httpClient.execute(request, context);

        System.out.println("name: " + name + ", return code: " + response.getStatusLine());
    }

    // ==================================================================================================================================================================
    public static HttpRequestBase createRequest(final Class<? extends HttpRequestBase> clazz, final String url) {
        final HttpRequestBase request;

        if (clazz == HttpPost.class) {
            request = new HttpPost(url);
        } else if (clazz == HttpDelete.class) {
            request = new HttpDelete(url);
        } else if (clazz == HttpPut.class) {
            request = new HttpPut(url);
        } else if (clazz == HttpTrace.class) {
            request = new HttpTrace(url);
        } else if (clazz == HttpOptions.class) {
            request = new HttpOptions(url);
        } else {
            request = new HttpGet(url);
        }

        return request;
    }

    // ==================================================================================================================================================================
    public static HttpClientContext createLoginBasicContext(final String name, final String password, final int port, final String protocol) {
        final CredentialsProvider credsProvider = new BasicCredentialsProvider();
        final HttpHost targetHost = new HttpHost("localhost", port, protocol);
        credsProvider.setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()), new UsernamePasswordCredentials(name, password));

        final AuthCache authCache = new BasicAuthCache();
        final BasicScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);

        // Add AuthCache to the execution context
        final HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credsProvider);
        context.setAuthCache(authCache);

        // make sure cookies is turn on
        CookieHandler.setDefault(new CookieManager());
        return context;
    }

    // ==================================================================================================================================================================
    public static Session connectToServerWebSockets(final Class<?> endpoint, final String uriPart) throws DeploymentException, IOException, URISyntaxException {

        final WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        final URI uri = new URI("ws://" //
                + "localhost" //
                + ":" //
                + HTTP_PORT //
                + "/" //
                + EMBEDDED_JEE_TEST_APP_NAME //
                + "/" //
                + uriPart);

        System.out.println("Connecting to: " + uri);
        return container.connectToServer(endpoint, uri);
    }

    public static String sourceToXMLString(final Source result) {
        String xmlResult = null;
        try {
            final TransformerFactory factory = TransformerFactory.newInstance();
            final Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            final OutputStream out = new ByteArrayOutputStream();
            final StreamResult streamResult = new StreamResult();
            streamResult.setOutputStream(out);
            transformer.transform(result, streamResult);
            xmlResult = streamResult.getOutputStream().toString();
        } catch (final TransformerException e) {
            e.printStackTrace();
        }
        return xmlResult;
    }
}
