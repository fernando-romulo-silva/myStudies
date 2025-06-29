package murach.http;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/headersHTML")
public class RequestHeadersHTMLServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Enumeration<String> headerNames = request.getHeaderNames();
	Map<String, String> headers = new HashMap<String, String>();
	while (headerNames.hasMoreElements()) {
	    String headerName = headerNames.nextElement();
	    headers.put(headerName, request.getHeader(headerName));
	}
	request.setAttribute("headers", headers);
	getServletContext().getRequestDispatcher("/headers.jsp").forward(request, response);
    }
}