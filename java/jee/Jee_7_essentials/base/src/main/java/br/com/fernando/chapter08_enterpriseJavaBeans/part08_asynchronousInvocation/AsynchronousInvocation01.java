package br.com.fernando.chapter08_enterpriseJavaBeans.part08_asynchronousInvocation;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedEar;
import org.myembedded.pack.EmbeddedEjb;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.JeeVersion;

public class AsynchronousInvocation01 {

    // Each method of a session bean is invoked synchronously (i.e., the client is blocked until the server-side processing is complete and the result returned). 
    // A session bean may tag a method for asynchronous invocation, and a client can then invoke that method asynchronously.
    //
    // This returns control to the client before the container dispatches the instance to a bean.
    //
    // The @Asynchronous annotation is used to mark a specific method (method level) or all methods (class level) of the bean as asynchronous.
    @Stateless
    @Asynchronous
    public static class MyAsyncBeanClassLevel {

        public static final long AWAIT = 3000;

        // The asynchronous operations must have a return type of void or Future<V>
        //
        // The method signature returns Future<Integer> and the return type is AsyncResult(Integer).
        public Future<Integer> addNumbers(final int n1, final int n2) {
            try {
                // simulating a long running query
                Thread.sleep(AWAIT);
            } catch (final InterruptedException ex) {
                Logger.getLogger(MyAsyncBeanClassLevel.class.getName()).log(Level.SEVERE, null, ex);
            }

            // AsyncResult is a new class introduced in EJB 3.1 that wraps the result of an asynchronous method as a Future object.
            // Behind the scenes, the value is retrieved and sent to the client. Adding any new methods to this class will 
            // automatically make them asynchronous as well.
            return new AsyncResult<Integer>(n1 + n2);
        }
    }

    @Stateless
    public static class MyAsyncBeanMethodLevel {

        public static final long AWAIT = 3000;

        @Asynchronous
        public Future<Integer> addNumbers(final int n1, final int n2) {
            try {
                // simulating a long running query
                Thread.sleep(AWAIT);
            } catch (final InterruptedException ex) {
                Logger.getLogger(MyAsyncBeanMethodLevel.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new AsyncResult<Integer>(n1 + n2);
        }
    }

    // ==================================================================================================================================================================
    public static void main(final String[] args) throws Exception {

        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedEjb ejb = new EmbeddedEjb(EMBEDDED_JEE_TEST_APP_NAME);
            ejb.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
            ejb.addClasses(MyAsyncBeanMethodLevel.class, MyAsyncBeanClassLevel.class);

            final EmbeddedEar ear = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME, JeeVersion.JEE_7);
            ear.addModules(ejb);

            final File earFile = ear.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);

            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, earFile.getAbsolutePath());

            // -------------------------------------------------------------------------------------------------------------------------

            final Integer numberOne = 5;
            final Integer numberTwo = 10;

            final long start = System.currentTimeMillis();

            final Context context = new InitialContext();

            // This session bean can be injected and invoked in any Java EE component:

            final MyAsyncBeanClassLevel bean = (MyAsyncBeanClassLevel) context.lookup("java:global/embeddedJeeContainerTest/embeddedJeeContainerTest/MyAsyncBeanClassLevel");

            final Future<Integer> resultFuture = bean.addNumbers(numberOne, numberTwo);

            System.out.println("Done? " + resultFuture.isDone() + " " + start);

            boolean isDone = false;

            do {
                // The methods on the Future API are used to query the availability of a result with isDone 
                // or cancel the execution with cancel(boolean mayInterruptIfRunning) 
                isDone = resultFuture.isDone();
            } while (isDone == false);

            System.out.println("And now? " + resultFuture.isDone() + " " + System.currentTimeMillis());

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        // * IMPORTANT *
        // The client transaction context does not propagate to the asynchronous business method.
        // This means that the semantics of the REQUIRED transaction attribute on an asynchronous method are exactly the same as REQUIRES_NEW 
        //
        // The client security principal propagates to the asynchronous business method. 
        // This means the security context propagation behaves the same way for synchronous and asynchronous method execution.

        downVariables();
    }

}
