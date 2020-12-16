package br.com.fernando.myExamCloud.createJavaWebApplicationsUsingServlets;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Question16 {

    // How can a servlet indicate to the browser that cookie data should be sent over a secure connection?
    // You had to select 1 option(s)
    //
    // A
    // Call the setSecure(true) method on the cookie object.
    //
    // B
    // Encrypt the cookie data. The browser automatically sends encrypted data over a secure connection.
    //
    // C
    // Set the ENCRYPT header in the response.
    //
    // D
    // Configure SessionTrackingMode.SSL on the ServletContext object.
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
    // The correct answer is A
    //
    // public void setSecure(boolean flag) Indicates to the browser whether the cookie should only be sent using a secure protocol, such as HTTPS or SSL.
    // The default value is false. If true, sends the cookie from the browser to the server only when using a secure protocol; 
    // if false, sent on any protocol
    //
    // D is wrong because This is a way to ensure that session is tracked using a particular mechanism. i.e. using COOKIE, using URL rewriting, or using SSL.
    // It is possible to invoke ServletContext.setSessionTrackingMode(SessionTrackingMode.SSL); . 
    // This will ensure all data including cookies will be sent over SSL.
    //
    // 
    public class FirstServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
	    try {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String n = request.getParameter("userName");
		out.print("Welcome " + n);

		Cookie cookie = new Cookie("key", "value"); // creating cookie object
		cookie.setSecure(true); // sends the cookie from the browser to the server only when using a secure protocol;
		
		response.addCookie(cookie);// adding cookie in the response

		// creating submit button
		out.print("<form action='servlet2'>");
		out.print("<input type='submit' value='go'>");
		out.print("</form>");

		out.close();

	    } catch (Exception e) {
		System.out.println(e);
	    }
	}
    }
    
    
}
