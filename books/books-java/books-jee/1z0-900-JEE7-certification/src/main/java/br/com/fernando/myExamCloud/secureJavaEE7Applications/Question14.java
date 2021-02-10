package br.com.fernando.myExamCloud.secureJavaEE7Applications;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;

public class Question14 {

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
    // By default, the identity of the caller of the intermediate component is propagated to the target enterprise bean.
    //
    // The @RunAs annotation defines the role of the application during execution in a Java EE container. 
    // It can be specified on a class, allowing developers to execute an application under a particular role. 
    // The role must map to the user/group information in the container’s security realm. 
    // The value element in the annotation is the name of a security role of the application during execution in a Java EE container.
    //
    // Defines the identity of the application during execution in a J2EE container.
    // This allows developers to execute under a particular role.
    // The role must map to the user / group information in the containers security realm.
    // It's value is the name of a security role.

    @Stateless
    @RunAs("god") // when it execute, it will be execute with "god" role
    public class StudentService {

	@Resource
	private EJBContext context;

	@RolesAllowed({ "admin", "guest" }) // two roles are permited
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
