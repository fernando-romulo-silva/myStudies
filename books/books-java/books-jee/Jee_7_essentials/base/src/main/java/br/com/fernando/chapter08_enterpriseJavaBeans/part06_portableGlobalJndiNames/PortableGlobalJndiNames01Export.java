package br.com.fernando.chapter08_enterpriseJavaBeans.part06_portableGlobalJndiNames;

import static br.com.fernando.Util.APP_FILE_TARGET;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.pack.EmbeddedEar;
import org.myembedded.pack.EmbeddedEjb;
import org.myembedded.pack.EmbeddedJar;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;
import org.myembedded.pack.JeeVersion;

import br.com.fernando.chapter08_enterpriseJavaBeans.part06_portableGlobalJndiNames.PortableGlobalJndiNames01.AddressBean;
import br.com.fernando.chapter08_enterpriseJavaBeans.part06_portableGlobalJndiNames.PortableGlobalJndiNames01.AddressInterface;
import br.com.fernando.chapter08_enterpriseJavaBeans.part06_portableGlobalJndiNames.PortableGlobalJndiNames01.AddressLocal;
import br.com.fernando.chapter08_enterpriseJavaBeans.part06_portableGlobalJndiNames.PortableGlobalJndiNames01.AddressRemote;
import br.com.fernando.chapter08_enterpriseJavaBeans.part06_portableGlobalJndiNames.PortableGlobalJndiNames01.CartBean;
import br.com.fernando.chapter08_enterpriseJavaBeans.part06_portableGlobalJndiNames.PortableGlobalJndiNames01.CartRemote;
import br.com.fernando.chapter08_enterpriseJavaBeans.part06_portableGlobalJndiNames.PortableGlobalJndiNames01.PaymentBean;
import br.com.fernando.chapter08_enterpriseJavaBeans.part06_portableGlobalJndiNames.PortableGlobalJndiNames01.PaymentLocal;
import br.com.fernando.chapter08_enterpriseJavaBeans.part06_portableGlobalJndiNames.PortableGlobalJndiNames01.ServletPrincipal01;
import br.com.fernando.chapter08_enterpriseJavaBeans.part06_portableGlobalJndiNames.PortableGlobalJndiNames01.ServletPrincipal02;
import br.com.fernando.chapter08_enterpriseJavaBeans.part06_portableGlobalJndiNames.PortableGlobalJndiNames01.UserBean;

public class PortableGlobalJndiNames01Export {

    private static final String EMBEDDED_JEE_TEST_APP_NAME_01 = "embeddedJeeContainerTest01";

    private static final String EMBEDDED_JEE_TEST_APP_NAME_02 = "embeddedJeeContainerTest02";

    private static final String MODULE_EJB_CLIENT_01 = EMBEDDED_JEE_TEST_APP_NAME_01 + "EjbClient01";

    private static final String MODULE_EJB_01 = EMBEDDED_JEE_TEST_APP_NAME_01 + "Ejb01";

    private static final String MODULE_EJB_02 = EMBEDDED_JEE_TEST_APP_NAME_01 + "Ejb02";

    private static final String MODULE_EJB_03 = EMBEDDED_JEE_TEST_APP_NAME_02 + "Ejb03";

    public static void main(String[] args) throws Exception {

        // ====================================================================================================================================================
        // App 01
        // --------------------------------------------------------------------------------------------------------
        // Module EJB Client 01
        // --------------------------------------------------------------------------------------------------------
        final EmbeddedJar ejbClient01 = new EmbeddedJar(MODULE_EJB_CLIENT_01);
        ejbClient01.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
        ejbClient01.addClasses(PortableGlobalJndiNames01.class, CartRemote.class);

        // --------------------------------------------------------------------------------------------------------
        // Module EJB 01
        // --------------------------------------------------------------------------------------------------------
        final EmbeddedEjb ejb01 = new EmbeddedEjb(MODULE_EJB_01);
        ejb01.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
        // ejb01.addMetaInfFiles(EmbeddedResource.add("glassfish-ejb-jar.xml", "src/main/resources/chapter08_enterpriseJavaBeans/glassfish-ejb-jar.xml"));
        ejb01.addClasses(PortableGlobalJndiNames01.class, CartBean.class);

        // --------------------------------------------------------------------------------------------------------
        // Module EJB 02
        // --------------------------------------------------------------------------------------------------------
        final EmbeddedEjb ejb02 = new EmbeddedEjb(MODULE_EJB_02);
        ejb02.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
        // ejb02.addMetaInfFiles(EmbeddedResource.add("glassfish-ejb-jar.xml", "src/main/resources/chapter08_enterpriseJavaBeans/glassfish-ejb-jar.xml"));
        ejb02.addClasses(PortableGlobalJndiNames01.class, PaymentLocal.class, PaymentBean.class);

        // --------------------------------------------------------------------------------------------------------
        // Module web 01
        // --------------------------------------------------------------------------------------------------------
        final EmbeddedWar war01 = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME_01);
        war01.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter14_jms/beans.xml"));
        war01.addClasses(PortableGlobalJndiNames01.class, ServletPrincipal01.class, UserBean.class);

