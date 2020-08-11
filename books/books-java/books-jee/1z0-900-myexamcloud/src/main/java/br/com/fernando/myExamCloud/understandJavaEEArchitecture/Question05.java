package br.com.fernando.myExamCloud.understandJavaEEArchitecture;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Question05 {

    // Your web application requires logic to remember items that a user placed into a shopping cart.
    // Which two Java EE web tier mechanisms should you use to associate that information with the user?
    // [ Choose two ]
    //
    // Choice A
    // HttpServletResponse objects
    //
    // Choice B
    // ServletContext objects
    //
    // Choice C
    // HttpSession objects
    //
    // Choice D
    // Database
    //
    //
    // Choice B and C are correct answers.
    //
    @WebServlet(urlPatterns = { "/blockIoServlet" }, asyncSupported = true)
    public static class BlockIoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ServletContext context;

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
	    PrintWriter out = response.getWriter();
	    out.print("Get() method called.");


	    ServletContext ctx = request.getServletContext();
	    ctx.setAttribute("map", Collections.singletonMap(7, "Seven"));

	    // And later...

	    @SuppressWarnings("unchecked")
	    Map<Integer, String> map = (Map<Integer, String>) ctx.getAttribute("map");
	    String value = map.get(7); // "Seven"
	    System.out.println(value);
	    
	    // or
	    
	    HttpSession session = request.getSession();
	    session.setAttribute("MySessionVariable", "myParam");
	}
    }

    // An object of ServletContext is created by the web container at time of deploying the project.
    // We can use setAttribute(String name, Object value) to save user cart info within ServletContext object.
    // HttpSession provides a way to identify a user across more than one page request or visit to a Web site and to store information about that use.
    // The shopping cart information can be saved in session object.
    // We cannot store any information in HttpServletResponse between page navigations, hence Choice A is incorrect.
    //
    // We can save cart information in database using JPA/JDBC, however the questions asks you to choose Java EE web tier mechanisms, hence choice D is also incorrect.

}
