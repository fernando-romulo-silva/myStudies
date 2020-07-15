package br.com.fernando.myExamCloud.secureJavaEE7Applications;

public class Question01 {

    // Which of the following code defines login-config for Form-based authentication?
    //
    // Choice A
    /**
     * <pre>
     *   <login-config>
     *       <auth-method>FORM</auth-method>
     *       <realm-name>file</realm-name>
     *       <form-login-config>
     *           <form-login-page>/logon.jsp</form-login-page>
     *           <form-error-page>/logonError.jsp</form-error-page>
     *       </form-login-config>
     *   </login-config>
     * </pre>
     **/
    //
    // Choice B
    /**
     * <pre>
     *      
     *   <login-config>
     *       <auth-method>FORM</auth-method>
     *       <realm-name>file</realm-name>
     *       <form-config>
     *           <login-page>/logon.jsp</form-login-page>
     *           <error-page>/logonError.jsp</form-error-page>
     *       </form-config>
     *   </login-config>
     * </pre>
     */
    //
    // Choice C
    /**
     * <pre>
     *  
     *  <login-config>
     *      <auth-method>FORM</auth-method>
     *      <realm>file</realm>
     *      <form-login-config>
     *          <form-login-page>/logon.jsp</form-login-page>
     *          <form-error-page>/logonError.jsp</form-error-page>
     *      </form-login-config>
     *  </login-config>
     * </pre>
     */
    //
    // Choice D
    /**
     * <pre>
     *   <login-config>
     *       <authentication-method>FORM</authentication-method>
     *       <realm-name>file</realm-name>
     *       <form-login-config>
     *           <form-login-page>/logon.jsp</form-login-page>
     *           <form-error-page>/logonError.jsp</form-error-page>
     *       </form-login-config>
     *   </login-config>
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
    // Explanation :
    // Choice A is correct.
    //
    // The main advantage of form based authentication is the customization of look and feel of login and error pages.
    //
    // A custom HTML form is used to capture the username and password, which leads to this customization.
    // The web application deployment descriptor contains entries for a login form and error page.
    //
    // The login form must contain fields for entering a username and a password. These fields must be named j_username and j_password, respectively.
    // Typical form login pag

}
