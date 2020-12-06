package br.com.fernando.enthuware.createJavaWebApplicationsUsingServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Question03 {

    // Consider the following code fragments:
    // web.xml:
    /**
     * <pre>
     *       <web-app>
     *          <servlet>
     *                <servlet-name>MyFooServlet</servlet-name>
     *                <servlet-class>com.foo.FooServlet</servlet-class>
     *          </servlet>
     *          <servlet-mapping>
     *                <servlet-name>MyFooServlet</servlet-name>
     *                <url-pattern>/thisFoo</url-pattern>
     *          </servlet-mapping>
     *       </web-app>
     * </pre>
     */
    //
    // Servlet class code:

    @WebServlet(name = "MyFooServlet", urlPatterns = { "/thatFoo" })
    public class FooServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
    }

    // Identify the URL patterns against which this servlet will be mapped.
    //
    // A
    // /thisFoo, /thatFoo, and /MyFooServlet
    //
    // B
    // /thisFoo
    //
    // C
    // /thisFoo and /thatFoo
    //
    // D
    // /thatFoo
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
    // The correct answer is C
  
}
