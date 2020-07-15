package br.com.fernando.myExamCloud.createJavaWebApplicationsJSPs;

public class Question04 {

    // Which of the following is the parent element of <error-page> element?
    //
    // Choice A - error-code
    //
    // Choice B - servlet
    //
    // Choice C - servlet-mapping
    //
    // Choice D - web-app
    //
    // Choice E - exception-type
    //
    //
    // Explanation :
    // Choice D is correct.
    //
    // The error-page element contains a mapping between an error code or exception type and the path of a resource in the web application.
    // It is declared directly under <web-app> element.
    /**
     * <pre>
     *    <web-app>
     *        ...
     *        <error-page>
     *            <error-code>404</exception-type>
     *            <location>/errors/404.jsp</location>
     *        </error-page>
     *         ...
     *    </web-app>
     * </pre>
     **/
}
