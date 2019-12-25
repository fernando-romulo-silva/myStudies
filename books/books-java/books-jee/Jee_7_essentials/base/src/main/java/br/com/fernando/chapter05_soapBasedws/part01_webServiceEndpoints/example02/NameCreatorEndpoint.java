package br.com.fernando.chapter05_soapBasedws.part01_webServiceEndpoints.example02;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
// By default, the generated WSDL uses the document/literal style of binding.
// You can change this by specifying the @SOAPBinding annotation on the class:
@SOAPBinding(style = Style.RPC)
public interface NameCreatorEndpoint {

    String createName();

    // In my understanding, you will not be able to process a plain List via JAXB, as JAXB has no idea how to transform that into XML.
    // Instead, you will need to define a JAXB type which holds a List<String>. (Don't work with ArrayList too)
    NamesListElement createNames();
}
