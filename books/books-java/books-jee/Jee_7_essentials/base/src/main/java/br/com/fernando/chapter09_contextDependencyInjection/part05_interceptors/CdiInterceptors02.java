package br.com.fernando.chapter09_contextDependencyInjection.part05_interceptors;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static javax.enterprise.inject.spi.InterceptionType.AROUND_INVOKE;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.Interceptor;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.interceptor.InterceptorBinding;
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

public class CdiInterceptors02 {

    // ===========================================================================================================================================================
    // Dynamic Interceptors
    //
    @Inherited
    @InterceptorBinding
    @Retention(RUNTIME)
    @Target(METHOD)
    public static @interface Hello {
    }

    @javax.interceptor.Interceptor
    @Priority(200)
    public static class HelloInterceptorEnabler {
    }

    /**
     * Default implementation of the Interceptor interface with all the boring defaults
     * 
     * @author Arjan Tijms
     */
    public static abstract class DynamicInterceptorBase<T> implements Interceptor<T>, PassivationCapable {

        @Override
        public Set<Annotation> getQualifiers() {
            return emptySet();
        }

        @Override
        public Class<? extends Annotation> getScope() {
            return Dependent.class;
        }

        @Override
        public Set<Class<? extends Annotation>> getStereotypes() {
            return emptySet();
        }

        @Override
        public Set<InjectionPoint> getInjectionPoints() {
            return emptySet();
        }

        @Override
        public boolean isAlternative() {
            return false;
        }

        @Override
        public boolean isNullable() {
            return false;
        }

        @Override
        public String getName() {
            return null;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T create(final CreationalContext<T> creationalContext) {
            try {
                return (T) getBeanClass().newInstance();
            } catch (final Exception e) {
                throw new RuntimeException("Error creating an instance of " + getBeanClass());
            }
        }

        @Override
        public Set<Type> getTypes() {
            return new HashSet<Type>(asList(getBeanClass(), Object.class));
        }

        @Override
        public void destroy(final T instance, final CreationalContext<T> creationalContext) {
            creationalContext.release();
        }

        @Override
        public String getId() {
            return toString();
        }
    }

    public static class DynamicHelloInterceptor extends DynamicInterceptorBase<HelloInterceptorEnabler> {

        /**
         * The Intercept binding this dynamic interceptor is doing its work for
         */
        @Override
        public Set<Annotation> getInterceptorBindings() {
            return singleton((Annotation) new HelloAnnotationLiteral());
        }

        /**
         * The type of intercepting being done, corresponds to <code>@AroundInvoke</code> etc on
         * "static" interceptors
         */
        @Override
        public boolean intercepts(final InterceptionType type) {
            return AROUND_INVOKE.equals(type);
        }

        /**
         * The annotated class that contains the priority and causes the interceptor to be enabled
         */
        @Override
        public Class<?> getBeanClass() {
            return HelloInterceptorEnabler.class;
        }

        @Override
        public Object intercept(final InterceptionType type, final HelloInterceptorEnabler enabler, final InvocationContext ctx) {
            try {
                return "Hello, " + ctx.proceed();
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("all")
    public static class HelloAnnotationLiteral extends AnnotationLiteral<Hello> implements Hello {

        private static final long serialVersionUID = 1L;

    }

    public static class CdiExtension implements Extension {

        /**
         * This method registers the (annotated) class that enables the interceptor and sets its priority
         */
        public void register(@Observes final BeforeBeanDiscovery beforeBean, final BeanManager beanManager) {
            beforeBean.addAnnotatedType(beanManager.createAnnotatedType(HelloInterceptorEnabler.class), "CdiExtension" + HelloInterceptorEnabler.class);
        }

        /**
         * This method registers the actual dynamic interceptor
         */
        public void afterBean(final @Observes AfterBeanDiscovery afterBeanDiscovery) {
            afterBeanDiscovery.addBean(new DynamicHelloInterceptor());
        }
    }

    public static class MyBean {

        @Hello
        public String getName() {
            return "John";
        }
    }

    public static class Service {

        @Inject
        private MyBean myBean;

        public void execute() {
            System.out.println(myBean.getName());
        }
    }

    // ==========================================================================================================================================================
    @WebServlet("/Servlet")
    public static class ServletTest extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Inject
        private Service service;

        @Override
        protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
            service.execute();
        }
    }

    // ==========================================================================================================================================================
    public static void main(final String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer()) {

            // ------------ Packages -----------------------------------------------------------
            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));

            war.addClasses( //
                    ServletTest.class, Service.class, //
                    MyBean.class, CdiExtension.class, HelloAnnotationLiteral.class, DynamicHelloInterceptor.class, DynamicInterceptorBase.class, HelloInterceptorEnabler.class, Hello.class //
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
