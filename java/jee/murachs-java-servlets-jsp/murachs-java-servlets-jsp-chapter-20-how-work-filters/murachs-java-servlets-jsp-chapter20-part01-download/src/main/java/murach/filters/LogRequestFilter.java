package murach.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import murach.util.CookieUtil;

public class LogRequestFilter implements Filter {

    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) {
	this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	HttpServletRequest httpRequest = (HttpServletRequest) request;
	ServletContext sc = filterConfig.getServletContext();

	String logString = filterConfig.getFilterName() + " | ";
	logString += "Servlet path: " + httpRequest.getServletPath() + " | ";

	Cookie[] cookies = httpRequest.getCookies();
	String emailAddress = CookieUtil.getCookieValue(cookies, "emailCookie");
	logString += "Email cookie: ";
	if (emailAddress.length() != 0) {
	    logString += emailAddress;
	} else {
	    logString += "Not found";
	}

	sc.log(logString);

	chain.doFilter(httpRequest, response);
    }

    @Override
    public void destroy() {
	filterConfig = null;
    }
}