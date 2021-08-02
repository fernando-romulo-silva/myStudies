package br.com.fernando.enthuware.createJavaWebApplicationsJSPs;

public class Question02 {

    // Which of the following approaches would result in the current date being added to the output of a JSP?
    // You had to select two options(s)
    //
    // A
    // <%= out.printIn(new java.util.Date()) %>
    //
    // B
    // <% out.printIn(new java.util.Date()); %>
    //
    // C
    // <%=new java.util.Date()%>
    //
    // D
    // <%System.out.printIn(new java.util.Date());%>
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
    // The B and C are the correct answer.
    //
    // Choice B is correct. The code will print current date. This is the JSP scriptlet tag where Java code can be used.
    //
    // Choice C is correct. This is the JSP expression tag.
    //
    // The code placed within JSP expression tag is written to the output stream of the response.
    // So you need not write out.print() to write data.
    //
    // It is mainly used to print the values of variable or method.
    //
    // A will not compile, void type will not be used in <%= %> tag.
    //
    // D Will print the date in the servlet log

}
