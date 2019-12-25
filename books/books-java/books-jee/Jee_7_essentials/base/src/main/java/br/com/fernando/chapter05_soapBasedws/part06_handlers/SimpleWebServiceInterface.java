package br.com.fernando.chapter05_soapBasedws.part06_handlers;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface SimpleWebServiceInterface {

    public String sayHello(final String name);
}
