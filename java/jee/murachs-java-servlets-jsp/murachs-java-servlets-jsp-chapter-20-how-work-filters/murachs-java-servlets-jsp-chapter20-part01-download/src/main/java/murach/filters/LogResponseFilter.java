package murach.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogResponseFilter implements Filter {

    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) {
	this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	chain.doFilter(request, response);

	HttpServletRequest httpRequest = (HttpServletRequest) request;
	HttpServletResponse httpResponse = (HttpServletResponse) response;
	ServletContext sc = filterConfig.getServletContext();

	String logString = filterConfig.getFilterName() + " | ";
	logString += "Servlet path: " + httpRequest.getServletPath() + " | ";
	logString += "Content type: " + httpResponse.getContentType();

	sc.log(logString);
    }

    @Override
    public void destroy() {
	filterConfig = null;
    }
}