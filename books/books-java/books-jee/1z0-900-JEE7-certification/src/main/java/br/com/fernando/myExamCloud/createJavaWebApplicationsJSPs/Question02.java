package br.com.fernando.myExamCloud.createJavaWebApplicationsJSPs;

public class Question02 {

    // Given the JSP code:
    //
    // <% request.setAttribute("orderNumber", "12345"); %>
    //
    // and the Classic tag handler code:
    //
    /**
     * <pre>
     *   <% 
     * 
     * 8.  public int doStartTag() throws JspException { 
     *        ...
     * 20.    // some code here 
     * 21. }
     *    %>
     * </pre>
     */
    //
    // Assume there are no other "orderNumber" attributes in the web application.
    // Which invocation on the pageContext object, inserted at line 9, assigns "12345" to the variable orderID?
    //
    // Choice A - String orderID = (String) pageContext.getAttribute("orderNumber");
    //
    // Choice B - String orderID = (String) pageContext.getRequestScope("orderNumber");
    //
    // Choice C - String orderID = (String) pageContext.getContextScope("orderNumber");
    //
    // Choice D - String orderID = (String) pageContext.getRequest().getAttribute("orderNumber");
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
    // Choice D is correct
    //
    // Because PageContext extends JspContext to provide useful context information for when JSP technology is used in a Servlet environment.
    //
    // A PageContext instance provides access to all the namespaces associated with a JSP page, provides access to several page attributes,
    // as well as a layer above the implementation details. Implicit objects are added to the pageContext automatically.
    //
    // The getRequest method of pageContext return current value of the request object (a ServletRequest).
    
    /**
     * <pre>
     *   <%
     *      public int doStartTag() throws JspException { 
     *      
     *        	ServletRequest servletRequest = pageContext.getRequest(); 
     *        
     *          String orderID = (String) servletRequest.getAttribute("orderNumber");
     *          
     *          ...
     *      }  
     * 
     *   %>
     * </pre>
     */

}
