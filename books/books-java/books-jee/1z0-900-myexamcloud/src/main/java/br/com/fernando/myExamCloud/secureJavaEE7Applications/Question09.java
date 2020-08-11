package br.com.fernando.myExamCloud.secureJavaEE7Applications;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

public class Question09 {

    class TestVO {

    }

    public interface TestManager {

    }

    // An Application Assembler is given the following stateless session bean:

    @Stateless
    public class TestManagerBean implements TestManager {

	@RolesAllowed("MANAGER")
	public void addTest(TestVO testVO) {
	}

	public void updateTest(TestVO testVO) {
	}

	public void deleteTest(int testId) {
	}
    }

    // A deployment descriptor is also supplied, a portion of which reads as follows:
    //
    /**
     * <pre>
     *      <method-permission>
     *          <role-name>EXAMINER</role-name>
     *          <method>
     *              <ejb-name>TestManagerBean</ejb-name>
     *              <method-name>*</method-name>
     *          </method>
     *      </method-permission>
     * </pre>
     */
    //
    // Which statement is true?
    //
    // Choice A
    // A client in any role will be able to access any of the methods.
    //
    // Choice B
    // A client in the role "EXAMINER" will be able to access any of the methods.
    //
    // Choice C
    // A client in the role "MANAGER" will be able to access any of the methods.
    //
    // Choice D
    // A client in the role "EXAMINER" will be able to access updateTest and deleteTest, but not addTest.
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
    // There are three supported styles of method element declarations.
    //
    // The first is used for referring to all the home and component interface methods of the named enterprise bean:
    /**
     * <pre>
     *    <method>
     *        <ejb-name>EJBNAME</ejb-name>
     *        <method-name>*</method-name>
     *    </method>
     * </pre>
     */
    //
    // The second style is used for referring to a specified method of the home or component interface of the named enterprise bean:
    /**
     * <pre>
     *    <method>
     *        <ejb-name>EJBNAME</ejb-name>
     *        <method-name>METHOD</method-name>
     *    </method>
     * </pre>
     */
    //
    // If there are multiple methods with the same overloaded name, this style refers to all of the overloaded methods.
    //
    // The third style is used to refer to a specified method within a set of methods with an overloaded name:
    /**
     * <pre>
     *    <method>
     *        <ejb-name>EJBNAME</ejb-name>
     *        <method-name>METHOD</method-name>
     *        <method-params>
     *            <method-param>PARAMETER_1</method-param>
     *            <!-- ... -->
     *            <method-param>PARAMETER_N</method-param>
     *        </method-params>
     *    </method>
     * </pre>
     */
    // The deployment descriptor uses first style (all methods access).
}
