package br.com.fernando.enthuware.secureJavaEE7Applications;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;

public class Question03 {

    // Which annotation allows a request to be executed with another Principal's identity?
    //
    // A
    // Itis not possible to execute a request with another Principal's identity.
    //
    // B
    // @Useldentity
    //
    // C
    // @RolesAllowed
    //
    // D
    // @RunAs
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
    // The correct answer is D
    //
    // Defines the identity of the application during execution in a J2EE container.
    // This allows developers to execute under a particular role.
    // The role must map to the user / group information in the containers security realm.
    // It's value is the name of a security role.

    @Stateless
    public class StudentService {

	@Resource
	private EJBContext context;

	@RolesAllowed({ "admin", "guest" })
	public Student addStudent(final Student student) {
	    System.out.println(context.isCallerInRole("admin"));
	    return student;
	}

	@RolesAllowed({ "admin" })
	public Student getDetails(int id) {
	    Student student = new Student();
	    student.setId(id);
	    student.setName("noname");
	    return student;
	}
    }

    public static class Student {

	public void setId(int id) {
	}

	public void setName(String string) {
	}
    }

}
