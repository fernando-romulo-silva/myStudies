package br.com.fernando.client;

import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.soap.AddressingFeature;

import br.com.fernando.core.estoque.v1.Autor;
import br.com.fernando.server.application.autores.v1.Autores;

public class Cliente {

    private static boolean ehAssincrono = true;
    private static String enderecoResposta = "http://localhost:8080/soa-aplicado/autoresCallback";

    public static void main(final String[] args) {
        Autores service = null;

        if (ehAssincrono) {
            service = new Autores_Service().getAutoresSOAP(new AddressingFeature());
            @SuppressWarnings("rawtypes")
            final List<Handler> handlerChain = ((BindingProvider) service).getBinding().getHandlerChain();

            handlerChain.add(new AddressingHandler(enderecoResposta));
            ((BindingProvider) service).getBinding().setHandlerChain(handlerChain);

        } else {
            service = new Autores_Service().getAutoresSOAP();
        }

        final List<Autor> autores = service.solicitarRelacaoDeAutores(null);

        for (final Autor autor : autores) {
            System.out.println(autor.getNome());
        }
    }
}
