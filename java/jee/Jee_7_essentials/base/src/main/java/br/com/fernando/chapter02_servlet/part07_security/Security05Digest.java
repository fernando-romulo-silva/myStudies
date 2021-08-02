package br.com.fernando.chapter02_servlet.part07_security;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.CERTIFICATE_ALIAS;
import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_DATA_SOURCE_NAME;
import static br.com.fernando.Util.HSQLDB_JDBC_DRIVER;
import static br.com.fernando.Util.HTTPS_PORT;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.KEY_STORE_PATH_FILE;
import static br.com.fernando.Util.SSL_PASSWORD;
import static br.com.fernando.Util.TRUST_STORE_PATH_FILE;
import static br.com.fernando.Util.createSslContex;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.jeecontainer.resources.MyEmbeddedDataSource;
import org.myembedded.jeecontainer.resources.MyEmbeddedDataSource.MyEmbeddedDataSourceBuilder;
import org.myembedded.jeecontainer.resources.MyEmbeddedJeeRealmJdbc;
import org.myembedded.jeecontainer.resources.MyEmbeddedJeeRealmJdbc.MyEmbeddedJeeRealmJdbcBuilder;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;
import org.myembedded.rdbms.MyEmbeddedRdbms;

public class Security05Digest {

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

        try ( //
                final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer(); //
                final MyEmbeddedRdbms myEmbeddedRdbms = new MyEmbeddedRdbms(); //
        ) {

            // ------------ War-----------------------------------------------------------------
            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(SecureServlet.class);
            // web inf files
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter02_servlet/part07_security/web_Security05Digest.xml"));
            war.addWebInfFiles(EmbeddedResource.add("glassfish-web.xml", "src/main/resources/chapter02_servlet/part07_security/glassfish-web05Digest.xml"));
            // export
            final File warFile = war.exportToFile(APP_FILE_TARGET);

            // ------------ Database -----------------------------------------------------------
            final String dataBaseName = EMBEDDED_JEE_TEST_APP_NAME + "DB";
            final String dataBaseUrl = "jdbc:hsqldb:hsql://localhost:" + DATA_BASE_SERVER_PORT + "/" + dataBaseName;
            myEmbeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
            myEmbeddedRdbms.start(DATA_BASE_SERVER_PORT);
            myEmbeddedRdbms.executeFileScripts(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD, "src/main/resources/chapter02_servlet/part07_security/realm.sql");

            // ------------ JEE Container ------------------------------------------------------
            final MyEmbeddedDataSource dataSource = new MyEmbeddedDataSourceBuilder(EMBEDDED_JEE_TEST_DATA_SOURCE_NAME, dataBaseUrl)//
                    .withCredential(DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD) //
                    .withDataBaseDriverClass(HSQLDB_JDBC_DRIVER) //
                    .build();

            embeddedJeeServer.addDataSource(dataSource);

            final MyEmbeddedJeeRealmJdbc realm = new MyEmbeddedJeeRealmJdbcBuilder("myRealm", dataSource.getJndi()) //
                    .withDigestAlgorithm("SHA-256") //
                    .withEncoding("Base64") //
                    .withMethodDigest(true) //
                    .build();

            embeddedJeeServer.addJdbcRealm(realm);

            embeddedJeeServer.start(HTTP_PORT, HTTPS_PORT, CERTIFICATE_ALIAS, SSL_PASSWORD, KEY_STORE_PATH_FILE, SSL_PASSWORD, TRUST_STORE_PATH_FILE);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // ------------ Client -------------------------------------------------------------

            final String url = "https://localhost:" + HTTPS_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/SecureServlet";

            final CredentialsProvider credsProvider = new BasicCredentialsProvider();
            final HttpHost targetHost = new HttpHost("localhost", HTTPS_PORT, "https");
            credsProvider.setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()), new UsernamePasswordCredentials("root", "AIEM+LlNb8ucXeSE077EGHYgs+KHblmquQ2FL+Dxj7Y="));

            final AuthCache authCache = new BasicAuthCache();
            authCache.put(targetHost, new BasicScheme());

            // Add AuthCache to the execution context
            final HttpClientContext context = HttpClientContext.create();
            context.setCredentialsProvider(credsProvider);
            context.setAuthCache(authCache);

            final HttpClient httpClient = HttpClientBuilder.create().setSSLContext(createSslContex(KEY_STORE_PATH_FILE, TRUST_STORE_PATH_FILE, SSL_PASSWORD)).build();
            final HttpResponse response = httpClient.execute(targetHost, new HttpGet(url), context);

            System.out.println(response);

            // access the browser
            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
