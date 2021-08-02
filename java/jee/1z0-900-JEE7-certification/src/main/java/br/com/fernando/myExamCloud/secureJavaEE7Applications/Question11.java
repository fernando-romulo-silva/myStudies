package br.com.fernando.myExamCloud.secureJavaEE7Applications;

public class Question11 {

    // Which web.xml element can be used to define authorization?
    //
    //
    // Choice A
    // user-data-constraint
    //
    // Choice B
    // auth-constraint
    //
    // Choice C
    // transport-guarantee
    //
    // Choice D
    // login-config
    //
    // Choice E
    // web-resource-collection
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
    // Choice B is correct
    //
    // The auth-constraint indicates the user roles that should be permitted access to a resource collection.
    // It is defined as follows: Only ADMIN role can access applications with the url "/admin/*".
    /**
     * <pre>
     *      <security-constraint>
     *          <web-resource-collection>
     *              <web-resource-name>admin</web-resource-name>
     *              <url-pattern>/admin/*</url-pattern>
     *          </web-resource-collection> 
     *          <auth-constraint>
     *              <role-name>ADMIN</role-name>
     *          </auth-constraint>
     *      </security-constraint>
     * </pre>
     */
}
