package br.com.fernando.myExamCloud.understandJavaEEArchitecture;

import java.util.HashMap;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

public class Question01 {

    // Code:
    @Stateless
    @Local(CartSession.class)
    public class PersistentCartSession implements CartSession {

	@Override
	public HashMap<String, String> getCartItems(int userId) {
	    HashMap<String, String> cartItems = new HashMap<>();
	    // logic to get cartItems
	    return cartItems;
	}
    }

    @Stateless
    @Local(CartSession.class)
    public class TransientCartSession implements CartSession {
	// code here
	@Override
	public HashMap<String, String> getCartItems(int userId) {
	    HashMap<String, String> cartItems = new HashMap<>();
	    // logic to get cartItems
	    return cartItems;
	}
    }

    public interface CartSession {
	HashMap<String, String> getCartItems(int userId);
    }

    @WebServlet(name = "CartServlet", urlPatterns = { "/cart" })
    public class CartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// code here
    }

    // Which of the following code snippet inserted at // code here injects TransientCartSession in CartServlet?
    //
    // Choice A
    // @EJB(beanName = "TransientCartSession")
    // CartSession session;
    //
    // Choice B
    // @EJB
    // CartSession session;
    //
    // Choice C
    // @EJB(type = "TransientCartSession")
    // CartSession session;
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // Choice A is correct.
    //
    // The existence of two implementations of the CartSession @Local interface breaks the convention and throws an exception during deployment
    // when we use @EJB annotation without beasnName attribute.
    // Enhancement of the @EJB annotation with the beanName attribute fixes the problem.
    //
    // The value of the beanName attribute is the simple name(getSimpleName) of the desired bean:

    @EJB(beanName = "TransientCartSession")
    CartSession session;

}
