package br.com.fernando.myExamCloud.freePracticeTest.createJavaWebApplicationsUsingServlets;

public class Question01 {

    // Code : A Servlet is mapped in the web.xml of the web application "order" as
    //
    /**
     * <pre>
     * 
     *    <web-app>
     *        ...    
     *        <servlet-mapping>
     *            <servlet-name>ManageProfile</servlet-name>
     *            <url-pattern>ManageProfile</url-pattern>
     *        </servlet-mapping>
     *        ...
     *    <web-app>
     * </pre>
     */
    //
    // Which of the following URLs would be served by this Servlet?
    //
    // Choice A - http://localhost/order/ManageProfile
    //
    // Choice B - http://localhost/order/manageprofile
    //
    // Choice C - http://localhost/order/manageProfile
    //
    // Choice D - None of the above
    //
    //
    // Choice D is correct
    //
    // In the Web application deployment descriptor, the following syntax is used to define mappings:
    //
    // A string beginning with a '/' character and ending with a '/*' suffix is used for path mapping.
    //
    // A string beginning with a '*.' prefix is used as an extension mapping.
    //
    // A string containing only the '/' character indicates the "default" servlet of the application.
    //
    // In this case the servlet path is the request URI minus the context path and the path info is null.
    // All other strings are used for exact matches only.
    // Since in our case the <url-pattern> specifies a path mapping without a leading "/", the
    // web application would fail to load. Hence choice D is correct while choices A, B, and C are incorrect.
}
