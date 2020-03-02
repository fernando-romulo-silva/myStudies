package br.com.fernando.server.application.autores.v1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.soap.Addressing;

import br.com.fernando.core.estoque.v1.Autor;

//@formatter:off
@WebService(endpointInterface = "br.com.fernando.server.application.autores.v1.Autores",  // deve conter o nome completamente qualificado da interface que o serviço implementa 
                     portName = "autoresSOAP", // corresponde ao valor do atributo name presente na tag port (que é filha de service ). No nosso caso, é AutoresSOAP.
                  serviceName = "autores", // corresponde ao valor do atributo name presente na tag service , no WSDL. No nosso caso, é 'autores'
              targetNamespace = "http://fernando.com.br/server/application/autores/v1", // corresponde ao namespace do WSDL
                 wsdlLocation = "WEB-INF/wsdl/autores.wsdl") // corresponde ao caminho do WSDL, dentro do projeto. Movendo a pasta contracts e schemas para dentro da pasta WEB-INF
//@formatter:on
public class AutoresServiceImpl implements Autores {

    @Override
    @WebMethod(action = "autores/ListarAutores")
    @WebResult(name = "autor", targetNamespace = "")
    @RequestWrapper(localName = "listarAutores", targetNamespace = "http://fernando.com.br/server/application/autores/v1", className = "br.com.fernando.server.application.autores.v1.ListarAutores")
    @ResponseWrapper(localName = "listarAutoresResponse", targetNamespace = "http://fernando.com.br/server/application/autores/v1", className = "br.com.fernando.server.application.autores.v1.ListarAutoresResponse")
    public List<Autor> listarAutores() {
        final Autor alexandre = new Autor();
        alexandre.setNome("Alexandre");
        return new ArrayList<>(Arrays.asList(alexandre));
    }

    @Override
    @WebMethod(action = "autores/SolicitarRelacaoDeAutores")
    @WebResult(name = "autor", targetNamespace = "")
    @RequestWrapper(localName = "solicitarRelacaoDeAutores", targetNamespace = "http://fernando.com.br/server/application/autores/v1", className = "br.com.fernando.server.application.autores.v1.SolicitarRelacaoDeAutores")
    @ResponseWrapper(localName = "solicitarRelacaoDeAutoresResponse", targetNamespace = "http://fernando.com.br/server/application/autores/v1", className = "br.com.fernando.server.application.autores.v1.SolicitarRelacaoDeAutoresResponse")
    @Action(input = " ", output = "AutoresService/solicitarRelacaoDeAutoresResponse")
    @Addressing(enabled = true, required = true)
    public List<Autor> solicitarRelacaoDeAutores(@WebParam(name = "desde", targetNamespace = "") final XMLGregorianCalendar desde) {
        final Autor alexandre = new Autor();
        alexandre.setNome("Alexandre Saudate");

        final Autor adriano = new Autor();
        adriano.setNome("Adriano Almeida");

        final Autor paulo = new Autor();
        paulo.setNome("Paulo Silveira");

        return new ArrayList<>(Arrays.asList(alexandre, adriano, paulo));
    }
}
