package br.com.fernando;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.ServerEndpoint;

public class ComponentsType {
    // -------------------------------------------------------------------------------------------------------------------
    // Web Sockets Components
    //
    // This class level annotation signifies that the Java class it decorates must be deployed by the implementation as a websocket server endpoint and
    // made available in the URI-space of the websocket implementation.
    //
    // The class must be public, concrete, and have a public no-args constructor.
    //
    // The class may or may not be final, and may or may not have final methods.
    //
    // Choice C
    @ServerEndpoint("/hello")
    public final class HelloServerC {
	public HelloServerC() {
	}
    }

    //
    // Choice D
    @ServerEndpoint("/hello")
    public class HelloServerD {
	public HelloServerD() {
	}
    }
    //
    // -------------------------------------------------------------------------------------------------------------------
    // Servlets
    // 
    @WebServlet(urlPatterns = { "/blockIoServlet" }, asyncSupported = true)
    public static class BlockIoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
	    super.init();
	}
	
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    PrintWriter out = res.getWriter();
	    out.print("Service() method called.");
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    PrintWriter owt = res.getWriter();
	    owt.print("Get() method called.");
	}
	
	@Override
	public void destroy() {
	    super.destroy();
	}
    }
    //
    // -------------------------------------------------------------------------------------------------------------------
    // CDI
    //
    // CDI can't be final class
}