        // ---------------------------------------------------------------------------------------------------------
        // EAR 01
        // ---------------------------------------------------------------------------------------------------------
        final EmbeddedEar ear01 = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME_01, JeeVersion.JEE_7);
        // ear01.addEmbededdedlibs(ejbClient01);
        ear01.addModules(ejbClient01);
        ear01.addModules(ejb01);
        ear01.addModules(ejb02);
        ear01.addModules(war01);

        // ====================================================================================================================================================
        // App 02
        // --------------------------------------------------------------------------------------------------------
        // Module EJB 03
        // --------------------------------------------------------------------------------------------------------
        final EmbeddedEjb ejb03 = new EmbeddedEjb(MODULE_EJB_03);
        ejb03.addMetaInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/beans.xml"));
        // ejb03.addMetaInfFiles(EmbeddedResource.add("glassfish-ejb-jar.xml", "src/main/resources/chapter08_enterpriseJavaBeans/glassfish-ejb-jar.xml"));
        ejb03.addClasses(PortableGlobalJndiNames01.class, AddressInterface.class, AddressLocal.class, AddressRemote.class, AddressBean.class);

        // --------------------------------------------------------------------------------------------------------
        // Module web 02
        // --------------------------------------------------------------------------------------------------------
        final EmbeddedWar war02 = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME_02);
        war02.addWebInfFiles(EmbeddedResource.add("beans.xml", "src/main/resources/chapter14_jms/beans.xml"));
        war02.addClasses(PortableGlobalJndiNames01.class, ServletPrincipal02.class);

        // ---------------------------------------------------------------------------------------------------------
        // EAR 02
        // ---------------------------------------------------------------------------------------------------------
        final EmbeddedEar ear02 = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME_02, JeeVersion.JEE_7);
        ear02.addModules(ejbClient01);
        ear02.addModules(ejb03);
        ear02.addModules(war02);
        // ear02.addEmbededdedlibs(ejbClient01);

        // ======================================================================================================================================================
        // embeddedJeeContainerTest01.ear
        //
        // ----------> embeddedJeeContainerTest01Ejb01Client.jar
        // -----------------> CartRemote
        //
        // ----------> embeddedJeeContainerTest01Ejb01.jar
        // -----------------> CartBean
        //
        // ----------> embeddedJeeContainerTest01Ejb02.jar
        // -----------------> PaymentLocal
        // -----------------> PaymentBean
        //
        // ----------> embeddedJeeContainerTest01.war
        // ------------------> ServletPrincipal02
        //
        //
        // embeddedJeeContainerTest02.ear
        //
        // ----------> embeddedJeeContainerTest01Ejb01Client.jar
        // -----------------> CartRemote
        //
        // ----------> embeddedJeeContainerTest02Ejb03.jar
        // -----------------> UserBean
        // -----------------> AddressInterface
        // -----------------> AddressRemote
        // -----------------> AddressLocal
        // -----------------> AddressBean
        //
        // ----------> embeddedJeeContainerTest01.war
        // ------------------> ServletPrincipal02
        //
        //
        // Export
        ear01.exportToFile(APP_FILE_TARGET);
        ear02.exportToFile(APP_FILE_TARGET);

        // ====================================================================================================================================================
        // JSE Client
        // ctx.lookup("java:global/*EARNAME/EJBJARNAME*/FirstBean!*fullyqualifiedpackage*.FirstBeanRemote");

        final Properties propertiesGlassfish = new Properties();
        propertiesGlassfish.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
        propertiesGlassfish.put(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming");
        propertiesGlassfish.put(Context.PROVIDER_URL, "localhost:1099");

        // propertiesGlassfish.setProperty("org.omg.CORBA.ORBInitialHost", "*hostname*");
        // propertiesGlassfish.setProperty("org.omg.CORBA.ORBInitialPort", "*3700*");//default port	

        final Properties propertiesJBoss = new Properties();
        propertiesJBoss.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        propertiesJBoss.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        propertiesJBoss.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, "remote://localhost:4447"));
        propertiesJBoss.put("jboss.naming.client.ejb.context", "true");

        propertiesJBoss.put(Context.SECURITY_PRINCIPAL, System.getProperty("username", "admin"));
        propertiesJBoss.put(Context.SECURITY_CREDENTIALS, System.getProperty("password", "admin"));

        final Context context = new InitialContext(propertiesJBoss);

        final CartRemote cart01 = (CartRemote) context.lookup("java:global/" + EMBEDDED_JEE_TEST_APP_NAME_01 + "/" + MODULE_EJB_01 + "/PortableGlobalJndiNames01$CartBean");
        cart01.purchase();

        final AddressRemote address = (AddressRemote) context.lookup("java:global/" + EMBEDDED_JEE_TEST_APP_NAME_02 + "/" + MODULE_EJB_03 + "/PortableGlobalJndiNames01$AddressBean");
        address.showAddress();

        // web Client
        final HttpClient httpClient = HttpClientBuilder.create().build();

        final String HTTP_PORT = "8080";

        // http://localhost:8080/embeddedJeeContainerTest01/ServletPrincipal
        final HttpResponse response01 = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME_01 + "/ServletPrincipal"));
        System.out.println(response01);

        // http://localhost:8080/embeddedJeeContainerTest02/ServletPrincipal
        final HttpResponse response02 = httpClient.execute(new HttpGet("http://localhost:" + HTTP_PORT + "/" + EMBEDDED_JEE_TEST_APP_NAME_02 + "/ServletPrincipal"));
        System.out.println(response02);
    }
}
