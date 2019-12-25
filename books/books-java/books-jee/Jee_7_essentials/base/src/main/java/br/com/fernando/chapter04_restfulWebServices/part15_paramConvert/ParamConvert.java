package br.com.fernando.chapter04_restfulWebServices.part15_paramConvert;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;
import static br.com.fernando.Util.HTTP_PORT;
import static br.com.fernando.Util.downVariables;
import static br.com.fernando.Util.startVariables;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.myembedded.jeecontainer.MyEmbeddedJeeContainer;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class ParamConvert {

    @Path("/endpoint")
    public static class MyResource {

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public String getWithQuery(@DefaultValue("bar") @QueryParam("search") MyBean myBean) {
            return myBean.getValue();
        }

        @GET
        @Path("/{id}")
        @Produces(MediaType.TEXT_PLAIN)
        public String getByPath(@PathParam("id") MyBean myBean) {
            return myBean.getValue();
        }
    }

    @Provider
    public static class MyBeanConverterProvider implements ParamConverterProvider {

        @Override
        public <T> ParamConverter<T> getConverter(Class<T> clazz, Type type, Annotation[] annotations) {
            if (clazz.getName().equals(MyBean.class.getName())) {

                return new ParamConverter<T>() {

                    @SuppressWarnings("unchecked")
                    @Override
                    public T fromString(String value) {
                        MyBean bean = new MyBean();
                        bean.setValue(value);
                        return (T) bean;
                    }

                    @Override
                    public String toString(T bean) {
                        return ((MyBean) bean).getValue();
                    }

                };
            }
            return null;
        }
    }

    @Provider
    public static class MyConverterProvider implements ParamConverterProvider {

        @Override
        public <T> ParamConverter<T> getConverter(final Class<T> rawType, final Type genericType, final Annotation[] annotations) {
            if (rawType.getName().equals(MyBean.class.getName())) {
                return new ParamConverter<T>() {

                    @Override
                    public T fromString(String value) {
                        MyBean myBean = new MyBean();
                        myBean.setValue(value);
                        return rawType.cast(myBean);
                    }

                    @Override
                    public String toString(T myBean) {
                        if (myBean == null) {
                            return null;
                        }
                        return myBean.toString();
                    }
                };
            }
            return null;
        }

    }

    public static class MyBean {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return getValue();
        }
    }

    @ApplicationPath("webresources")
    public static class MyApplication extends Application {
    }

    // ==================================================================================================================================================================
    public static void main(String[] args) throws Exception {
        startVariables();

        try (final MyEmbeddedJeeContainer embeddedJeeServer = new MyEmbeddedJeeContainer();) {

            final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);
            war.addClasses(MyBean.class, MyApplication.class, MyResource.class, MyBeanConverterProvider.class, MyConverterProvider.class);

            // WEB-INF
            war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter04_restfulWebServices/web.xml"));
            war.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter04_restfulWebServices/beans.xml"));

            final File warFile = war.exportToFile(APP_FILE_TARGET);

            embeddedJeeServer.start(HTTP_PORT);
            embeddedJeeServer.deploy(EMBEDDED_JEE_TEST_APP_NAME, warFile.getAbsolutePath());

            // ---------------------------------------------------------------------------------------
            final Client client = ClientBuilder.newClient();

            final String result01 = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/endpoint") //
                    .queryParam("search", "foo") //
                    .request() //
                    .get(String.class);

            System.out.println(result01); // foo

            // ----------------------------------------------------------------------------------------
            final String result02 = client //
                    .target("http://localhost:8080/embeddedJeeContainerTest/webresources/endpoint/foo") //
                    .request() //
                    .get(String.class);

            System.out.println(result02); // foo

            System.out.println("the end");

        } catch (final Exception ex) {
            System.out.println(ex);
        }

        downVariables();

        // resource-validation
    }
}
