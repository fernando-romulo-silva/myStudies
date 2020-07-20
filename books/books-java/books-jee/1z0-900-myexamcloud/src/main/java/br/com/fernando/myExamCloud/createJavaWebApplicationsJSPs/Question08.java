package br.com.fernando.myExamCloud.createJavaWebApplicationsJSPs;

public class Question08 {

    // Which JSP code snippet indicates that the page you are designing is capable of handling errors?
    //
    // Choice A
    // <%@page errorPage="errorHandler.jsp"%>
    //
    // Choice B
    // <%= requestScope['javax.servlet.error'] !=null %>
    //
    // Choice C
    // <%@page isErrorPage="true"%>
    //
    // Choice D
    // <c:set var="errorHandler" value="true"/>
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
    // Choice C is correct.
    //
    // The page directive tag <%@ page isErrorPage="true" %> indicates that it is serving as an error page.
    // This directive makes an object of type javax.servlet.jsp.ErrorData available to the error page so that we can retrieve, interpret, and possibly display
    // information about the cause of the exception in the error page.
    // We can access the error data object in an EL expression by way of the page context.
    // Thus, ${pageContext.errorData.statusCode} retrieves the status code, and ${pageContext.errorData.throwable} retrieves the exception.
    //
    // The directive tag <%@ page errorPage="file-name" %> at the beginning of JSP page helps the container to forward control to an error page if an exception occurs.
    // The <%= requestScope['javax.servlet.error'] !=null %> will not compile.
    // The <c:set var="errorHandler" value="true"/> tag EL version of setProperty action.
    // The tag is helpful because it evaluates an expression and uses the results to set a value of a JavaBean or a java.util.Map object.
}
