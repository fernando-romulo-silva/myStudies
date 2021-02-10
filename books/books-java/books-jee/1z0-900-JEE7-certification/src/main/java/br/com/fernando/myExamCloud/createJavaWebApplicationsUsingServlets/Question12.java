package br.com.fernando.myExamCloud.createJavaWebApplicationsUsingServlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

public class Question12 {

    // Which of the following statements are true about javax.servlet.ServletContextListener
    // implemented in Java EE 7 Servlet container? [ Choose two ]
    //
    // Choice A
    // Implementations of javax.servlet.ServletContextListener are invoked at their contextInitialized method in the order in which they have been declared
    //
    // Choice B
    // Implementations of javax.servlet.ServletContextListener are invoked at their contextDestroyed method in reverse order.
    //
    // Choice C
    // Implementations of javax.servlet.ServletContextListener are invoked at their contextDestroyed method in the order in which they have been declared
    //
    // Choice D
    // Implementations of javax.servlet.ServletContextListener are invoked at their contextInitialized method in reverse order.
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
    // Choice A and B are correct answers.
    //
    // As of Servlet 3.0, the listeners are invoked in the order in which they are declared in the web.xml
    //
    /**
     * <pre>
     *   <servlet>
     *       <servlet-name>ProcessReg</servlet-name>
     *       <servlet-class>ProcessReg</servlet-class>
     *       <init-param>
     *           <param-name>pract123</param-name>
     *           <param-value>jdbc:odbc:practODBC</param-value>
     *       </init-param>       
     *   </servlet>
     * 
     * 
     *   <listener> 
     *       <listener-class>com.fernando.FirstListener</listener-class>
     *   </listener>
     *   
     *   <listener>
     *       <listener-class>com.fernando.SecondListener</listener-class>
     *   </listener>
     *
     * </pre>
     */

    // Implementations of javax.servlet.ServletContextListener are invoked at their contextInitialized method in the order in which they have been declared,
    // and at their contextDestroyed method in reverse order.
    //

    @WebListener
    public static class AppContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(final ServletContextEvent servletContextEvent) {

	    ServletContext ctx = servletContextEvent.getServletContext();

	    System.out.println("Inicialized");
	}

	@Override
	public void contextDestroyed(final ServletContextEvent servletContextEvent) {
	    ServletContext ctx = servletContextEvent.getServletContext();

	    System.out.println("Destroyed");
	}
    }

    @WebListener
    public class MeuServletContextAttributeListener implements ServletContextAttributeListener {
	@Override
	public void attributeAdded(ServletContextAttributeEvent event) {
	    // . . . event.getName();
	    // . . . event.getValue();
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent event) {
	    // . . .
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent event) {
	    // . . .
	}
    }

}
