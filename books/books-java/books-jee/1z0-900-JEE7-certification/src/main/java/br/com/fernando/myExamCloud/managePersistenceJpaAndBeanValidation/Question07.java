package br.com.fernando.myExamCloud.managePersistenceJpaAndBeanValidation;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Question07 {

    public class Customer {

	public void setName(String name) {
	    // TODO Auto-generated method stub
	}
    }

    @Stateless
    public class CustomerService {

	@PersistenceContext(unitName = "Cust-PU")
	EntityManager entityManager;

	private Customer findCustomer(String customerId) {
	    return entityManager.find(Customer.class, customerId);
	}

	public void updateCustomer(Customer cust) {
	    // add code here
	}
    }

    @WebServlet(name = "Employee", urlPatterns = { "/customer" })
    public class CustomerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private CustomerService customerService;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    String customerId = req.getParameter("customerId");
	    String name = req.getParameter("name");
	    Customer customer = customerService.findCustomer(customerId);
	    customer.setName(name);

	    customerService.updateCustomer(customer);
	}
    }
    
    // What code should you add to the body of the updateCustomer method in order to save pending changes to the database?
    //
    // Choice A 	
    // entityManager.merge(customer);
    //
    // Choice B 	
    // entityManager.getTransaction().begin();
    // entityManager.merge(emp);
    // entityManager.getTransaction().commit();
    //
    // Choice C 	
    // Context ctx = new InitialContext();
    // UserTransaction utx (UserTransaction)ctx.lookup("java:comp/UserTransaction");
    // utx.begin();
    // entityManager.merge(customer);
    // utx.commit();
    // entityManager.lock(emp);EntityManager.merge(emp);
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
    // Choice C is correct.

}
