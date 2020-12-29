package br.com.fernando.enthuware.createJavaWebApplicationsUsingServlets;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Question07 {

    // Given the following code:

    @WebServlet(urlPatterns = { "/myServlet" }, asyncSupported = true)
    public class NonBlockingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    AsyncContext ac = req.startAsync();
	    // line 1
	}
    }

    // What should you do at line 1 to enable this servlet receive request data without blocking?
    // You had to select 1 option(s)
    //
    // A
    // Use a Runnable instance with the start() method of AsyncContext.
    //
    // B
    // Define a ReadListener and assign it to the request input stream.
    //
    // C
    // Create a Callable class and delegate this operation to a ManagedExecutorService by using the dispatch method of AsyncContext.
    //
    // D
    // Define an AsyncListener and assign it to the AsyncContext object.
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
    // The correct answer is B
    //
    protected void processRequestB(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	// it is not clear what do they mean by "assign it to the request input stream". You can do something like this:
	AsyncContext ac = req.startAsync();
	
	ServletInputStream in = req.getInputStream();

	req.getInputStream().setReadListener(new MyReadListener(ac, res, in));
    }

    // A is wrong because this has nothing to do with reading receiving request data without blocking.
    protected void processRequestA(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	final AsyncContext ac = req.startAsync();
	ac.start(new Runnable() {
	    public void run() {
		System.out.println("Handling request");
		// do something
		ac.complete();
	    }
	});
    }

    // C is wrong because The dispatch method dispatches the request and response objects of this AsyncContext to another resource.
    // It has nothing to do with Callable and ManagedExecutorService.

    // D is wrong because An AsyncListener is used if you want to be notified in the event that an asynchronous operation initiated on a ServletRequest
    // to which the listener had been added has completed, timed out, or resulted in an error.

    // -------------------
    class MyReadListener implements ReadListener {

	private final AsyncContext ctx;
	private final HttpServletResponse resp;
	private final ServletInputStream in;
	private LinkedList<String> parts = new LinkedList<>();

	public MyReadListener(AsyncContext ctx, HttpServletResponse resp, ServletInputStream in) {
	    super();
	    this.ctx = ctx;
	    this.resp = resp;
	    this.in = in;
	}

	@Override
	public void onDataAvailable() throws IOException {
	    try {
		int len = -1;
		byte b[] = new byte[1024];

		while (in.isReady() && (len = in.read(b)) != -1) {
		    String data = new String(b, 0, len);
		    System.out.println("onDataAvailable: \"{}\"" + data);
		    parts.add(data);
		}
	    } catch (IOException e) {
		System.out.println(e);
	    }
	}

	@Override
	public void onAllDataRead() throws IOException {
	    System.out.println("onAllDataRead");
	    // Stream response back
	    ServletOutputStream out = resp.getOutputStream();
	    out.setWriteListener(new MyWriteListener(ctx, out, parts));
	}

	@Override
	public void onError(Throwable t) {
	    System.out.println(t);
	    try {
		resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    } catch (IOException ignore) {
		System.out.println(ignore);
	    }
	    ctx.complete();
	}
    }

    public static class MyWriteListener implements WriteListener {
	private final AsyncContext ctx;
	private final ServletOutputStream out;
	private final Deque<String> parts;

	public MyWriteListener(AsyncContext ctx, ServletOutputStream out, Deque<String> parts) {
	    this.ctx = ctx;
	    this.out = out;
	    this.parts = parts;
	}

	@Override
	public void onWritePossible() throws IOException {
	    String part;
	    try {
		while ((part = parts.pop()) != null && out.isReady()) {
		    out.print("[" + part + "]\n");
		}
	    } catch (NoSuchElementException e) {
		ctx.complete();
	    }
	}

	@Override
	public void onError(Throwable t) {
	    ctx.complete();
	}
    }
}
