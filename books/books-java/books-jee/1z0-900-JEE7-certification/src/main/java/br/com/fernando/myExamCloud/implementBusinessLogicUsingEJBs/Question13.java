package br.com.fernando.myExamCloud.implementBusinessLogicUsingEJBs;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;

public class Question13 {

    // OrderBean and LicenseProcessBean are both EJB 3.x stateless session beans with container-managed transaction demarcation.
    // All business methods in the two beans have transaction attribute REQUIRED.
    // The business method processOrder in OrderBean invokes the business method processLicense in LicenseProcessBean.

    @Stateless
    public class OrderBean {

	@EJB
	private LicenseProcessBean licenseProcessBean;

	public void processOrder() {

	    licenseProcessBean.processLicense(); // ???
	}

    }

    @Stateless
    public class LicenseProcessBean {

	public void processLicense() {
	    throw new EJBException("unexpected error..."); // 12
	}
    }
    // Which statement is true about the result of this method invocation assuming execution reaches Line 12?
    //
    //
    //
    //
    //
    //
    //
    // Choice C is correct.
    //
    // The container catch EJBException and throws EJBTransactionRolledbackException to mark the transaction associated
    // with processing of the request has been rolled back, or marked to roll back.
    //
    // Thus the requested operation either could not be performed or was not performed because further computation
    // on behalf of the transaction would be fruitless.
}