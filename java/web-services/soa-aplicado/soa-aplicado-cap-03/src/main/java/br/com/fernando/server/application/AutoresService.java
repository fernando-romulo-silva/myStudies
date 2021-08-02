package br.com.fernando.server.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import br.com.fernando.core.Autor;

@WebService(serviceName = "autores")
public class AutoresService {

    @WebResult(name = "autor")
    @WebMethod(operationName = "listarAutores")
    public List<Autor> listarAutores() {
        final Autor adrianoAlmeida = new Autor("Adriano Almeida", new Date());
        final Autor pauloSilveira = new Autor("Paulo Silveira", new Date());
        final Autor viniciusBaggio = new Autor("Vinicius Baggio Fuentes", new Date());
        return new ArrayList<>(Arrays.asList(adrianoAlmeida, pauloSilveira, viniciusBaggio));
    }

    public static void main(final String[] args) {
        Endpoint.publish("http://localhost:8080/soa-aplicado/autores", new AutoresService());
    }
}
