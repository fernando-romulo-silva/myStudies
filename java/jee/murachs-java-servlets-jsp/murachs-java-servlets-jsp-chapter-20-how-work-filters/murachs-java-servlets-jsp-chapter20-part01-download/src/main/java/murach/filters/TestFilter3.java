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

public class TestFilter3 implements Filter {

    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) {
	this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	HttpServletRequest httpRequest = (HttpServletRequest) request;
	ServletContext sc = filterConfig.getServletContext();

	String filterName = filterConfig.getFilterName();
	String servletPath = "Servlet path: " + httpRequest.getServletPath();

	sc.log(filterName + " | " + servletPath + " | before request");

	chain.doFilter(httpRequest, response);

	sc.log(filterName + " | " + servletPath + " | after request");
    }

    @Override
    public void destroy() {
	filterConfig = null;
    }
}