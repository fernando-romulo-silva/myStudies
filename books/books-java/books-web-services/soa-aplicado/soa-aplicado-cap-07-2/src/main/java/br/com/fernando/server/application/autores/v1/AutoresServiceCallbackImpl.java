package br.com.fernando.server.application.autores.v1;

import java.util.List;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import br.com.fernando.core.estoque.v1.Autor;

//@formatter:off
@WebService(endpointInterface = "br.com.fernando.server.application.autores.v1.AutoresCallback", 
                     portName = "autoresCallbackSOAP", 
                  serviceName = "autoresCallback", 
              targetNamespace = "http://fernando.com.br/server/application/autores/v1",
                 wsdlLocation = "WEB-INF/wsdl/autoresCallback.wsdl")
//@formatter:on
public class AutoresServiceCallbackImpl implements AutoresCallback {

    @Override
    @WebMethod
    @Oneway
    public void solicitarRelacaoDeAutoresCallback(
            @WebParam(name = "solicitarRelacaoDeAutoresResponse", targetNamespace = "http://fernando.com.br/server/application/autores/v1", partName = "parameters") final SolicitarRelacaoDeAutoresResponse parameters) {
        final List<Autor> autores = parameters.getAutor();

        System.out.println("Callback imprimindo:");
        for (final Autor autor : autores) {
            System.out.println("\t" + autor.getNome());
        }
    }
}
