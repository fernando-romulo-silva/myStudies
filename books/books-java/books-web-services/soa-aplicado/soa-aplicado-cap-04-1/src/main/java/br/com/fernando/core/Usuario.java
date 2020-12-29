package br.com.fernando.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Usuario {

    private String nome;
    private String login;
    private String senha;

    public String getNome() {
        if (nome != null) {
            return nome;
        }
        return "";
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        if (login != null) {
            return login;
        }
        return "";
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getSenha() {
        if (senha != null) {
            return senha;
        }
        return "";
    }

    public void setSenha(final String senha) {
        this.senha = senha;
    }

}
