package br.com.fernando.myExamCloud.createJavaWebApplicationsJSPs;

public class Question10 {

    // Which of the following error-page declaration is syntactically correct.
    //
    // Choice A
    /**
     * <pre>
     *       <error-page>
     *           <exception-type>com.epractizelabs.web.WebException</exception-type>
     *           <location>/ServletErrorPage</location>
     *       </error-page>
     * </pre>
     */

    // Choice B
    /**
     * <pre>
     *       <error-page>
     *           <exception>com.epractizelabs.web.WebException</exception>
     *           <location>/ServletErrorPage</location>
     *       </error-page>
     * </pre>
     */

    // Choice C
    /**
     * <pre>
     *       <error-page>
     *           <exception>com.epractizelabs.web.WebException</exception>
     *           <location>ServletErrorPage</location>
     *       </error-page>
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
    // Choice A is correct.
    //
    // The error-page contains a mapping between an error code or an exception type to the path of a resource in the Web application.
    // The sub-element exception-type contains a fully qualified class name of a Java exception type.
    // The sub-element location element contains the location of the resource in the web application relative to the root of the web application.
    // The value of the location must have a leading '/'.
}
