package br.com.fernando.myExamCloud.createJavaWebApplicationsUsingServlets;

public class Question02 {

    // You are assigned to define servlet mappings in web.xml for the following classes.
    //
    // com.epractizelabs.web.OrderServlet
    // com.epractizelabs.web.LicenseServlet
    //
    // Which of the following web.xml fragments are correct?
    //
    //
    // Choice A
    /**
     * <pre>
     *    <servlet>
     *        <servlet-class>com.epractizelabs.web.OrderServlet</servlet-class>
     *        <servlet-name>order</servlet-name>
     *    </servlet>
     *    
     *    <servlet>
     *        <servlet-class>com.epractizelabs.web.LicenseServlet</servlet-class>
     *        <servlet-name>license</servlet-name>
     *    </servlet>
     * </pre>
     */
    //
    //
    // Choice B
    /**
     * <pre>
     * 
     *   <servlet>
     *       <servlet-name>order</servlet-name>
     *       <servlet-class>com.epractizelabs.web.OrderServlet</servlet-class>
     *   </servlet>
     *   
     *   <servlet>
     *       <servlet-name>license</servlet-name>
     *       <servlet-class>com.epractizelabs.web.LicenseServlet</servlet-class>
     *   </servlet>
     * 
     * </pre>
     */
    //
    //
    // Choice C
    /**
     * <pre>
     *    <servlet>
     *        <servlet-name>licenseServlet</servlet-name>
     *        <servlet-class>com.epractizelabs.web.LicenseServlet.class</servlet-class>
     *    </servlet>
     *    
     *    <servlet>
     *        <servlet-name>orderServlet</servlet-name>
     *        <servlet-class>com.epractizelabs.web.OrderServlet.class</servlet-class>
     *    </servlet>
     * </pre>
     */

    //
    //
    // Choice D
    /**
     * <pre>
     *    <servlet>
     *        <name>licenseServlet</name>
     *        <class>com.epractizelabs.web.LicenseServlet<class>
     *    </servlet>
     *    
     *    <servlet>    
     *        <name>orderServlet</name>
     *        <class>com.epractizelabs.web.OrderServlet<class>
     *    </servlet>
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
    // Choice B is correct.
    //
    // Each <servlet> element defines a servlet for the web application.
    // The servlet-name defines the name of the servlet and servlet-class specifies the Java class that should be used by the servlet.
    // The servlet-name must occur before the servlet-class, so choice A is incorrect. 
    // We do not specify the extension .class for the servlet-class element. So choice C is incorrect.
    //
    // Choice D is incorrect because the tag names are wrong.
    // Choice B has all the elements correct and hence is correct.
    /**
     * 
     *  <web-app>
     *   
     *       <servlet>
     *       	<servlet-name>ManageProfile</servlet-name>
     *       	<servlet-class>com.ManageProfileClass</servlet-class>
     *       </servlet>
     *  
     * 	     <servlet-mapping>
     * 	     	 <servlet-name>ManageProfile</servlet-name>
     * 	     	 <url-pattern>/ManageProfile</url-pattern>
     * 	     </servlet-mapping>
     * 
     *  <web-app>
     */    

}
