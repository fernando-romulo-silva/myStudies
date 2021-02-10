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
    // If a page has isErrorPage set to true, then the exception implicit scripting language variable of that page is initialized.
    //
    // The variable is set to the value of the javax.servlet.error.exception request attribute value if present, otherwise to the value of
    // the javax.servlet.jsp.jspException request attribute value (for backwards compatibility for JSP pages pre-compiled with a JSP 1.2 compiler).

}
