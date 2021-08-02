package br.com.fernando.chapter08_enterpriseJavaBeans.part12_interceptors;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.interceptor.AroundConstruct;
import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.Interceptor;
import javax.interceptor.InterceptorBinding;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class EjbInterceptors {

    // An interceptor is defined as a simple class where there is a single method annotated with @AroundInvoke and life-cycle annotation.
    //
    // This method will be called on every business and life-cycle method call to the EJB .
    //
    public static class ConstructorInterceptor {

        // Decorate the method with @AroundConstruct in order to intercept the constructor invocation for a class.
        //
        // The method annotated with @AroundConstruct cannot be a part of the intercepted class.
        //
        // It has to be defined using a separate Interceptor class
        // 
        // Use the @PostConstruct annotation on a method in order to intercept a call back method on a managed bean. 
        @AroundConstruct
        public Object construct(final InvocationContext context) throws Exception {
            System.out.println("SampleInterceptor > construct");
            return context.proceed();
        }
    }

    // As shown in above examples : just use the @Interceptors annotation to specify the interceptor classes
    // @Interceptors can be applied on a class level (automatically applicable to all the methods of a class), to a particular method or multiple methods 
    // and constructor in case of a constructor specific interceptor using @AroundConstruct
    @Stateless
    @Interceptors(ConstructorInterceptor.class)
    public static class ConstructorSampleBean {

        public void execute() {
            System.out.println("ConstructorSampleBean.execute");
        }
    }

    // ----------------------------------------------------------------------------------------------------------------
    public static class SampleInterceptor {

        // The method containing the logic can be part of separate class as well as the target class (class to be intercepted) itself.
        @AroundInvoke
        public Object invoke(final InvocationContext context) throws Exception {
            System.out.println("SampleInterceptor > invoke");
            return context.proceed();
        }

        // The @PreDestroy (another call back annotation defined in Common Annotations spec) annotation is used in a similar fashion
        @PreDestroy
        public void destroy(final InvocationContext context) throws Exception {
            System.out.println("SampleInterceptor > PreDestroy > destroy");
            context.proceed();
        }
    }

    @Interceptors(SampleInterceptor.class)
    @Stateless
    @LocalBean
    public static class SampleBean {

        @PostConstruct
        public void init() {
            System.out.println("SampleBean > PostConstruct > init");
        }

        public void execute() {
            System.out.println("SampleBean > test");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("SampleBean > PreDestroy > destroy");
        }
    }

    // ----------------------------------------------------------------------------------------------------------------
    // An EJB timeout interceptor can be implemented like so:

    @Stateless
    public static class TimeoutInterceptor {

        @Schedule(minute = "*/1", hour = "*")
        public void automaticTimerMethod() {
            System.out.println("TimeoutInterceptor.automaticTimerMethod");
        }

        // @AroundTimeout used to intercept time outs of EJB timers along with a way to obtain an instance of the Timer being intercepted 
        // (via javax.interceptor.InvocationContext.getTimer())
        @AroundTimeout
        public Object timeout(final InvocationContext context) throws Exception {
            System.out.println("TimeoutInterceptor: " //
                    + context.getMethod().getName() + " in class " //
                    + context.getMethod().getDeclaringClass().getName()); //

            return context.proceed();
        }
    }

    // ------------------------------------------------------------------------------------------------------------------
    //
    // CDI Interceptors, look at cdi packages
    @InterceptorBinding
    @Target({ TYPE, METHOD, CONSTRUCTOR })
    @Retention(RUNTIME)
    public static @interface Auditable {

    }

    @Auditable
    @Interceptor
    public static class AuditInterceptor {

        // Interceptors for EJB timer service timeout methods may be defined using the @AroundTimeout annotation on methods in the target 
        // class or in an interceptor class. Only one @AroundTimeout method per class is allowed.
        @AroundInvoke
        public Object audit(final InvocationContext context) throws Exception {
            System.out.println("AuditInterceptor: " //
                    + context.getMethod().getName() + " in class " //
                    + context.getMethod().getDeclaringClass().getName()); //

            return context.proceed();
        }
    }

    @Stateless
    @Auditable
    public static class AnEJB {

        public void bizMethod() {
            //any calls to this method will be intercepted by AuditInterceptor.audit()

            System.out.println("AnEJB.bizMethod");
        }
    }

    // ==========================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private ConstructorSampleBean constructorSampleBean;

        @EJB
        private SampleBean sampleBean;

        @EJB
        private AnEJB anEJB;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            constructorSampleBean.execute();

            sampleBean.execute();

            anEJB.bizMethod();
        }
    }

    // ==========================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));

            war.addClasses( //
                    ServletTest.class, //
                    ConstructorInterceptor.class, ConstructorSampleBean.class, //
                    SampleInterceptor.class, SampleBean.class, //
                    TimeoutInterceptor.class, //
                    Auditable.class, AuditInterceptor.class, AnEJB.class//
            );

            final File appFile = war.exportToFile(APP_FILE_TARGET);

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
