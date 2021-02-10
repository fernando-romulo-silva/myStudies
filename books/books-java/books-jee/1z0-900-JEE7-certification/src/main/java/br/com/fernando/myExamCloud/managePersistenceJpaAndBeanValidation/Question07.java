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
    // Choice B is correct.
    //
    // The state of persistent entities is synchronized to the database at transaction commit.
    // This synchronization involves writing to the database any updates to persistent entities and their relationships.
    // An update to the state of an entity includes both the assignment of a new value to a persistent property or field of the entity
    // as well as the modification of a mutable value of a persistent property or field.
    //
    // Therefore, for a managed entity, no code is necessary to save changes to the database.
    // However, if you want to save a detached entity, then you need to call merge.
    // Further, if you want to persist the changes immediately, then you should either call flush() on the entityManager or commit the transaction.
    //
    // Ideally, no code is required. However, that is not an option.
    // Further, since the object refered to by emp is already a managed entity object, call to merge is definitely not required.
    // At the most, you could call flush if you want the changes to be persisted immediately but that is not given as an option either.
    @Stateless
    public class CustomerServiceResponse {

	@PersistenceContext(unitName = "Cust-PU")
	EntityManager entityManager;

	private Customer findCustomer(String customerId) {
	    return entityManager.find(Customer.class, customerId);
	}

	public void updateCustomer(Customer cust) {
	    entityManager.getTransaction().begin();
	    entityManager.merge(cust);
	    entityManager.getTransaction().commit();
	}
    }

    @WebServlet(name = "Employee", urlPatterns = { "/customer" })
    public class CustomerServletResponse extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private CustomerServiceResponse customerService;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    String customerId = req.getParameter("customerId");
	    String name = req.getParameter("name");
	    Customer customer = customerService.findCustomer(customerId);
	    customer.setName(name);

	    customerService.updateCustomer(customer);
	}
    }
}
