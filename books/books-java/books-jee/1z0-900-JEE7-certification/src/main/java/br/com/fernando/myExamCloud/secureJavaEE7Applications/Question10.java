package br.com.fernando.myExamCloud.secureJavaEE7Applications;

import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;

public class Question10 {

    class TestVO {

    }

    interface QuestionManager {
	void associateTest(TestVO testVO);
    }

    interface TestManager {
	void associateQuestion(TestVO testVO);
    }

    // Given:

    @Stateless // 10
    @RunAs("ADMIN") // 11
    public class TestManagerBean implements TestManager { // 12
	// 13
	@EJB
	QuestionManager qm; // 14

	@RolesAllowed("ROOTADMIN") // 22
	public void associateQuestion(TestVO testVO) { // 24
	    qm.associateTest(testVO); // 25
	} // 26
    }

    @Stateless
    public class QuestionManagerBean implements QuestionManager {

	@RolesAllowed("ROOTADMIN") // 23
	public void associateTest(TestVO testVO) { // 24

	}
    }

    // A user who is only in role ROOTADMIN invokes associateQuestion method
    // Assuming NO other security-related metadata, what is the expected result?
    //
    // Choice A
    // An exception is thrown at Line 25.
    //
    // Choice B
    // An exception is thrown at Line 14.
    //
    // Choice C
    // method A cannot be invoked by this user.
    //
    // Choice D
    // The code executes without raising an exception.
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
    // Choice A is correct.
    //
    // The @RunAs annotation defines the role of the application during execution in a Java EE container. 
    // It can be specified on a class, allowing developers to execute an application under a particular role. 
    // The role must map to the user/group information in the container’s security realm. 
    // The value element in the annotation is the name of a security role of the application during execution in a Java EE container.
    // Hence the caller is not part of ADMIN role an exception is thrown at Line 25.
}
