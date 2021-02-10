package br.com.fernando.myExamCloud.createJavaWebApplicationsUsingServlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Question13 {

    // Which type allows you to share servlet attributes across your entire web application?
    //
    // You had to select 1 option.
    //
    // A
    // ServletContext
    //
    // B
    // HttpSession
    //
    // C
    // ServletRequest
    //
    // D
    // ServletConfig
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
    // The correct answer is A
    //
    // B is wrong because Attributes stored in the session are shared only while the session is alive and only with the resources that are participating in the session.
    //
    // C is wrong because ServletRequest to provide client request information to a servlet.
    //
    // D is wrong because Parameters in ServletConfig are also shared accross the entire web application, however, these are pamaters
    // (specified in web.xml) and not attributes. You can't change them.

    /**
     * <pre>
     *       <web-app> 
     *         
     *           <servlet> 
     *               <servlet-name>recruiter</servlet-name> 
     *               <servlet-class>Recruiter</servlet-class> 
     *               <init-param> 
     *                 <param-name>Email</param-name> 
     *                 <param-value>forRecruiter@xyz.com</param-value> 
     *               </init-param> 
     *           </servlet> 
     *             
     *           <servlet-mapping> 
     *               <servlet-name>recruiter</servlet-name> 
     *               <url-pattern>/servlet1</url-pattern> 
     *           </servlet-mapping> 
     *             
     *           <servlet> 
     *               <servlet-name>applicant</servlet-name> 
     *               <servlet-class>Applicant</servlet-class> 
     *               <init-param> 
     *                 <param-name>Email</param-name> 
     *                 <param-value>forApplicant@xyz.com</param-value> 
     *               </init-param> 
     *           </servlet> 
     *             
     *           <servlet-mapping> 
     *               <servlet-name>applicant</servlet-name> 
     *               <url-pattern>/servlet2</url-pattern> 
     *           </servlet-mapping> 
     *             
     *           <context-param> 
     *               <param-name>Website-name</param-name> 
     *               <param-value>NewWebsite.tg</param-value> 
     *           </context-param> 
     *          
     *        </web-app>
     * </pre>
     */

    public class Recruiter extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    final ServletConfig servletConfig = getServletConfig();
	    final ServletContext servletContext = getServletContext();

	    String email = servletConfig.getInitParameter("Email");
	    String website = servletContext.getInitParameter("Website-name");
	    PrintWriter out = response.getWriter();
	    out.println("<center><h1>" + website + "</h1></center><br><p>Contact us:" + email);
	}
    }

    public class Applicant extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    final ServletConfig servletConfig = getServletConfig();
	    final ServletContext servletContext = getServletContext();

	    String email = servletConfig.getInitParameter("Email");
	    String website = servletContext.getInitParameter("Website-name");
	    PrintWriter out = response.getWriter();
	    out.println("<center><h1>" + website + "</h1></center><br><p>Contact us:" + email);
	}
    }

}
