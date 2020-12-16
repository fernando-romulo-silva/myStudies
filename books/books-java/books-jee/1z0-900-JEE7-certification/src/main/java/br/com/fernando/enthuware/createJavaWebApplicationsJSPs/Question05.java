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
    // The correct answer is B
    //
    // It is not clear whether it is talking about the error page or not. But this will work either way.
    // 
    // A is wrong because 'message' is not a public variable in exception.
    // 
    // C is wrong because It is valid scriptlet, but it will not print anything. 
    //
    // D is wrong because 'e' is not a valid variable name for an implicit variable.
}
