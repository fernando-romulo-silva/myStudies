package br.com.fernando.myExamCloud.createJavaWebApplicationsUsingServlets;

public class Question15 {
    // Code :
    /**
     * <pre>
     *    <servlet-mapping>
     *        <servlet-name> HelloServlet </servlet-name>
     *        <url-pattern> /hello/hello/* </url-pattern>
     *    </servlet-mapping>
     * </pre>
     */
    //
    // Which of the above requests will not be serviced by HelloServlet?
    // Assume that the WAR file name is 'app.war'. [ Choose two ]
    //
    //
    // Choice A
    // /app/hello/hello/hello
    //
    // Choice B
    // /app/hello/hello/x/y
    //
    // Choice C
    // /app/hello/hello/test.jsp
    //
    // Choice D
    // /app/hello/test.jsp
    //
    // Choice E
    // /app/hello/hello.jsp
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
    // Choice D and E are correct answers.
    //
    // To match a request URI with a servlet, the servlet container identifies the context path and then evaluates the remaining part of the
    // request URI with the servlet mappings specified in the deployment descriptor.
    //
    // It tries to recursively match the longest path by stepping down the request URI path tree a directory at a time, using the '/' character
    // as a path separator, and determining if there is a match with a servlet.
    //
    // If there is a match, the matching part of the request URI is the servlet path and the remaining part is the path info.
    //
    // In this case, when the servlet encounters any request with the path "/app/hello/hello", it maps that request to HelloServlet. 
    //
    // In choices A, B, and C this path is present, hence they are serviced by HelloServlet.
    //
    // Choices D and E do not have this complete path, so they are not serviced.
    //
    // Hence choices D and E are correct while choices A, B, and C are incorrect.
}
