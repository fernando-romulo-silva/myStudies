package br.com.fernando.myExamCloud.createJavaWebApplicationsUsingServlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Question07 {

    // Given the code fragment:

    @WebServlet(urlPatterns = { "/blockIoServlet" }, asyncSupported = true)
    public static class BlockIoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
	    super.init();
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    // super.service(req, res); // 14 
	    PrintWriter out = res.getWriter();
	    out.print("Service() method called.");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    PrintWriter out = res.getWriter();
	    out.print("Get() method called.");
	}
	
	@Override
	public void destroy() {
	    super.destroy();
	}
    }
    // Choice A
    // Service() method called. 
    //
    // Choice B
    // Service() method called.Get() method called.
    //
    // Choice C
    // An HTTP error
    //
    // Choice D
    // GET() method called.
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
    // Choice A is correct. // If 14 is commented or B not
    //
    // A servlet life cycle can be defined as the entire process from its creation till the destruction. 
    // The following are the paths followed by a servlet.
    //
    // * The servlet is initialized by calling the init() method.
    //
    // * The servlet calls service() method to process a client's request. 
    // Each time the server receives a request for a servlet, the server spawns a new thread and calls service. 
    // The service() method checks the HTTP request type (GET, POST, PUT, DELETE, etc.) and calls doGet, doPost, doPut, doDelete, etc. methods as appropriate.
    //
    // * The servlet is terminated by calling the destroy() method.
    //
    // Finally, servlet is garbage collected by the garbage collector of the JVM.

}
