package br.com.fernando.enthuware.createJavaWebApplicationsUsingServlets;

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

public class Question06 {

    // Your web application requires logic to remember items that a user placed into a shopping cart.
    //
    // Which of the following mechanisms should you use to associate that information with the user?
    // You had to select 2 option(s)
    //
    // A
    // HttpServletResponse objects
    //
    // B
    // ServletContext objects
    //
    // C
    // HttpSession objects
    //
    // D
    // a database
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
    // B and D is the correct answer
    //
    @WebServlet(urlPatterns = { "/blockIoServlet" }, asyncSupported = true)
    public static class BlockIoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
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
    //
    // It is not clear how long do you need to remember the items.
    // If you require it to remember only until the user is still browsing, then HttpSession is the right answer. Otherwise, ServletContext is the right answer.
    // You can always store this information in the database.
    //
    // Information stored in a ServletContext remains as long as the servlet exists.
    // So, you could potentially store a map of userid and list of items in the servlet context.
    //
    // C is wrong because If you storing items for a user in a session object,
    // it will be available only until the user session is alive. But the requirement is to remember the items for later use
    //
    //
    // ** We can save cart information in database using JPA/JDBC, however the questions asks you to choose Java EE web tier mechanisms, hence choice D is also incorrect.
}
