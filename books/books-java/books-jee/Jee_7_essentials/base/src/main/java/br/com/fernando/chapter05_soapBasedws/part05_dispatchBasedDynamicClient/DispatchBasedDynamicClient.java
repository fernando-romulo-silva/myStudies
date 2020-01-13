package br.com.fernando.chapter05_soapBasedws.part05_dispatchBasedDynamicClient;

import static br.com.fernando.Util.sourceToXMLString;

import java.io.StringReader;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Response;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;

public class DispatchBasedDynamicClient {

	// https://examples.javacodegeeks.com/enterprise-java/jws/jax-ws-dynamic-proxy-client-example/

	public static void main(String[] args) throws Exception {
		// ----------------------------------------------------------------------------------------
		// Server
		final Endpoint endpoint = Endpoint.publish("http://localhost:8080/example/NewSimpleWebService", new SimpleWebService());

		// A Dispatch-based endpoint provides a dynamic alternative to the generated proxybased client.
		// Instead of just the mapped Java types, the complete protocol message or protocol message payload is prepared by way of XML APIs.

		// -----------------------------------------------------------------------------------------
		// Client
		//
		// We create a Service by specifying the fully qualified QName, a port is created from the service, a Dispatch<Source> is created,
		// and the web service endpoint is invoked.
		final Service service = Service.create( //
				new URL("http://localhost:8080/example/NewSimpleWebService?wsdl"), //
				new QName("http://newdifferentwebservice.com.br", "NewDifferentWebService") //
		);

		final String name = "Joao";
		final String request = "<ns1:sayHello xmlns:ns1=\"http://newdifferentwebservice.com.br\"><arg0>" + name + "</arg0></ns1:sayHello>";

		// The client can be implemented via Dispatch<Source>, Dispatch<SOAPMessage>, Dispatch<DataSource>, or Dispatch<JAXB Object>:
		final Dispatch<Source> dispatcher = service.createDispatch(new QName("http://newdifferentwebservice.com.br", "NewDifferentPort"), Source.class, Service.Mode.PAYLOAD);

		/******************* Username & Password ******************************/
		
		dispatcher.getRequestContext().put(Dispatch.USERNAME_PROPERTY, "wsUser");
		dispatcher.getRequestContext().put(Dispatch.PASSWORD_PROPERTY, "wsPassword");

		/**********************************************************************/

		// The business method invoked on the service endpoint is dispatched based upon the received SOAP message.
		final Source result01 = dispatcher.invoke(new StreamSource(new StringReader(request)));
		System.out.println("Received xml response: " + sourceToXMLString(result01));

		// A new class, MyAsyncHandler, registers a callback class that receives control when the response is received from the endpoint.
		class MyAsyncHandler implements AsyncHandler<Source> {

			@Override
			public void handleResponse(final Response<Source> res) {
				System.out.println("It's done? " + res.isDone());
			}
		}
		// A Dispatch client can also be invoked asynchronously
		Future<?> response = dispatcher.invokeAsync(new StreamSource(new StringReader(request)), new MyAsyncHandler());
		// The response can be used to check if the web service invocation has completed, wait for its completion, or retrieve the result. The
		// handleResponse method of the callback is used to process the response received.
		final Object object = response.get();
		System.out.println("Received xml async response: " + sourceToXMLString((Source) object));

		// A one-way request using a Dispatch-based client may be made:
		dispatcher.invokeOneWay(new StreamSource(new StringReader(request)));

		// ----------------------------------------------------------------------------------------
		// Stop server
		endpoint.stop();
	}
}
