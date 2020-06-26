package br.com.fernando.chapter16_jndi_resources.part03_generic_javaBean_resources;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.myembedded.pack.EmbeddedResource;
import org.myembedded.pack.EmbeddedWar;

public class Part01 {

    // -------------------------------------------------------------------------------------------------------------------------
    // Generic JavaBean Resources
    //
    // This resource factory can be used to create objects of any Java class that conforms to standard JavaBeans naming conventions
    //
    // Create the JavaBean class which will be instantiated each time that the resource factory is looked up.
    // For this example, assume you create a class like this:
    public static class MyBean {

	private String foo = "Default Foo";

	public String getFoo() {
	    return (this.foo);
	}

	public void setFoo(String foo) {
	    this.foo = foo;
	}

	private int bar = 0;

	public int getBar() {
	    return (this.bar);
	}

	public void setBar(int bar) {
	    this.bar = bar;
	}

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("MyBean [foo=").append(foo).append(", bar=").append(bar).append("]");
	    return builder.toString();
	}
    }

    // -------------------------------------------------------------------------------------------------------------------------
    // Declare Your Resource Requirements
    // Next, modify your web application deployment descriptor (/WEB-INF/web.xml) to declare the JNDI name under which you will request new instances of this bean.
    // The simplest approach is to use a <resource-env-ref> element, like this:
    /**
     * <pre>
     *  
     *  <resource-env-ref>
     *    <description>
     *      Object factory for MyBean instances.
     *    </description>
     *    <resource-env-ref-name>
     *      bean/MyBeanFactory
     *    </resource-env-ref-name>
     *    <resource-env-ref-type>
     *      br.com.fernando.chapter16_jndi_resources.part03_generic_javaBean_resources.Jndi01$MyBean
     *    </resource-env-ref-type>
     *  </resource-env-ref>
     * 
     * </pre>
     */

    // -------------------------------------------------------------------------------------------------------------------------
    // Configure Tomcat's Resource Factory
    // To configure Tomcat's resource factory, add an element like this to the <Context> element for this web application.
    // ** context.xml is Tomcat specific **
    /**
     * <pre>
     * 
     * <Context>
     *    
     *     <Resource name="bean/MyBeanFactory" auth="Container"                                                                
     *               type="br.com.fernando.chapter16_jndi_resources.part03_generic_javaBean_resources.Part01$MyBean"           
     *               factory="br.com.fernando.chapter16_jndi_resources.part03_generic_javaBean_resources.Part01$MyBeanFactory"   
     *               bar="23"/>  
     *             
     * </Context>
     *
     * </pre>
     */

    // -------------------------------------------------------------------------------------------------------------------------
    // Configure Glassfish's Resource Factory
    // To configure Glassfish's resource factory, add an element like this to the <resources> element for this web application.
    // ** glassfish-resources.xml is Glassfish specific **

    /**
     * <pre>
     * 
     * <resources>
     * 
     *    <custom-resource jndi-name="java:app/bean/MyBeanFactory" 
     *                     res-type="br.com.fernando.chapter16_jndi_resources.part03_generic_javaBean_resources.Part01$MyBean" 
     *                     factory-class="br.com.fernando.chapter16_jndi_resources.part03_generic_javaBean_resources.Part01$MyBeanFactory">
     *  	                 
     *    </custom-resource>
     *    
     * </resources>
     * 
     * </pre>
     */

    // Note that the resource name (here, bean/MyBeanFactory must match the value specified in the web application deployment descriptor.
    // We are also initializing the value of the bar property, which will cause setBar(23) to be called before the new bean is returned.
    // Because we are not initializing the foo property (although we could have), the bean will contain whatever default value is set up by its constructor.
    public static class MyBeanFactory implements ObjectFactory {

	@SuppressWarnings("rawtypes")
	public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws NamingException {

	    // Acquire an instance of our specified bean class
	    MyBean bean = new MyBean();

	    // Customize the bean properties from our attributes
	    Reference ref = (Reference) obj;
	    Enumeration addrs = ref.getAll();
	    while (addrs.hasMoreElements()) {
		RefAddr addr = (RefAddr) addrs.nextElement();
		String nameTemp = addr.getType();
		String value = (String) addr.getContent();
		if (nameTemp.equals("foo")) {
		    bean.setFoo(value);
		} else if (nameTemp.equals("bar")) {
		    try {
			bean.setBar(Integer.parseInt(value));
		    } catch (NumberFormatException e) {
			throw new NamingException("Invalid 'bar' value " + value);
		    }
		}
	    }

	    // Return the customized instance
	    return (bean);
	}
    }

    // ==================================================================================================================================================================
    @WebServlet(urlPatterns = { "/ServletPrincipal" })
    public static class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html;charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.println("<!DOCTYPE html>");
	    out.println("<html>");
	    out.println("<head>");
	    out.println("<title> Just a Servlet</title>");
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<h1>This is a Servlet</h1>");
	    out.println("</body>");
	    out.println("</html>");

	    try {

		Context initCtx = new InitialContext();
		// Context envCtx = (Context) initCtx.lookup("java:comp/env"); // for Tomcat server
		Context envCtx = (Context) initCtx.lookup("java:app"); // for Glassfish server

		MyBean bean = (MyBean) envCtx.lookup("bean/MyBeanFactory");

		System.out.println(bean);

	    } catch (Exception e) {
		System.out.println(e);
	    }

	}
    }

    public static void test01() throws Exception {

	final EmbeddedWar war = new EmbeddedWar(EMBEDDED_JEE_TEST_APP_NAME);

	// for Tomcat
	war.addManifestFiles(EmbeddedResource.add("context.xml", "src/main/resources/chapter16_jndi_resources/part02_generic_javaBean_resources/context.xml"));

	// for glassfish
	war.addWebInfFiles(EmbeddedResource.add("glassfish-resources.xml", "src/main/resources/chapter16_jndi_resources/part03_generic_javaBean_resources/glassfish-resources.xml"));

	war.addWebInfFiles(EmbeddedResource.add("web.xml", "src/main/resources/chapter16_jndi_resources/part03_generic_javaBean_resources/web.xml"));
	war.addClasses(TestServlet.class, MyBean.class, MyBeanFactory.class);

	final File warFile = war.exportToFile(APP_FILE_TARGET);

	System.out.println("War file :" + warFile);

	// ----------------------------------------------------------------------------------------

	// web Client
	final HttpClient httpClient = HttpClientBuilder.create().build();

	// http://localhost:8080/embeddedJeeContainerTest01/ServletPrincipal
	final HttpResponse response01 = httpClient.execute(new HttpGet("http://localhost:" + 8080 + "/" + EMBEDDED_JEE_TEST_APP_NAME + "/ServletPrincipal"));
	System.out.println(response01);

    }

    public static void test02() throws Exception {
	final Properties propertiesGlassfish = new Properties();
	propertiesGlassfish.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
	propertiesGlassfish.put(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming");
	propertiesGlassfish.put(Context.PROVIDER_URL, "localhost:1099");

	Context initCtx = new InitialContext(propertiesGlassfish);
	Context envCtx = (Context) initCtx.lookup("java:global/comp/env");
	MyBean bean = (MyBean) envCtx.lookup("bean/MyBeanFactory");

	System.out.println("foo = " + bean.getFoo() + ", bar = " + bean.getBar());
    }

    // ================================================================================================================================
    public static void main(String[] args) throws Exception {
	test01();
    }
}
