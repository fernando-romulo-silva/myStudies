package br.com.fernando.core;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer anoDePublicacao;

    @XmlElementWrapper(name = "autores")
    @XmlElement(name = "autor")
    @XmlJavaTypeAdapter(value = AdaptadorAutores.class)
    @ManyToMany(cascade = { CascadeType.PERSIST })
    private List<Autor> autores;

    private String editora;

    private String nome;

    private String resumo;

    private Date dataDeCriacao = new Date();

    public Livro() {
    }

    public Livro(final Integer anoDePublicacao, final List<Autor> autores, final String editora, final String nome, final String resumo) {
        this();
        this.anoDePublicacao = anoDePublicacao;
        this.autores = autores;
        this.editora = editora;
        this.nome = nome;
        this.resumo = resumo;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Integer getAnoDePublicacao() {
        return anoDePublicacao;
    }

    public void setAnoDePublicacao(final Integer anoDePublicacao) {
        this.anoDePublicacao = anoDePublicacao;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(final List<Autor> autores) {
        this.autores = autores;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(final String editora) {
        this.editora = editora;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(final String resumo) {
        this.resumo = resumo;
    }

    public Date getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(final Date dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public static class AdaptadorAutores extends XmlAdapter<String, Autor> {

        @Override
        public String marshal(final Autor autor) throws Exception {
            return autor.getNome();
        }

        @Override
        public Autor unmarshal(final String autor) throws Exception {
            return new Autor(autor, null);
        }

    }
}
