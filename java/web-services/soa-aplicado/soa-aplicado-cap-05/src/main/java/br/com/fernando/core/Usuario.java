package br.com.fernando.core;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.fernando.core.rest.Link;
import br.com.fernando.core.rest.RESTEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
public class Usuario extends EntidadeModelo implements RESTEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String login;
    private String senha;

    @OneToOne(cascade = { CascadeType.ALL }, orphanRemoval = true)
    @XmlTransient
    private Imagem imagem;

    @XmlElement(name = "link")
    @Transient
    private Collection<Link> links;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

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

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(final Imagem imagem) {
        this.imagem = imagem;
    }

    @Override
    public void adicionarLink(final Link link) {
        if (links == null) {
            links = new ArrayList<>();
        }
        links.add(link);
    }

}
