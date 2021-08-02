package br.com.fernando.enthuware.createJavaWebApplicationsJSPs;

public class Question08 {

    // Which of the following features are provided by the JSTL Core library for a JSP?
    // You had to select 2 options
    //
    // A - iteration over a collection
    //
    // B - buffering of large result sets
    //
    // C - testing conditions
    //
    // D - message localization
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
    // The correct answer is A and C
    //
    // D is wrong because Tags for I18N capable formatting are in http://java.sun.com/jsp/jstl/fmt
    //
    // FunctionalArea ------------ URI ------------------------------------ Prefix
    //
    // core ---------------------- http://java.sun.com/jsp/jstl/core ------ c
    // XML processing ------------ http://java.sun.com/jsp/jstl/xml ------- xml
    // I18N capable formatting --- http://java.sun.com/jsp/jstl/fmt ------- fmt
    // relational db access (SQL)- http://java.sun.com/jsp/jstl/sql ------- sql
    // Functions ----------------- http://java.sun.com/jsp/jstl/functions - fn

    /**
     * <pre>
     *      <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
     *      <html>
     *         <head>
     *            <title>Example</title>
     *         </head>
     *      
     *         <body>
     *    
     *            <c:set var = "salary" scope = "session" value = "${2000*2}"/>
     *            
     *            <c:if test = "${salary > 2000}">
     *               <p>My salary is:  <c:out value = "${salary}"/><p>
     *            </c:if>
     *         
     *            <c:forEach var = "i" begin = "1" end = "5">         
     *               Item <c:out value = "${i}"/><p>         
     *            </c:forEach>
     *            
     *         </body>
     *      </html>
     * </pre>
     */
}
