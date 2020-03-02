package br.com.fernando.server.application;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPFaultException;

import br.com.fernando.core.Livro;
import br.com.fernando.core.Usuario;
import br.com.fernando.server.persistence.LivroDAO;

@WebService(serviceName = "livros")
public class LivrosService {

    // Atributos no Header da Mensagem
    //
    // O atributo 'usuario' foi coloca no header da mensagem SOAP, qual a diferenca?
    // Em geral, o elemento 'Header' num envelope SOAP eh utilizado para passagem de metadados e itens que nao fazem
    // parte da requisicao propriamente dita. Neste caso, um operacao de criacao de livro. O usuario eh apenas um
    // elemento de seguranca, nao faz parte da criacao de um livro. A especificacao SOAP proibi que as excecoes
    // trafegadas no corpo da mensagem sejam relativas a problemas de dados passados no elemento 'Header'. Tenha
    // em mente que estes problemas sao tratados pela 'WS-Security'
    //
    // Excecoes nas Mensagens
    //
    // Quando eh lancado uma exception (excecao verificada) o conteudo do corpo da mensagem eh um tag 'Fault'.
    // (que possui o mesmo namespace que as tags 'Body' e 'Envolope').
    //
    // No conteudo doe elemento , temos tres outros elementos:
    // 'faultCode' : O campo 'faultcode' aceita quatro valores (VersionMismatch, MustUnderstand, Client e Server);
    // 'faultstring': O campo 'faultstring' contem uma descricao do problema;
    // 'detail' : contem a especificacao do problema em detalhes;
    //
    // Veja a classe 'UsuarioNaoAutorizadoException'
    @WebMethod(operationName = "criarLivro")
    public void criarLivro(@WebParam(name = "livro") final Livro livro, @WebParam(name = "usuario", header = true) final Usuario usuario) throws UsuarioNaoAutorizadoException, SOAPException {

        if (usuario.getLogin().equals("soa") && usuario.getSenha().equals("soa")) {
            obterDAO().criarLivro(livro);
        } else if (usuario.getNome().equals("faultCode")) {
            //
            // Atraves da API do JAX-WS eh possivel realizar manipulacao das excecoes por meio de manipulacao direta e XML, usando um mecanismo hibrico de API JAX-WS e DOM.
            // A partir da classe 'SOAPFactory' podemos manipular livremente estes campos sem precisar criar classe nenhuma...
            // Este codigo abaixo gera a seguingte saida:
            //
            // <S:Fault xmlns:ns4="http://www.w3.org/2003/05/soap-envelope">
            // // <faultcode>S:Client.autorizacao</faultcode>
            // // <faultstring>Usuário não autorizado</faultstring>
            // // <faultactor>http://servicos.estoque.knight.com/LivrosServices</faultactor>
            // </S:Fault>

            final SOAPFault soapFault = SOAPFactory.newInstance().createFault("Usuário não autorizado", new QName(SOAPConstants.URI_NS_SOAP_1_1_ENVELOPE, "Client.autorizacao"));
            soapFault.setFaultActor("http://application.server.fernando.com.br/LivrosService");

            throw new SOAPFaultException(soapFault);
        } else {
            throw new UsuarioNaoAutorizadoException("Usuário não autorizado");
        }
    }

    private LivroDAO obterDAO() {
        return new LivroDAO();
    }

    public static void main(final String[] args) {
        Endpoint.publish("http://localhost:8080/soa-aplicado/livros", new LivrosService());
        System.out.println("Serviço inicializado!");

    }
}
