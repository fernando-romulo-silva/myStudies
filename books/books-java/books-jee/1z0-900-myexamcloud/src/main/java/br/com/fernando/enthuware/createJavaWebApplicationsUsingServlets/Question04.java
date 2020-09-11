package br.com.fernando.enthuware.createJavaWebApplicationsUsingServlets;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Question04 {

    // Given the code fragment:
    public void useCookie() {
	Cookie cookie = new Cookie("key", "value");
	cookie.setMaxAge(-1);
    }

    // How long does this cookie persist?
    // You have select 1 option
    //
    // A
    // until server shutdown
    //
    // B
    // until garbage collection in the servlet instance
    //
    // C
    // this request
    //
    // D
    // until browser shutdown
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
    // The correct answer is D
    //
    // A cookie is a small piece of information that is persisted between the multiple client requests.
    // A cookie has a name, a single value, and optional attributes such as a comment, path and domain qualifiers, a maximum age, and a version number.
    public class FirstServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
	    try {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String n = request.getParameter("userName");
		out.print("Welcome " + n);

		Cookie cookie = new Cookie("key", "value"); // creating cookie object
		response.addCookie(cookie);// adding cookie in the response
		cookie.setMaxAge(-1); //

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
    //
    //
    // public void setMaxAge(int expiry)
    // Sets the maximum age in seconds for this Cookie.
    //
    // A positive value indicates that the cookie will expire after that many seconds have passed.
    // Note that the value is the maximum age when the cookie will expire, not the cookie's current age.
    //
    // A negative value means that the cookie is not stored persistently and will be deleted when the Web browser exits.
    // A zero value causes the cookie to be deleted.
}
