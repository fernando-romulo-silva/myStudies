package br.com.fernando.client;

import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import br.com.fernando.core.Autor;

public class Cliente {

    public static void main(final String[] args) throws Exception {

        final AutoresService service = new AutoresServiceService().getAutoresServicePort();

        final BindingProvider bindingProvider = (BindingProvider) service;

        @SuppressWarnings("rawtypes")
        final List<Handler> handlerChain = bindingProvider.getBinding().getHandlerChain();

        handlerChain.add(new WSSecurityHandler("alexandre", "alexandre"));

        bindingProvider.getBinding().setHandlerChain(handlerChain);
        final List<Autor> autores = service.listarAutores();

        for (final Autor autor : autores) {
            System.out.println(autor.getNome());
        }
    }
}
