package br.com.fernando.chapter08_enterpriseJavaBeans.part06_portableGlobalJndiNames;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PortableGlobalJndiNames01 {

    // Payara and GlassFish have supported classic remote EJB since its inception. Classic remote EJB uses RMI-IIOP 
    // (Remote Method Invocation over Internet Inter-ORB Protocol ) for transport and CSIv2 (Common Secure Interoperability Protocol Version 2) for security.
    // While feature rich, these protocols were not designed with firewalls, NAT, (private) clouds, Docker - and generally the Internet itself - in mind
    //
    // For these reasons, Payara Server 5.191, Wildfly 11, etc, has features an additional EJB remoting technology based on HTTP and using a thin client. 
    // This technology is built from the ground up, and is not using the existing CORBA and IIOP stacks.

    // =======================================================================================================================================================
    // The following portable JNDI namespaces are available. Which ones you can use depends on how your application is packaged.
    //
    // java:global: Makes the resource available to all deployed applications
    //
    // java:app: Makes the resource available to all components in all modules in a single application
    //
    // java:module: Makes the resource available to all components within a given module (for example, all enterprise beans within an EJB module)
    //
    // java:comp: Makes the resource available to a single component only (except in a web application, where it is equivalent to java:module)
    //
    // ==================================================================================================================================================================
    // 1º Application
    // ==================================================================================================================================================================
    // --------------------------------------------------------------------------------------------------------
    // Module EJB Client 01
    // --------------------------------------------------------------------------------------------------------
    @Remote
    public static interface CartRemote {

        public void purchase();
    }

    // --------------------------------------------------------------------------------------------------------
    // Module EJB 01
    // --------------------------------------------------------------------------------------------------------

    // https://docs.oracle.com/cd/E19798-01/821-1841/bnaeu/index.html
    // https://docs.oracle.com/cd/E19879-01/819-3669/bncjh/index.html
    // @Resources({ //
    // @Resource( //
    // name = "java:/services/CartRemote", //
    // lookup = "java:global/CartBean", //
    // type = CartBean.class), //
    // })
    @Stateless
    public static class CartBean implements CartRemote {

        @Override
        public void purchase() {
            System.out.println("Purchase!");
        }
    }

    // --------------------------------------------------------------------------------------------------------
    // Module EJB 02
    // --------------------------------------------------------------------------------------------------------
    @Local
    public static interface PaymentLocal {

        void pay();
    }

    @Stateful
    public static class PaymentBean implements PaymentLocal {

        @Override
        public void pay() {
            System.out.println("Pay!");
        }
    }

    // --------------------------------------------------------------------------------------------------------
    // Module Web 01
    // --------------------------------------------------------------------------------------------------------
    @WebServlet("/ServletPrincipal02")
    public static class ServletPrincipal02 extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private AddressLocal address;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            try {
                address.showAddress();
            } catch (final Exception e) {
                throw new ServletException(e);
            }
        }
    }

    // ==================================================================================================================================================================
    // 2º Application
    // ==================================================================================================================================================================
    // --------------------------------------------------------------------------------------------------------
    // Module EJB 03
    // --------------------------------------------------------------------------------------------------------

    @Stateless
    public static class UserBean {

        public void showUser() {
            System.out.println("Show User!");
        }
    }

    public static interface AddressInterface {

        void showAddress();
    }

    @Remote
    public static interface AddressRemote extends AddressInterface {

    }

    @Local
    public static interface AddressLocal extends AddressInterface {

    }

    @Stateless
    public static class AddressBean implements AddressRemote, AddressLocal {

        @EJB(lookup = "java:global/embeddedJeeContainerTest01/embeddedJeeContainerTest01EjbClient01/PortableGlobalJndiNames01$CartBean")
        private CartRemote cartRemote;

        @Override
        public void showAddress() {
            System.out.println("Show Address!");
            cartRemote.purchase();
        }
    }

    // --------------------------------------------------------------------------------------------------------
    // Module web 02
    // --------------------------------------------------------------------------------------------------------
    @WebServlet("/ServletPrincipal01")
    public static class ServletPrincipal01 extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @EJB
        private UserBean userBean;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            try {
                userBean.showUser();
            } catch (final Exception e) {
                throw new ServletException(e);
            }
        }
    }
    // ==================================================================================================================================================================
    // embeddedJeeContainerTest01.ear
    //
    // ----------> embeddedJeeContainerTest01EjbClient01.jar
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
    // ----------> embeddedJeeContainerTest01EjbClient01.jar
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
    // ==================================================================================================================================================================
    //
    // A session bean can be packaged in an ejb-jar file or within a web application module (.war).
    //
    // An optional EJB deployment descriptor, ejb-jar.xml, providing additional information about the deployment may be packaged in an ejb-jar or .war file.
    //
    // The ejb-jar.xml file can be packaged as either WEB-INF/ejb-jar.xml or META-INF/ejb-jar.xml within one of the WEB-INF/lib JAR files, but not both.
    //
    // A local or no-interface bean packaged in the .war file is accessible only to other components within the same .war file,
    // but a bean marked with @Remote is remotely accessible independent of its packaging.
    //
    // The ejb-jar file may be deployed by itself or packaged within an .ear file. The beans packaged in this ejb-jar can be accessed remotely.
    //
    // You can access this EJB using a portable global JNDI name with the following syntax:
    //
    // java:global[/<app-name>]/<module-name>/<bean-name>[!<fully-qualified-interface-name>]
    //
    // <app-name> applies only if the session bean is packaged with an .ear file.
    //
    // <module-name> is the name of the module in which the session bean is packaged.
    //
    // <bean-name> is the ejb-name of the enterprise bean.
    //
    // If the bean exposes only one client interface (or alternatively has only a no-interface view), the bean is also exposed with an additional JNDI
    // name using the following syntax:
    //
    // java:global[/<app-name>]/<module-name>/<bean-name>
    //
    // The stateless session bean is also available through the java:app and java:module namespaces.
}
