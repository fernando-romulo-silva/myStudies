package br.com.fernando.myExamCloud.createJavaWebApplicationsJSPs;

public class Question07 {

    // You need to use com.epractizelabs.cart.OrderBean in your JSP.
    // Which of the following code snippets can define com.epractizelabs.cart.OrderBean in your JSP?
    // [ Choose two ]
    //
    // Choice A
    // <jsp:useBean id = "com.epractizelabs.cart.OrderBean" scope="page" />
    //
    // Choice B
    // <jsp:useBean id = "orderBean" class="com.epractizelabs.cart.OrderBean" />
    //
    // Choice C
    // <jsp:useBean id = "orderBean" type = "java.lang.OrderBean" scope="page" />
    //
    // Choice D
    // <jsp:useBean id = "orderBean" beanName="com.epractizelabs.cart.OrderBean" scope="page" />
    //
    // Choice E
    // <jsp:useBean id = "orderBean" beanName="com.epractizelabs.cart.OrderBean" className="OrderBean" type = "OrderBean" scope="page" />
    
    public static class OrderBean {
	
    }
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
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // Choice B and C are correct answers.
    //
    // A jsp:useBean action associates an instance of a Java programming language object defined within a given scope and available with a given
    // id with a newly declared scripting variable of the same id. Following is the syntax of the jsp:useBean action:
    /**
     * <pre>
     * 
     *   <jsp:useBean
     *     id="beanInstanceName"
     *     scope="page | request | session | application"
     *     {
     *       class="package.class" |
     *       type="package.class" |
     *       class="package.class" type="package.class" |
     *       beanName="{package.class|<%= expression %>}" type="package.class"
     *     } 
     *     {
     *       /> |
     *       > other elements </jsp:useBean>
     *     }
     * 
     * </pre>
     */
    //
    // Following is the description of various attributes:
    // * id - The case sensitive name used to identify the object instance.
    //
    // * scope - The scope within which the reference is available.
    // The default value is page.
    //
    // * class - The fully qualified (including package name) class name.
    //
    // * beanName - The name of a Bean, as you would supply to instantiate() method in the java.beans.Beans class.
    // This attribute can also be a request time expression.
    //
    // * type - This optional attribute specifies the type of the class, and follows standard Java casting rules.
    // The type must be a superclass, an interface, or the class itself.
    // The default value is the same as the value of the class attribute.
    
    // <jsp:useBean id="message" class="com.java.Message" scope="request"/>
    //
    // <jsp:useBean id="message" type="com.java.Message" scope="request"/>
}
