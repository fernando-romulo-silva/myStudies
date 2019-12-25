package br.com.fernando.chapter15_batchProcessing.part13_csvDatabase;

import static br.com.fernando.Util.EMBEDDED_JEE_TEST_DATA_SOURCE_NAME;
import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HSQLDB_JDBC_DRIVER;
import static br.com.fernando.Util.HSQLDB_XA_JDBC_DRIVER;
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
import org.myembedded.jeecontainer.resources.MyEmbeddedDataSource;
import org.myembedded.jeecontainer.resources.MyEmbeddedBatchConfiguration.MyEmbeddedBatchConfigurationBuilder;
import org.myembedded.jeecontainer.resources.MyEmbeddedDataSource.MyEmbeddedDataSourceBuilder;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.utils.BatchTestHelper;

public class CsvDatabase {

    public static void main(final String[] args) throws Exception {
        startVariables();

        try ( //
                final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer(); //
                final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms(); //
        ) {

            // ------------ Database -----------------------------------------------------------
            final String batchDataBaseName = EMBEDDED_JEE_TEST_APP_NAME + "DB";
            final String batchDataBaseUrl = "jdbc:hsqldb:hsql://localhost:" + DATA_BASE_SERVER_PORT + "/" + batchDataBaseName;
            embeddedRdbms.addDataBase(batchDataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
            embeddedRdbms.addSchema("APP", batchDataBaseName);

            final String sysDataBaseName = EMBEDDED_JEE_TEST_APP_NAME + "SysDB";
            final String sysDataBaseUrl = "jdbc:hsqldb:hsql://localhost:" + DATA_BASE_SERVER_PORT + "/" + sysDataBaseName;
            embeddedRdbms.addDataBase(sysDataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);

            embeddedRdbms.start(DATA_BASE_SERVER_PORT);
            embeddedRdbms.executeFileScripts(batchDataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD, "src/main/resources/chapter15_batchProcessing/schema-hsqldb.sql");

            // ------------ Packages -----------------------------------------------------------
            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            war.addMetaInfFiles(EmbeddedResource.add("batch-jobs/myJob.xml", "src/main/resources/chapter15_batchProcessing/part13_csvDatabase/myJob.xml"));
            war.addMetaInfFiles(EmbeddedResource.add("persistence.xml", "src/main/resources/chapter15_batchProcessing/part13_csvDatabase/persistence.xml"));
            war.addMetaInfFiles(EmbeddedResource.add("create.sql", "src/main/resources/chapter15_batchProcessing/part13_csvDatabase/create.sql"));
            war.addMetaInfFiles(EmbeddedResource.add("drop.sql", "src/main/resources/chapter15_batchProcessing/part13_csvDatabase/drop.sql"));
            war.addMetaInfFiles(EmbeddedResource.add("mydata.csv", "src/main/resources/chapter15_batchProcessing/part13_csvDatabase/mydata.csv"));

            war.addClasses(//
                    ExecuteBatchServlet.class, ExecuteBatchEJB.class, BatchTestHelper.class, // 
                    MyItemProcessor.class, MyItemReader.class, MyItemWriter.class, Person.class //
            );

            final File appFile = war.exportToFile(APP_FILE_TARGET);

            // ------------ jee Server -----------------------------------------------------------
            // batch DS
            final MyEmbeddedDataSource batchDataSource = new MyEmbeddedDataSourceBuilder(EMBEDDED_JEE_TEST_DATA_SOURCE_NAME, batchDataBaseUrl) //
                    .withDataBaseDriverClass(HSQLDB_JDBC_DRIVER) //
                    .withXaTransaction(true) //
                    .build();
            embeddedJeeServer.addDataSource(batchDataSource);

            // Sys DS
            final MyEmbeddedDataSource sysDataSource = new MyEmbeddedDataSourceBuilder(EMBEDDED_JEE_TEST_APP_NAME + "SysDS", sysDataBaseUrl) //
                    .withDataBaseDataSourceClass(HSQLDB_XA_JDBC_DRIVER) //
                    .withXaTransaction(true) //
                    .build();
            embeddedJeeServer.addDataSource(sysDataSource);

            final MyEmbeddedBatchConfiguration batchConfiguration = new MyEmbeddedBatchConfigurationBuilder("name") //
                    .withDataSourceLookupName(batchDataSource.getJndi()) //
                    .build();
            embeddedJeeServer.configBatchJob(batchConfiguration);

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, appFile.getAbsolutePath());

            // ------------ Client -----------------------------------------------------------
            final HttpClient httpClient = HttpClientBuilder.create().build();
            final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/Servlet"));

            System.out.println(response);

            Thread.sleep(50000); // 5 seconds only for the server finishs its jobs...

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();
    }
}
