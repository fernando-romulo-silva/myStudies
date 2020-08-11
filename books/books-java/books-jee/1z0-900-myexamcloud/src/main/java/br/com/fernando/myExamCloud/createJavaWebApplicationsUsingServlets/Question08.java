package br.com.fernando.myExamCloud.createJavaWebApplicationsUsingServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Question08 {

    // Given the definition of MyServlet:

    public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    HttpSession session = request.getSession();
	    session.setAttribute("myAttribute", "myAttributeValue");
	    session.invalidate(); // 17
	    response.getWriter().println("value=" + session.getAttribute("myAttribute")); // 18
	}
    }

    // Choice A
    // An IllegalStateException is thrown at runtime.
    //
    // Choice B
    // An InvalidSessionException is thrown at runtime.
    //
    // Choice C
    // The string "value=null" appears in the response stream.
    //
    // Choice D
    // The string "value=myAttributeValue" appears in the response stream.
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
    // Choice A is correct.
    // At line #17 the session is invalidated.
    // The method getAttribute will throw java.lang.IllegalStateException hence the call is from invalidated session.

}
