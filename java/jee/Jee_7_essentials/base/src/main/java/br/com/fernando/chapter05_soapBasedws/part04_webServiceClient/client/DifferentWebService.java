
package br.com.fernando.chapter05_soapBasedws.part04_webServiceClient.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.2.9-b130926.1035 Generated source version: 2.2
 */
@WebService(name = "DifferentWebService", targetNamespace = "http://differentwebservice.com.br")
@XmlSeeAlso({ ObjectFactory.class })
public interface DifferentWebService {

    /**
     * @param arg0
     * @return returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "sayHello", targetNamespace = "http://differentwebservice.com.br", className = "br.com.fernando.chapter05_soapBasedws.part04_webServiceClient.client.SayHello")
    @ResponseWrapper(localName = "sayHelloResponse", targetNamespace = "http://differentwebservice.com.br", className = "br.com.fernando.chapter05_soapBasedws.part04_webServiceClient.client.SayHelloResponse")
    @Action(input = "http://differentwebservice.com.br/DifferentWebService/sayHelloRequest", output = "http://differentwebservice.com.br/DifferentWebService/sayHelloResponse")
    public String sayHello(@WebParam(name = "arg0", targetNamespace = "") String arg0);

}