package murach.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestInitParamsFilter implements Filter {

    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) {
	this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	ServletContext sc = filterConfig.getServletContext();

	String filterName = filterConfig.getFilterName();

	String logFilename = filterConfig.getInitParameter("logFilename");

	sc.log(filterName + " | logFilename: " + logFilename);

	chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
	filterConfig = null;
    }
}