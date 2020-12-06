package br.com.fernando.myExamCloud.implementBusinessLogicUsingEJBs;

import javax.ejb.EJB;
import javax.ejb.Stateless;

public class Question07 {

    class Product {

    }

    // Give the code fragment:
    @Stateless
    // 2:
    public class ProductWarehouse {
	public void sendConfirmation(String msg) {

	}

	public String reserve(Product pro) {
	    return "OK";
	}
    }

    @Stateless
    public class Shop {
	@EJB
	private ProductWarehouse warehouse;

	// 12:
	public void purchase(Product pro) {
	    String confirmationCode = warehouse.reserve(pro);
	    warehouse.sendConfirmation(confirmationCode);
	}
    }

    // The sendConfirmation() and reserve() methods should be executed in the same transactional context
    // Which transaction attributes do you ensure this?
    //
    // Choice A
    // Add annotations:@TransactionAttribute (TransactionAttributeType.REQUIRES_NEW) at line2,
    // @Transactional at line 9 and@TransactionAttribute ((TransactionAttributeType.MANDATORY) at line 12
    //
    // Choice B
    // No additional annotations are required.
    //
    // Choice C
    // Add annotations:@TransactionAttribute (TransactionAttributeType.REQUIRED) at line 2,
    // @Transactional at line 9 and@TransactionAttribute ((TransactionAttributeType.REQUIRES_NEW) at line 12
    //
    // Choice D
    // Add annotations:@TransactionAttribute (TransactionAttributeType.MANDATORY) at line 2,
    // @Transactional at line 9 and@TransactionAttribute ((TransactionAttributeType.REQUIRES_NEW) at line 12
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
    // The TransactionAttribute annotation specifies whether the container is to invoke a business method within a transaction context.
    // The TransactionAttribute annotation can be used for session beans and message driven beans.
    // It can only be specified if container managed transaction demarcation is used.
    //
    // The values of the TransactionAttribute annotation are defined by the enum TransactionAttributeType. If the TransactionAttribute annotation is not specified,
    // and the bean uses container managed transaction demarcation, the semantics of the REQUIRED transaction attribute are assumed.
    //
    // Here, the default transaction attribute REQUIRED applies to all methods.
    // The purchase method will runs within a transaction if already started or create new transaction if none exists.
    // Both sendConfirmation() and reserve() will run within the same transaction context since it uses REQUIRED default transaction attribute.
}
