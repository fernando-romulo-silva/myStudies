package br.com.fernando.myExamCloud.createJavaWebApplicationsJSPs;

public class Question09 {

    // Which code snippet prints the exception error message as part of the page output?
    //
    //
    // Choice A
    // <%= exception.message %>
    //
    // Choice B
    // <c:out value="${requestScope['javax.servlet.error.exception']}"/>
    //
    // Choice C
    // <% exception.getMessage(); %>
    //
    // Choice D
    // <% System.out.println(exception.getMessage()) %>
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
    // Choice B is correct.
    // 
    // A list of attributes stored in the request scope for errors.
    //
    // * javax.servlet.error.status_code: An integer that tells you the error status code.
    // * javax.servlet.error.exception_type: An instance of a class that points to the type of exception that caused the error.
    // * javax.servlet.error.message: A string that tells the exception message, which is sent to the exception constructor.
    // * javax.servlet.error.exception: An object that can be discarded if the actual exception is lost.
    // * javax.servlet.error.request_uri: A string that tells the URI of the resource that caused the problem.
    //
    // If we write an error page in JSTL, the above properties can be accessed like this:
    // <c: out value = "$ {requestScope ['javax.servlet.error.message']}" />
    // <c: out value = "$ {requestScope ['javax.servlet.error.exception]}" />
    //
    // Choice A is incorrect. The code will not compile.
    // Choice C is incorrect. This is the scriptlet tag and it will not print anything on the page output.
    // Choice D is incorrect. This will print error message in server console and not on page output.
    //
    //
    // A JSP is considered an Error Page if it sets the page directive's isErrorPage attribute to true (default value of this attribute is false).
    //
    // The isErrorPage attribute indicates that the current JSP can be used as the error page for another JSP.
    // The exception object is created only if the JSP uses the page directive to set isErrorPage set to true.
    // When a JSP generates an error and forwards that error to the error page, the container sets the JSP exception object of the error page to the generated error
    //
    // If a page has isErrorPage set to true, then the exception implicit scripting language variable of that page is initialized.
    //
    // The variable is set to the value of the javax.servlet.error.exception request attribute value if present, otherwise to the value of
    // the javax.servlet.jsp.jspException request attribute value (for backwards compatibility for JSP pages pre-compiled with a JSP 1.2 compiler).
    //

    /**
     * page1.jsp
     * 
     * <pre>
     *     <%@page errorPage="errorpage.jsp" %>
     * 
     *     <%
     *       //this has your code that throws some exception
     *     
     *     %>
     * 
     * </pre>
     */

    /**
     * errorpage.jsp
     * 
     * <pre>
     * 
     *       <%@ page isErrorPage='true' %> 
     *       <%
     *       	out.print("Error Message : ");  
     *       	out.print(exception.getMessage());
     *       %>
     *       
     *       // or
     *       
     *       <c:out value="${requestScope['javax.servlet.error.exception']}"' />
     * 
     * </pre>
     */    
}
