package br.com.fernando.myExamCloud.createJavaWebApplicationsJSPs;

public class Question06_Arrumar {

    // Consider the following code snippets.
    //
    // index.html
    /**
     * <pre>
     *       <html> 
     *         <body >
     *           <form method="post" action="index.jsp">
     *             Guest Name <input type="text" name="guestName"> 
     *             <input type="submit" name="Submit" value="Enter Site"> 
     *           </form>
     *         </body>
     *       </html>
     * </pre>
     */
    //
    //
    // GuestBean.java
    /**
     * <code>
     * package com.epractizelabs.site;
     * 
     * public class GuestBean {
     *     private String guestName;
     * 
     *     public GuestBean(){}
     * 
     *     public String getGuestName() {
     * 	       return guestName;
     *     }
     * 
     *     public void setGuestName(String guestNameIn) {
     * 	       guestName = guestNameIn;
     *     }
     * }
     * </code>
     */
    //
    // index.jsp
    /**
     * <pre>
     *       <jsp:useBean id="guestBean" scope="request" class="com.epractizelabs.site.GuestBean" >
     *          <jsp:setProperty name="guestBean" property="guestName" /> 
     *       </jsp:useBean>
     *       <html> 
     *         <body> 
     *           <jsp:getProperty name="guestBean" property="guestName" /> 
     *         </body> 
     *       </html>
     * </pre>
     */
    //
    // What will be the output if a user enters 'Brain Christopher' in the HTML form and clicks the Enter Site button?
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
    // The JSP page (index.jsp) uses GuestBean as specified by jsp:useBean action. On submitting the HTML form, index.jsp is invoked.
    //
    // The JSP page also uses jsp:setProperty and jsp:getProperty actions.
    //
    // The jsp:setProperty action is used in conjunction with the jsp:useBean action to set the value of bean properties used by JSP page.
    //
    // The property of the Java Bean can also be set as follows: <jsp:setProperty name="guestBean" property="guestName" value="<%=expression %>" />.
    //
    // In the given code, GuestBean's bean property "guestName" is matched with the "guestName" as the HTML form input parameter which comes as a part of the request object.
    //
    // Thus when user inputs "Brain Christopher" and submits it, the value of guestName element of GuestBean is set to "Brain Christopher".
    // Finally jsp:getProperty action is used by index.jsp to print the name.
}
