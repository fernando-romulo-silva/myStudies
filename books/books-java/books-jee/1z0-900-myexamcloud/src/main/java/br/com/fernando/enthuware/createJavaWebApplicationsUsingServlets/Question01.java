package br.com.fernando.enthuware.createJavaWebApplicationsUsingServlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Question01 {

    // Given the code fragment:

    @WebServlet(urlPatterns = { "/blockIoServlet" }, asyncSupported = true)
    public static class BlockIoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    PrintWriter out = res.getWriter();
	    out.print("Service() method called.");
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    PrintWriter owt = res.getWriter();
	    owt.print("Get() method called.");
	}
    }

    // What output will be returned when this servlet is called a GET request?
    //
    // A - Service() method called.
    //
    // B - Service() method called. Get() method called.
    //
    // C - An HTTP error
    //
    // D - Get() method called.
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
    // Correct Answer is A
    //
    // HttpServlet's service(HttpServletRequest req, HttpServletResponse res) has the code that dispatches the request to appropriate doXXX method.
    // However, in the given code, the servlet has overridden the service method.
    // This code does not do any dispatching of the request.
    //
    // Therefore, only "service() method called" will be printed. doGet() will not be executed.
    // That is why, the service(HttpServletRequest req, HttpServletResponse res) method of a Servlet is almost never overridden.

}
