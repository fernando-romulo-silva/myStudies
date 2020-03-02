package br.com.fernando.server.application;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.ws.WebFault;

// Excecoes
//
// Com excecoes podemos especicificar o erro na mensagem de volta.
// Com a anotacao '@WebFault' customizamos um mapeamento mais detalhado da excecao (trocar 'namespace', trocar
// o nome do elemento, etc). A mensagem de erro que vai aparecer na mensagem SOAP eh a mensagem que esta 
// dentro da excecao. Mas podemos fornecer um medodo chamado 'getFaultInfo'. Este memetodo pode retornar um objeto
// (compativel com JAXB) para ser usado como o mapeamento da propria excecao.
// 
// Muito cuidado com o 'targetNamespace', para gerar um wsdl correta eh necessario que seja igual ao do projeto, entao
// geralmente nao coloque este campo
//
@WebFault(name = "UsuarioNaoAutorizado")
public class UsuarioNaoAutorizadoException extends Exception {

    private static final long serialVersionUID = 1L;

    public UsuarioNaoAutorizadoException() {
        super();
    }

    public UsuarioNaoAutorizadoException(final String message) {
        super(message);
    }

    public UsuarioNaoAutorizadoException(final Throwable cause) {
        super(cause);
    }

    public UsuarioNaoAutorizadoException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UsuarioNaoAutorizadoException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    // Com o metodo 'getFaultInfo' o WSDL pode usar uma classe para especificar mais o erro
    public UsuarioFaultInfo getFaultInfo() {
        return new UsuarioFaultInfo(getMessage());
    }

    //
    // Criando uma classe para especificar mais o erro da exception
    //
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class UsuarioFaultInfo {

        @XmlAttribute
        private String mensagem;

        private Date data;

        public UsuarioFaultInfo(final String mensagem) {
            this.mensagem = mensagem;
            this.data = new Date();
        }

        public UsuarioFaultInfo() {
        }

        public Date getData() {
            return data;
        }
    }
}
