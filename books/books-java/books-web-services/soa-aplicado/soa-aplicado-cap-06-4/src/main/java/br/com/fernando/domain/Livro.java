package br.com.fernando.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "livro", propOrder = { "anoDePublicacao", "autores", "editora", "nome", "resumo" })
public class Livro {

    protected Integer anoDePublicacao;

    @XmlElement(nillable = true)
    protected List<String> autores;

    protected String editora;

    protected String nome;

    protected String resumo;

    public Livro() {
        super();
    }

    public Livro(final Integer anoDePublicacao, final List<String> autores, final String editora, final String nome, final String resumo) {
        super();
        this.anoDePublicacao = anoDePublicacao;
        this.autores = autores;
        this.editora = editora;
        this.nome = nome;
        this.resumo = resumo;
    }

    public Integer getAnoDePublicacao() {
        return anoDePublicacao;
    }

    public void setAnoDePublicacao(final Integer value) {
        this.anoDePublicacao = value;
    }

    public List<String> getAutores() {
        if (autores == null) {
            autores = new ArrayList<String>();
        }
        return this.autores;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(final String value) {
        this.editora = value;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String value) {
        this.nome = value;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(final String value) {
        this.resumo = value;
    }

}