package murach.filters;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class LogResponseCookiesFilter implements Filter {

    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) {
	this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	HttpServletResponse httpResponse = (HttpServletResponse) response;
	ResponseCookiesWrapper wrappedResponse = new ResponseCookiesWrapper(httpResponse);

	chain.doFilter(request, wrappedResponse);

	HttpServletRequest httpRequest = (HttpServletRequest) request;
	ServletContext sc = filterConfig.getServletContext();
	String filterName = filterConfig.getFilterName();
	String servletPath = "Servlet path: " + httpRequest.getServletPath();
	ArrayList<Cookie> cookies = wrappedResponse.getCookies();
	String cookiesString = "";
	for (Cookie c : cookies) {
	    cookiesString += c.getName() + "=" + c.getValue() + " ";
	}

	sc.log(filterName + " | " + servletPath + " | cookies: " + cookiesString);
    }

    @Override
    public void destroy() {
	filterConfig = null;
    }

    class ResponseCookiesWrapper extends HttpServletResponseWrapper {

	private ArrayList<Cookie> cookies = null;

	public ResponseCookiesWrapper(HttpServletResponse response) {
	    super(response);
	    cookies = new ArrayList<Cookie>();
	}

	// provide a new method for the ResponseWrapper class
	public ArrayList<Cookie> getCookies() {
	    return cookies;
	}

	// override the addCookie method of the HttpServletResponse object
	@Override
	public void addCookie(Cookie cookie) {
	    // store the cookie in the response wrapper object
	    cookies.add(cookie);

	    // store the cookie in the original response object
	    HttpServletResponse httpResponse = (HttpServletResponse) this.getResponse();
	    httpResponse.addCookie(cookie);
	}
    }
}