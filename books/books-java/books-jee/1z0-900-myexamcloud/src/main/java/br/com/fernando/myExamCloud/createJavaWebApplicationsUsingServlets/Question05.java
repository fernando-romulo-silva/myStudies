package br.com.fernando.myExamCloud.createJavaWebApplicationsUsingServlets;

public class Question05 {

    // Given the code fragment:
    /**
     * <pre>
     *     <servlet-mapping>
     *       <servlet-name>firstServlet</servlet-name>
     *       <url-pattern>/*</url-pattern>
     *     </servlet-mapping>
     *     
     *     <servlet-mapping>
     *       <servlet-name>secondServlet</servlet-name>
     *       <url-pattern>/</url-pattern>
     *     </servlet-mapping>
     *     
     *     <servlet-mapping>
     *       <servlet-name>thirdServlet</servlet-name>
     *       <url-pattern>/</url-pattern>
     *     </servlet-mapping>
     *     
     * </pre>
     */
    // When the context root is requested http://host:port/context, how does the container resolve this mapping?
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
    // Choice C is correct.
    //
    // The http://host:port/context will not match with any of these servlets. It may work if the URL appended with / .
    // The url-pattern pattern for the first servlet is /*. The /* indicates that it can accept all URL calls to this web application. 
    // The /* on a servlet overrides all other servlets, including all servlets provided by the servletcontainer such as the default servlet and the JSP servlet. 
    // Whatever request you fire, it will end up in that servlet. This is thus a bad URL pattern for servlets. Usually, you'd like to use /* on a Filter only
    //
    // <url-pattern>/</url-pattern>
    // The / doesn't override any other servlet. 
    // It only replaces the servletcontainer's builtin default servlet for all requests which doesn't match any other registered servlet. 
    // This is normally only invoked on static resources (CSS/JS/image/etc) and directory listings
    // In contrast, no two servlet-mapping elements in the same application may use the same url-pattern. 
    // If the web.xml file contains two identical mappings to different servlets, the container makes no guarantees about which servlet the container calls for a given request    
    
    
    
}
