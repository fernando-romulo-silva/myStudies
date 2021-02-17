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
    // The correct answers is 'A' and 'C'.
    //
    // JSTL stands for JSP Standard Tag Library.
    // JSTL is the standard tag library that provides tags to control the JSP page behaviour.
    // JSTL tags can be used for iteration and control statements, formatation, SQL etc.
    //
    // message localization = internationalisation

    /**
     * <pre>
     *      <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
     *      <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <!-- formatation -->
     *      <html>
     *         <head>
     *         	  <title><fmt:message key="site.title" /></title>
     *         </head>
     *      
     *         <body>
     *    
     *            <c:set var = "salary" scope = "session" value = "${2000*2}"/>
     *            
     *            <c:if test = "${salary > 2000}"> <!-- testing conditions -->
     *               <p>My salary is:  <c:out value = "${salary}"/><p>
     *            </c:if>
     *         
     *            <c:forEach var = "i" begin = "1" end = "5"> <!-- iteration -->   
     *               Item <c:out value = "${i}"/><p>         
     *            </c:forEach>
     *            
     *            <fmt:setLocale value = "es_ES" />
     *            
     *         </body>
     *      </html>
     * </pre>
     */
}
