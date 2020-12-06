package br.com.fernando.myExamCloud.createJavaWebApplicationsJSPs;

public class Question14 {

    // Given:
    //
    // licenseRequest.jsp
    // <jsp:useBean id="licenseBean" class="LicenseBean" />
    // <jsp:include page="orderDisplay.jsp" />
    //
    // orderDisplay.jsp
    // Your order number : <jsp:getProperty name="licenseBean" property="orderNumber" /><br>
    // Your Email ID: <jsp:getProperty name="licenseBean" property="email" />
    //
    // A developer wants to display order number and email address in orderDisplay.jsp.
    // What modification should be made in licenseRequest.jsp to make the employee bean accessible within orderDisplay.jsp?
    //
    // Choice A 	
    // No modification is needed; the bean will be available in the included page also.
    //
    // Choice B 	
    // Modify the code to use the include directive instead of the include action.
    //
    // Choice C 	
    // Change the scope of the bean to request.
    //
    // Choice D 	
    // Change the scope of the bean to session.
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
    // By default, the scope of a bean is page, so the bean will not be available in the dynamically included page.So choice A is incorrect.
    //
    // In a static inclusion (include directive), the two pages become one translation unit. 
    // So the bean will be available in the included page when the include directive is used. Hence choice B is correct.
    //
    // Choices C and D are incorrect because even if the scope of the bean is changed to session or request, the bean instance would be 
    // available in the included jsp file only if that bean also contains a useBean declaration identical to the one in the including file.
    //
    // JSP include action tag (non static incluse) 
    //
    // index.jsp
    /**
     * <pre>
     *     <html> 
     *     <head>
     *     	<title>JSP Include Action example</title>
     *     </head>
     *     <body> 
     *     	<b>index.jsp Page</b><br>
     *     	<jsp:include page="Page2.jsp" /> 
     *     </body> 
     *     </html>
     * </pre>
     */
    // Page2.jsp
    /**
     * <pre>
     *     <html> 
     *     <head>
     *     	<title>JSP Include example</title>
     *     </head>
     *     <body> 
     *     	<b>Page2.jsp</b><br>
     *     	<i> This is the content of Page2.jsp page</i>
     *     </body> 
     *     </html>
     * </pre>
     */
    //
    //
    //
    //
    // Jsp Include Directive (static incluse) 
    //
    // index.jsp
    /**
     * <pre>
     *     <html> 
     *     <head>
     *     	<title>JSP Include Directive example</title>
     *     </head>
     *     <body> 
     *     	<%@ include file="Page2.jsp" %>  
     *     </body> 
     *     </html>
     * </pre>
     */

    

}
