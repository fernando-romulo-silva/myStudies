package br.com.fernando.enthuware.createJavaWebApplicationsUsingServlets;

public class Question05 {

    // Given the code fragment:

    /**
     * <pre>
     *  
     *      <servlet-mapping>
     *      	<servlet-name>firstServlet</servlet-name>
     *      	<url-pattern>/*</url-pattern>
     *      </servlet-mapping>
     *      
     *      <servlet-mapping>
     *      	<servlet-name>secondServlet</servlet-name>
     *      	<url-pattern>/</url-pattern>
     *      </servlet-mapping>
     *      
     *      <servlet-mapping>
     *      	<servlet-name>thirdServlet</servlet-name>
     *      	<url-pattern>/</url-pattern>
     *      </servlet-mapping>
     * </pre>
     */

    // When the context root is requested http://host:port/context, how does the container resolve this mapping?
    // You had to select 1 option(s)
    //
    // A
    // thirdServlet handles the request.
    //
    // B
    // firstServlet handles the request.
    //
    // C
    // The container throws an error at startup.
    //
    // D
    // secondServlet handles the request.
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
    // The D is correct
    //
    // As per Section 12.2 "Specification of Mappings" of Servlet 3.1:
    // If the effective web.xml (after merging information from fragments and annotations)
    // contains any url-patterns that are mapped to multiple servlets then the deployment must fail.

}
