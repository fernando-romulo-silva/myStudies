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

}
