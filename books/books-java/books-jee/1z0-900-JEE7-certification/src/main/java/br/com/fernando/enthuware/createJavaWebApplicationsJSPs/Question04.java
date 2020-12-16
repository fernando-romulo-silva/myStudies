package br.com.fernando.enthuware.createJavaWebApplicationsJSPs;

public class Question04 {
    // Which of the following error-page declaration is syntactically correct.
    //
    // Choice A
    /**
     * <pre>
     *       <on-error>
     *          <location>/general-error.html<location>
     *       </on-error>
     * </pre>
     */

    // Choice B
    /**
     * <pre>
     *     <error-page>
     *        <error-code>*</error-code>
     *        <location>/general-error.html</location>
     *     </error-page>
     * </pre>
     */

    // Choice C
    /**
     * <pre>
     *      <error-page>
     *         <location>/general-error.html</location>
     *      </error-page>
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
    // The correct answer is C
    // 
    // You can use the standard webapp configuration file located in webapp/WEB-INF/web.xml to map errors to specific URLs with the error-page element. 
    // This element creates a mapping between the error-code or exception-type to the location of a resource in the web application.
    //
    // If an error-page element in the deployment descriptor does not contain an exception-type or an error-code element, the error page is a default error page.
    //
    // You can specify the error page in a deployment descriptor in 2 ways:
    /**
     * <pre>
     *	    <web-app>
     *	      ...
     *	      <error-page>
     *	        <error-code>404</error-code>
     *	        <location>/html/ErrorPage.html</location>
     *	      </error-page>
     *
     *	      <error-page>
     *	        <exception-type>javax.servlet.ServletException</exception-type>
     *	        <location>/html/ErrorPage.html</location>
     *	      </error-page>
     *	       ...
     *	    </web-app>
     * </pre>
     */
    // Here, if the HTTP ERROR 404 (not found) is generated, ErrorPage.html will be serviced. 
    // Similarly if any servlet throws servlet exception while processing a request, this page will be serviced.

}
