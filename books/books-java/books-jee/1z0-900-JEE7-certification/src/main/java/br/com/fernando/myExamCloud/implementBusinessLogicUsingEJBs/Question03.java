package br.com.fernando.myExamCloud.implementBusinessLogicUsingEJBs;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

public class Question03 {

    public class CreditValidationException extends Exception {
	private static final long serialVersionUID = 1L;
    }

    public class DatabaseException extends SQLException {
	private static final long serialVersionUID = 1L;
    }

    public class LicenseInfo {
    }

    public class OrderInfo {
    }

    @Stateless
    // Uses CMT
    @TransactionManagement(TransactionManagementType.CONTAINER)
    public class LicenseManagerBean {
	// Injects EJB context

	@Resource
	private SessionContext context;

	public void placeLicenseRequest(LicenseInfo licenseInfo, OrderInfo orderInfo) {
	    try {

		if (licenseInfo == null) {
		    throw new DatabaseException();
		}

		if (orderInfo == null) {
		    throw new CreditValidationException();
		}

	    } catch (CreditValidationException cve) {
		context.setRollbackOnly();
	    } catch (DatabaseException de) {
		context.setRollbackOnly();
	    }
	}
    }

    // Select the correct annotation to run placeLicenseRequest in separate transaction context.
    //
    // Choice A
    // @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW);
    //
    // Choice B
    // @TransactionAttribute(TransactionAttributeType.REQUIRED);
    //
    // Choice C
    // @TransactionAttribute(TransactionAttributeType.SUPPORTS);
    //
    // Choice D
    // @TransactionAttribute(TransactionAttributeType.MANDATORY);
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
    // Choice A is correct.
    //
    // The method requires new transaction context, so we should use REQUIRES_NEW transaction attribute
    // to ensure that the method always runs within a new transaction.
}
