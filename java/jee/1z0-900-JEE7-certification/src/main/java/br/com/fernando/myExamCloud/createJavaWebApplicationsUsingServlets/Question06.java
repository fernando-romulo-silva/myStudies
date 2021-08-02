package br.com.fernando.myExamCloud.createJavaWebApplicationsUsingServlets;

public class Question06 {

    // A web application wants the filter "MyFilter" to be invoked whenever the servlet "MyServlet" is accessed.
    // MyServlet has been mapped to the url "/MyServlet" and is implemented by the class "com.epractizelabs.web.MyServlet".
    //
    // Which of the following deployment descriptor fragments to do the same? (select two)
    //
    /**
     * <pre>
     * 
     *   Choice A 	
     *   <filter-mapping>
     *       <filter-name>MyFilter</filter-name>
     *       <servlet-name>MyServlet</servlet-name>
     *   </filter-mapping>
     *     
     *   Choice B 	
     *   <filter-mapping>
     *       <filter-name>MyFilter</filter-name>
     *       <servlet-class>com.epractizelabs.MyServlet</servlet-class>
     *   </filter-mapping>
     *   
     *   Choice C 	
     *   <filter-mapping>
     *       <filter-name>MyFilter</filter-name>
     *       <url-pattern>/MyServlet</url-pattern>
     *   </filter-mapping>
     *     
     *   Choice D 	
     *   <filter-mapping>
     *       <filter-name>MyFilter</filter-name>
     *       <servlet-url-pattern>/MyServlet</servlet-url-pattern>
     *   </filter-mapping>
     *     
     *   Choice E 	
     *   <filter-mapping>
     *       <filter-name>MyFilter</filter-name>
     *       <servlet>MyServlet<servlet>
     *   </filter-mapping>
     * 
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
    //
    //
    //
    //
    //
    //    
    //
    //
    // Choice A and C are correct answers.
    // 
    // A filter can be mapped in two ways:
    //
    // * Mapped specifically to a Servlet using the <servlet-name> For example,
    //     <filter-mapping>
    //         <filter-name>MyFilter</filter-name>
    //         <servlet-name>MyServlet</servlet-name>
    //     </filter-mapping>
    //
    // Whenever MyServlet is accessed, the request goes through the MyFilter.
    //
    // * Mapped to groups of servlets and static content using the <url-pattern> For example,
    //     <filter-mapping>
    //         <filter-name>MyFilter</filter-name>
    //         <url-pattern>/MyServlet</url-pattern>
    //     </filter-mapping>
    // Whenever the request URL is of the form /MyServlet, this filter gets invoked.
    // 
    // Hence choices A and C are correct. Rest of the choices are incorrect as they are semantically incorrect.

}
