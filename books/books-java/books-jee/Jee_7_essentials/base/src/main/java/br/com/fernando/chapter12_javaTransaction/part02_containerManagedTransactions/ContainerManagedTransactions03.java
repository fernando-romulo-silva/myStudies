package br.com.fernando.chapter12_javaTransaction.part02_containerManagedTransactions;

import static br.com.fernando.Util.sourceToXMLString;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

public class ContainerManagedTransactions03 {

    // The Check before commit Issue
    //
    // The check before commit issue or as widely known, the session bean's instance variables synchronization handling,
    // is an issue raised due to the uncontrollable transaction commit.
    //
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // App 01
    //
    @Stateful // Just note that the interface SessionSynchronization can be used with Stateful Beans and not any other bean (JEE7)
    @LocalBean
    @TransactionManagement(TransactionManagementType.CONTAINER)
    public static class SessionSynchronizationExample implements SessionSynchronization {

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void doAction() { // transaction commit after this method
	    insertNewElement();
	    updateAnotherElement();
	    logAction();
	    // sendInsertUpdateNotification(); // wrong way, you don't know if it'll be commit
	}

	// ---------------------------------------------------------------------------------
	@Override
	public void afterBegin() throws EJBException, RemoteException {
	}

	@Override
	public void beforeCompletion() throws EJBException, RemoteException {
	}

	@Override
	public void afterCompletion(boolean committed) throws EJBException, RemoteException {
	    // Such issue can be solved using the SessionSynchronization interface.
	    //
	    // The interface produces 3 methods that can be used to handle operations needed to be invoked just after the transaction starts, just before the transaction ends,
	    // and finally after the transaction does commit the changes to the database.
	    //
	    // Here we can use the last method to send the notification to the web service to check the newly added element, and as this will be after the transaction commit the data,
	    // the new element will be visible to the web service method
	    //
	    if (committed) { // okay, now you know :)
		try {
		    sendInsertUpdateNotification();
		} catch (final MalformedURLException ex) {
		    throw new EJBException(ex);
		}
	    }
	}

	// ---------------------------------------------------------------------------------
	// Now suppose that the method sendInsertUpdateNotification will call a web service with the following code:
	private void sendInsertUpdateNotification() throws MalformedURLException {
	    // Call the server
	    final Service service = Service.create( //
		    new URL("http://localhost:8080/example/NewSimpleWebService?wsdl"), //
		    new QName("http://newdifferentwebservice.com.br", "NewDifferentWebService") //
	    );

	    final String request = "<ns1:handleInsertUpdate xmlns:ns1=\"http://newdifferentwebservice.com.br\"></ns1:handleInsertUpdate>";

	    final Dispatch<Source> dispatcher = service.createDispatch(new QName("http://newdifferentwebservice.com.br", "NewDifferentPort"), Source.class, Service.Mode.PAYLOAD);
	    final Source result01 = dispatcher.invoke(new StreamSource(new StringReader(request)));

	    System.out.println("Received xml response: " + sourceToXMLString(result01));
	}

	private void logAction() {
	    // Some code here
	}

	private void updateAnotherElement() {
	    // Some code here
	}

	private void insertNewElement() {
	    // Some code here
	}
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // APP 02
    //
    // Now when the method sendInsertUpdateNotification gets invoked, the web service method handleInsertUpdate will be invoked asynchronously.
    // The web service method will first try to get the newly added element from the database and then validate on that element data.
    @WebService
    public static interface SimpleWebServiceInterface {

	public String handleInsertUpdate();
    }

    @WebService( //
	    name = "DifferentWebService", //
	    portName = "DifferentPort", //
	    serviceName = "DifferentWebService", //
	    targetNamespace = "http://differentwebservice.com.br")
    public static class SimpleWebService implements SimpleWebServiceInterface {

	@EJB
	private ElementService elementService;

	@Override
	public String handleInsertUpdate() {
	    Element e = elementService.getNewElement();
	    elementService.validateElementData(e);

	    return "Done";
	}
    }

    public static class Element {

    }

    @Stateless
    public static class ElementService {

	public Element getNewElement() {
	    // TODO Auto-generated method stub
	    return null;
	}

	public void validateElementData(Element e) {
	    // TODO Auto-generated method stub

	}
    }

    // The following scenario once executed, will produce what can be called a check before commit issue.
    // The web service method is not running in the same transaction scope as the SessionSynchronizationExample.doAction method.
    // So all changes that take place inside the transaction, won’t be visible to the web service method until the transaction does commit.
    //
    // As the commit only takes place just before the doAction method finishes execution, the method handleInsertUpdate will be called
    // before the doAction transaction does commit the changes and will fail to retrieve the newly added element by the doAction method.
}
