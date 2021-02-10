package br.com.fernando.myExamCloud.createJavaWebApplicationsJSPs;

public class Question05 {

    // Which two features are provided by the JSTL Core library for a JSP?
    //
    // Choice A - iteration over a collection
    //
    // Choice B - buffering of large result sets
    //
    // Choice C - testing conditions
    //
    // Choice D - message localization
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
    // The correct answers is 'A' and 'C'
    //
    // JSTL stands for JSP Standard Tag Library.
    // JSTL is the standard tag library that provides tags to control the JSP page behaviour.
    // JSTL tags can be used for iteration and control statements, internationalisation, SQL etc.

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
