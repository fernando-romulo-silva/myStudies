package br.com.fernando.chapter09_contextDependencyInjection.part08_stereotypes;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.annotation.Priority;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Stereotype;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InterceptorBinding;
import javax.interceptor.InvocationContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Stereotypes {

    @InterceptorBinding
    @Retention(RUNTIME)
    @Target({ METHOD, TYPE })
    public static @interface Logging {
    }

    @Interceptor
    @Logging
    @Priority(Interceptor.Priority.APPLICATION + 10)
    public static class LoggingInterceptor {

        @AroundInvoke
        public Object log(final InvocationContext context) throws Exception {
            final String name = context.getMethod().getName();
            final String params = context.getParameters().toString();
            System.out.println("LoggingInterceptor, method: " + name);
            System.out.println("LoggingInterceptor, params: " + params);
            return context.proceed();
        }
    }

    // A stereotype encapsulates architectural patterns or common metadata for beans that produce recurring roles in a central place.
    //
    // It encapsulates scope, interceptor bindings, qualifiers, and other properties of the role.
    //
    // Adding @Alternative to the stereotype definition marks all the target beans to be alternatives.
    //
    // A stereotype is a meta-annotation annotated with @Stereotype
    @Stereotype
    @Retention(RUNTIME)
    @Target(TYPE)
    @Transactional // A stereotype that adds transactional behavior can be define
    @RequestScoped // A stereotype may declare the default scope of a bean
    @Logging // A stereotype may declare zero, one, or multiple interceptor bindings
    @Model // Stereotypes can be stacked together to create new stereotypes as well.
    // @Interceptor, @Decorator, and @Model are predefined stereotypes.
    // The @Model stereotype is predefined
    public static @interface MyStereotype {
        // In this code, an interceptor binding defined earlier, @Transactional, is used to define the stereotype.
        //
        // Specifying this stereotype on a bean marks it to have @RequestScoped unless the bean explicitly specifies the scope.
        // A stereotype may declare at most one scope.
    }

    // A single interceptor binding defines this stereotype instead of the interceptor binding.
    //
    // However, it allows you to update the stereotype definition later with other scopes, qualifiers, and properties, and those values
    // are then automatically applied on the bean.
    //
    //
    // A stereotype can be specified on a target bean like any other annotation:
    @MyStereotype
    public static class MyBean {
        // The metadata defined by the stereotype is now applicable on the bean.

        public void execute() {
            System.out.println("MyBean.execute");
        }
    }

    // Stereotypes can be stacked together to create new stereotypes as well.
    //
    // The @Model provides a default name for the bean and marks it @RequestScoped.
    //
    // Adding this stereotype on a bean will enable it to pass values from a JSF view to a controller, say, an EJB.

    // ==========================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private MyBean myBean;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            myBean.execute();
        }
    }

    // https://turngeek.github.io/javaeeinaday/chapter/7-one-to-rule-them-all-cdi/

    // You can define a new scope using the extensible context model (@Contextual, @CreationalContext, and @Context interfaces), but that is generally
    // not required by an application developer.

    // ==========================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);

            // WEB-INF
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));

            war.addClasses( //
                    ServletTest.class, //
                    Logging.class, LoggingInterceptor.class, MyStereotype.class, MyBean.class//
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
