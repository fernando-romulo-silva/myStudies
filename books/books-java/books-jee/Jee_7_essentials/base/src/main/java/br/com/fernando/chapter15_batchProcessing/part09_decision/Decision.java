package br.com.fernando.chapter15_batchProcessing.part09_decision;

import static br.com.fernando.Util.EMBEDDED_JEE_TEST_DATA_SOURCE_NAME;
import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HSQLDB_JDBC_DRIVER;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.jeecontainer.resources.MyEmbeddedBatchConfiguration;
import org.myembedded.jeecontainer.resources.MyEmbeddedBatchConfiguration.MyEmbeddedBatchConfigurationBuilder;
import org.myembedded.jeecontainer.resources.MyEmbeddedDataSource;
import org.myembedded.jeecontainer.resources.MyEmbeddedDataSource.MyEmbeddedDataSourceBuilder;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.utils.BatchTestHelper;

public class Decision {

    //
    // A decision execution element provides a customized way of determining sequencing among steps, flows, and splits.
    //
    // Four transition elements are defined to direct job execution sequence or to terminate job execution:
    //
    // * next : Directs execution flow to the next execution element.
    //
    // * fail : Causes a job to end with a FAILED batch status.
    //
    // * end : Causes a job to end with a COMPLETED batch status.
    //
    // * stop : Causes a job to end with a STOPPED batch status.
    //
    // The decision uses any of the transition elements (you can use any text) to select the next transition, please look at src/main/resources/chapter15_batchProcessing/part09_decision/myJob.xml
    //
    //
    // ==========================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try ( //
                final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer(); //
                final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms(); //
        ) {

            // ------------ Database -----------------------------------------------------------
            final String dataBaseName = EMBEDDED_JEE_TEST_APP_NAME + "DB";
            final String dataBaseUrl = "jdbc:hsqldb:hsql://localhost:" + DATA_BASE_SERVER_PORT + "/" + dataBaseName;
            embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
            embeddedRdbms.addSchema("APP", dataBaseName);

            embeddedRdbms.start(DATA_BASE_SERVER_PORT);
            embeddedRdbms.executeFileScripts(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD, "src/main/resources/chapter15_batchProcessing/schema-hsqldb.sql");

            // ------------ Packages -----------------------------------------------------------
            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            war.addMetaInfFiles(EmbeddedResource.add("batch-jobs/myJob.xml", "src/main/resources/chapter15_batchProcessing/part09_decision/myJob.xml"));

            war.addClasses( //
                    ExecuteBatchServlet.class, ExecuteBatchEJB.class, BatchTestHelper.class, //
                    MyDecider.class, MyBatchlet1.class, MyBatchlet2.class, MyBatchlet3.class);

            final File appFile = war.exportToFile(APP_FILE_TARGET);

            // ------------ jee Server -----------------------------------------------------------
            final MyEmbeddedDataSource dataSource = new MyEmbeddedDataSourceBuilder(EMBEDDED_JEE_TEST_DATA_SOURCE_NAME, dataBaseUrl) //
                    .withDataBaseDriverClass(HSQLDB_JDBC_DRIVER) //
                    .build();
            embeddedJeeServer.addDataSource(dataSource);

            final MyEmbeddedBatchConfiguration batchConfiguration = new MyEmbeddedBatchConfigurationBuilder("name") //
                    .withDataSourceLookupName(dataSource.getJndi()) //
                    .build();
            embeddedJeeServer.configBatchJob(batchConfiguration);

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, appFile.getAbsolutePath());

            // ------------ Client -----------------------------------------------------------
            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/Servlet"));

            System.out.println(response);

            Thread.sleep(5000); // 5 seconds only for the server finishs its jobs...

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }

}
