package br.com.fernando;

import br.com.fernando.view.util.HeaderHandlerResolver;
import br.com.fernando.view.webservices.LivroWebService;
import br.com.fernando.view.webservices.LivroWebServiceImpService;

public class TestaAplicacaoClient {

    public static void main(final String[] args) {

        final LivroWebServiceImpService livroWebServiceImpService = new LivroWebServiceImpService();
        livroWebServiceImpService.setHandlerResolver(new HeaderHandlerResolver());
        final LivroWebService livroWebService = livroWebServiceImpService.getLivroWebServicePort();
        System.out.println(livroWebService.listarLivros());

        // http://localhost:8080/soa-aplicado/livroWebService?wsdl
        //
        // wsimport -keep -d /home/fernando/workspace/soa-aplicado/soa-aplicado-cap-06-4-client/src/main/java/ -Xnocompile -verbose /home/fernando/workspace/soa-aplicado/soa-aplicado-cap-06-4/src/main/resources/livroWebService.wsdl
        //

    }
}
