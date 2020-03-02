package br.com.fernando.server.application;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

import br.com.fernando.core.Livro;
import br.com.fernando.core.Usuario;
import br.com.fernando.server.persistence.LivroDAO;

@WebService(serviceName = "livros")
public class LivrosService {

    @WebMethod(operationName = "criarLivro")
    public void criarLivro(@WebParam(name = "livro") final Livro livro, @WebParam(name = "usuario", header = true) final Usuario usuario) throws UsuarioNaoAutorizadoException, SOAPException {

        if (usuario.getLogin().equals("soa") && usuario.getSenha().equals("soa")) {
            obterDAO().criarLivro(livro);
        } else if (usuario.getNome().equals("faultCode")) {

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
}
