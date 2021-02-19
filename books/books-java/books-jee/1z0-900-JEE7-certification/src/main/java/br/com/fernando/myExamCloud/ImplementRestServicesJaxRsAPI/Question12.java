package br.com.fernando.myExamCloud.ImplementRestServicesJaxRsAPI;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

public class Question12 {

    // Given: Resource class
    @Path("/epractizelabs")
    public class OrderResource {

	@Path("/id")
	@POST
	String saveOrderReport() {
	    // ...
	    return "";
	}

	@Path("/d")
	@GET
	String getOrderReport() {
	    // ...
	    return "";
	}
    }

    // Assume that the following code fragment is defined in web.xml file.
    /**
     * <pre>
     *    <servlet>
     *        <servlet-name>MyResourceHandler</ servlet -name>
     *        <servlet-class>com.jersey.spi.container.servlet.ServletContainer</servlet-class>
     *        ...
     *    </servlet>
     * 
     *    <servlet-mapping>
     *        <servlet-name> MyResourceHandler</ servlet-name>
     *        <url-pattern>/eplorder</url-pattern>
     *    </servlet-mapping>
     *    
     *    // Insert code here
     * 
     * </pre>
     */
    // Which of the following code fragment that would secure access only to the OrderResource saveOrderReport() method?
    //
    // Choice A
    /**
     * <pre>
     *    <security-constraint>
     *        <web-resource-collection>
     *            <url-pattern>/eplorder</url-pattern>
     *            <http-method>POST</http-method>
     *        </web-resource-collection>
     *        ...
     *    </security-constraint>
     * </pre>
     */
    //
    // Choice B
    /**
     * <pre>
     *    <security-constraint>
     *        <web-resource-collection>
     *            <url-pattern>/eplorder</url-pattern>
     *            <http-method>GET</http-method>
     *            <http-method>POST</http-method>
     *        </web-resource-collection>
     *        ...
     *    </security-constraint>
     * </pre>
     */
    //
    // Choice C
    /**
     * <pre>
     *    <security-constraint>
     *        <web-resource-collection>
     *            <url-pattern>/eplorder/id</url-pattern>
     *            <http-method>POST</http-method>
     *            <http-method>GET</http-method>
     *        </web-resource-collection>
     *        ...
     *    </security-constraint>
     * </pre>
     */
    //
    // Choice D
    /**
     * <pre>
     *    <security-constraint>
     *        <web-resource-collection>
     *            <url-pattern>/id</url-pattern>
     *            <http-method>POST</http-method>
     *        </web-resource-collection>
     *        ...
     *    </security-constraint>
     * </pre>
     */
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // Choice A is correct.
    //
    // The saveOrder method is annotated as @POST.
    // The @POST annotation is a request method designator and corresponds to the similarly named HTTP method.
    // The @Path annotation’s value is a relative URI path indicating where the Java class will be hosted. 
    // Hence Choice A is correct.
}
