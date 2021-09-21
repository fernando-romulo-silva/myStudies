package murach.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/headersXML")
public class RequestHeadersXMLServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	StringBuilder returnString = new StringBuilder();
	
	Enumeration headerNames = request.getHeaderNames();
	
	returnString.append("<?xml version='1.0' encoding='UTF-8'?>" + "<!-- Request Header Information-->" + "<headers>");
	
	while (headerNames.hasMoreElements()) {
	    String name = (String) headerNames.nextElement();
	    String value = request.getHeader(name);
	    String header = "<header>" + "<name>" + name + "</name>" + "<value>" + value + "</value>" + "</header>";
	    returnString.append(header);
	}
	
	returnString.append("</headers>");

	response.setContentType("text/xml");

	PrintWriter out = response.getWriter();
	out.println(returnString);
    }
}