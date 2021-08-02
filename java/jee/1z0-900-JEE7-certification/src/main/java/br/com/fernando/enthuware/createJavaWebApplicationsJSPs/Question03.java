package br.com.fernando.enthuware.createJavaWebApplicationsJSPs;

public class Question03 {

    // Which code snippet prints the exception error message as part of the page output?
    // You have to select 1 option
    //
    // A
    // <%@page errorPage="errorHandler.jsp" %>
    //
    // B
    // <%= requestScope['javax.servlet.error'] !=null %>
    //
    // C
    // <%@page isErrorPage="true" %>
    //
    // D
    // <c:set var="errorHandler" value="true" />
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
    // The correct answer is C
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
