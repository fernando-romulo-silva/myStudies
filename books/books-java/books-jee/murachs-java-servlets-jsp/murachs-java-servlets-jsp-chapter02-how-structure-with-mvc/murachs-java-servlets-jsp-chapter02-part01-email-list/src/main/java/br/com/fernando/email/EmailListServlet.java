package br.com.fernando.email;

import java.io.IOException;

import br.com.fernando.business.User;
import br.com.fernando.data.UserDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


// https://ftp.unicamp.br/pub/apache/tomcat/tomcat-10/v10.0.5/bin/apache-tomcat-10.0.5.tar.gz
public class EmailListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	String url = "/index.html";

	// get current action
	String action = request.getParameter("action");
	if (action == null) {
	    action = "join"; // default action
	}

	// perform action and set URL to appropriate page
	if (action.equals("join")) {
	    url = "/index.html"; // the "join" page
	} else if (action.equals("add")) {
	    // get parameters from the request
	    String firstName = request.getParameter("firstName");
	    String lastName = request.getParameter("lastName");
	    String email = request.getParameter("email");

	    // store data in User object and save User object in database
	    User user = new User(firstName, lastName, email);
	    UserDB.insert(user);

	    // set User object in request object and set URL
	    request.setAttribute("user", user);
	    url = "/thanks.jsp"; // the "thanks" page
	}

	// forward request and response objects to specified URL
	getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request, response);
    }
}