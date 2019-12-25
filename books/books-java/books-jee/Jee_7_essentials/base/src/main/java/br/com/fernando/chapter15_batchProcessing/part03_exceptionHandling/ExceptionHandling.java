package br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_DATA_SOURCE_NAME;
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

import br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.listeners.MyRetryProcessorListener;
import br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.listeners.MyRetryReadListener;
import br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.listeners.MyRetryWriteListener;
import br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.listeners.MySkipProcessorListener;
import br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.listeners.MySkipReadListener;
import br.com.fernando.chapter15_batchProcessing.part03_exceptionHandling.listeners.MySkipWriteListener;
import br.com.fernando.utils.BatchTestHelper;

public class ExceptionHandling {

    // By default, when any batch artifact that is part of a chunk step throws an exception,
    // the job execution ends with a batch status of FAILED. You can override this default behavior
    // for reader, processor, and writer artifacts by configuring exceptions to skip or to retry:
    //
    // The SkipReadListener, SkipProcessListener<T>, and SkipWriteListener<T> interfaces can be implemented to receive control when a skippable exception is thrown.
    // The RetryReadListener, RetryProcessListener<T>, and RetryWriteListener<T> interfaces can be implemented to receive control when a retriable exception is thrown.
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
            war.addMetaInfFiles(EmbeddedResource.add("batch-jobs/myJob.xml", "src/main/resources/chapter15_batchProcessing/part03_exceptionHandling/myJob.xml"));

            war.addClasses( //
                    ExecuteBatchServlet.class, ExecuteBatchEJB.class, BatchTestHelper.class, UtilRecorder.class, //
                    MyInputRecord.class, MyOutputRecord.class, //
                    MyItemReader.class, MyItemProcessor.class, MyItemWriter.class, //
                    MyRetryProcessorListener.class, MyRetryReadListener.class, MyRetryWriteListener.class, //
                    MySkipProcessorListener.class, MySkipReadListener.class, MySkipWriteListener.class //
            );

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
