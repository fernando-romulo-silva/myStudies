package br.com.fernando.enthuware.createJavaWebApplicationsJSPs;

public class Question05 {

    // Which code snippet prints the exception error message as part of the page output?
    //
    //
    // A
    // <%= exception.message %>
    //
    // B
    // <c:out value="${requestScope['javax.servlet.error.exception']}"'/>
    //
    // C
    // <% exception.getMessage(); %>
    //
    // D
    // <% System.out.printIn(e.getMessage()) %>
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
    // The correct answer is B
    //
    // It is not clear whether it is talking about the error page or not. But this will work either way.
    //
    // A is wrong because 'message' is not a public variable in exception.
    //
    // C is wrong because It is valid scriptlet, but it will not print anything.
    //
    // D is wrong because 'e' is not a valid variable name for an implicit variable.
    //
    //
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
