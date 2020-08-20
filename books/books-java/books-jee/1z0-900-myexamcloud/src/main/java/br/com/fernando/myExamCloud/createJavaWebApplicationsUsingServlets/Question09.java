package br.com.fernando.myExamCloud.createJavaWebApplicationsUsingServlets;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Question09 {

    // You created two filters for your web application by using the @WebFilter annotation, one for authorization
    // and the other for narrowing results by the provided search criteria.
    //
    // The authorization filter must be invoked first.
    // How can you specify this?
    //
    // Choice A
    // setting the priority attribute of the @WebFilter annotation for each of the filters
    //
    // Choice B
    // placing the filter mapping elements in the required order in the web.xml deployment descriptor
    //
    // Choice C
    // placing @WebFilterMapping annotations in the required order
    //
    // Choice D
    // specifying the filter precedence order by using the @Priority annotation
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
    // Choice B is correct.
    //
    // You can indeed not define the filter execution order using @WebFilter annotation.
    // However we can achieve by web.xml filter mappings.
    // If using annotations (@WebFilter) the order seems to be undefined - you still have to declare the <filter-mapping> entries in web.xml.
    
    @WebFilter(urlPatterns = "/*")
    public static class AuthorizationFilter implements javax.servlet.Filter {

	public void doFilter(HttpServletRequest request, HttpServletResponse response) {
	    System.out.println("I'm here! {LoggingFilter.doFilter 1} ");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	    System.out.println("I'm here! {LoggingFilter.init} ");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	    System.out.println("I'm here! {LoggingFilter.doFilter 2} ");

	    long start = System.currentTimeMillis();
	    System.out.println("Milliseconds in: " + start);

	    chain.doFilter(request, response); // next filter mapped on same url!

	    long end = System.currentTimeMillis();
	    System.out.println("Milliseconds out: " + end);
	}

	@Override
	public void destroy() {
	    System.out.println("I'm here! {LoggingFilter.destroy} ");
	}
    }    

    @WebFilter(urlPatterns = "/*")
    public static class NarrowingFilter implements javax.servlet.Filter {

	public void doFilter(HttpServletRequest request, HttpServletResponse response) {
	    System.out.println("I'm here! {LoggingFilter.doFilter 1} ");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	    System.out.println("I'm here! {LoggingFilter.init} ");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	    System.out.println("I'm here! {LoggingFilter.doFilter 2} ");

	    long start = System.currentTimeMillis();
	    System.out.println("Milliseconds in: " + start);

	    chain.doFilter(request, response); // next filter mapped on same url!

	    long end = System.currentTimeMillis();
	    System.out.println("Milliseconds out: " + end);
	}

	@Override
	public void destroy() {
	    System.out.println("I'm here! {LoggingFilter.destroy} ");
	}
    }

}
