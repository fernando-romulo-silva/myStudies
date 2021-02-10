package br.com.fernando.myExamCloud.createJavaWebApplicationsUsingServlets;

public class Question17 {

    // The web.xml of a web application looks like this:
    /**
     * <pre>
     *    <web-app>
     *        <servlet>
     *            <servlet-name>aServlet</servlet-name>
     *            <servlet-class>com.epractizelabs.web.AServlet</servlet-class>
     *        <load-on-startup>3<load-on-startup>
     *        <servlet>
     *        <servlet>
     *            <servlet-name>bServlet</servlet-name>
     *            <servlet-class>com.epractizelabs.web.BServlet</servlet-class>
     *            <load-on-startup>-1<load-on-startup>
     *        <servlet>
     *        <servlet>
     *            <servlet-name>cServlet</servlet-name>
     *            <servlet-class>com.epractizelabs.web.CServlet</servlet-class>
     *            <load-on-startup>1<load-on-startup>
     *        <servlet>
     *    </web-app>
     * </pre>
     */

    // Which of the following statements are true by considering the above web.xml file?
    //
    //
    // Choice A
    // The application tries to load aServlet first followed by cServlet.
    //
    // Choice B
    // Servlet will not be loaded.
    //
    // Choice C
    // The loading order of bServlet cannot be predicted from the web.xml.
    //
    // Choice D
    // The application fails to load as load-on-startup cannot have negative values.
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
    //
    //
    // Choice C is correct.
    //
    // The load-on-startup element indicates that the Servlet should be loaded (instantiated and have its init() called) on the startup of the web application.
    //
    // The optional contents of these elements must be an integer indicating the order in which the servlet should be loaded.
    // If the value is a NEGATIVE integer, or the element is not present, the container is free to load the servlet whenever it chooses.
    // Hence choice C is correct while choice B is incorrect.
    //
    // The container must guarantee that servlets marked with lower integers are loaded before servlets marked with higher integers.
    // So cServlet loads first, followed by aServlet and hence choice A is incorrect.
    //
    // The container may choose the order of loading of Servlets with the same load-on-start-up value.
    // From the above explanation, it follows that choice D is incorrect.

}
