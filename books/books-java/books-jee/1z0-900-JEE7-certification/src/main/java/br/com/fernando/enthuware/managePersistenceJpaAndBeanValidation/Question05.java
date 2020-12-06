package br.com.fernando.enthuware.managePersistenceJpaAndBeanValidation;

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

public class Question05 {

    public class Employee {

	public void setName(String name) {
	    // TODO Auto-generated method stub
	}
    }

    @Stateless
    public class HRService {

	@PersistenceContext(unitName = "HRApp-PU")
	EntityManager entityManager;

	private Employee findEmployee(String customerId) {
	    return entityManager.find(Employee.class, customerId);
	}

	public void updateEmployee(Employee emp) {
	    // add code here
	}
    }

    @WebServlet(name = "HR", urlPatterns = { "/hr" })
    public class HRServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private HRService hrService;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	    String badgeld = req.getParameter("badge");
	    String name = req.getParameter("name");
	    
	    Employee emp = hrService.findEmployee(badgeld);
	    emp.setName(name);
	    
	    hrService.updateEmployee(emp);
	}
    }

    // What code should you add to the body of the updateEmployee method in order to save pending changes to the database?
    //
    // Choice A
    // entityManager.merge(emp);
    //
    // Choice B
    // Context ctx = new InitialContextQ);
    // UserTransaction utx = (UserTransaction)ctx.lookup("java:comp/UserTransaction'');
    // utx.begin();
    // entityManager.merge(emp);
    // utx.entityManager.lock(emp);
    // entityManager.merge(emp);    
    //
    // Choice C
    // entityManager.getTransaction().begin();
    // entityManager.merge(emp);
    // entityManager.getTransaction().commit();
    //
    // Choice D
    // entityManager.lock(emp);
    // entityManager.merge(emp);
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
    // The state of persistent entities is synchronized to the database at transaction commit.
    // This synchronization involves writing to the database any updates to persistent entities and their relationships.
    // An update to the state of an entity includes both the assignment of a new value to a persistent property or field of the entity 
    // as well as the modification of a mutable value of a persistent property or field.
    // Therefore, for a managed entity, no code is necessary to save changes to the database. 
    // However, if you want to save a detached entity, then you need to call merge.
    // Further, if you want to persist the changes immediately, then you should either call flush() on the entityManager or commit the transaction.
}
