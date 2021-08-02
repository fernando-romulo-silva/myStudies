package br.com.fernando.server.application;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.ws.WebFault;

@WebFault(targetNamespace = "http://application.server.fernando.com.br/excecoes/", name = "UsuarioNaoAutorizado")
public class UsuarioNaoAutorizadoException extends Exception {

    private static final long serialVersionUID = 1L;

    public UsuarioNaoAutorizadoException() {
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

    public UsuarioFaultInfo getFaultInfo() {
        return new UsuarioFaultInfo(getMessage());
    }

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
