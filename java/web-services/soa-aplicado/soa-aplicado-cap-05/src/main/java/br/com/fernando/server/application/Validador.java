package br.com.fernando.server.application;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.fernando.core.AlgoritmoValidacaoCPF;

@Path("/validador")
public class Validador {

    @GET
    @Path("/cpf/{valor : [0-9]{11}}")
    public Response validaCPF(@PathParam("valor") final String valor, @QueryParam("algoritmo") @DefaultValue("TODOS") final AlgoritmoValidacaoCPF algoritmo) {
        // Não precisamos testar nenhum dos valores:
        // A String "valor" passa por checagem via regex
        // e o algoritmo é preenchido pelo valor padrão

        if (algoritmo.eValido(valor)) {
            return Response.ok().build();
        }
        return Response.status(Status.BAD_REQUEST).build();

    }
}
