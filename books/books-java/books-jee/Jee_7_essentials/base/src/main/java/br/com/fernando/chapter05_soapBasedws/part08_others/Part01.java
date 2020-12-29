package br.com.fernando.chapter05_soapBasedws.part08_others;

import java.util.Map;

import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;

public class Part01 {
    
  // <service-ref>
  //   <service-ref-name>services/MyService</service-ref-name>
  //   <service-interface>br.com.fernando.chapter05_soapBasedws.part08_others.Part01#MyService</service-interface>
  //   <wsdl-file>http://example.com/MyWsdl</wsdl-file>
  // </service-ref>

    @Stateless
    public class EbjService {

	@WebServiceRef(wsdlLocation = "META-INF/wsdl/AnyService/Any.wsdl")
	private MyService myService;
	
	public void method01() {
	    MySEI proxy = myService.getMyEndpointPort();
	    BindingProvider bp = (BindingProvider)proxy;
	    Map<String, Object> rc = bp.getRequestContext();
	    rc.put(BindingProvider.USERNAME_PROPERTY, "myuser");
	    rc.put(BindingProvider.PASSWORD_PROPERTY, "mypass");
	}
	

    }

    @WebService
    @HandlerChain(file = "handler-chain.xml")
    public interface MyService {

	MySEI getMyEndpointPort();

    }
    
}
