package br.com.fernando.core;

import java.util.List;

public class EBook extends Livro {

    private FormatoArquivo formato = FormatoArquivo.PDF;

    public EBook() {
        super();
    }

    public EBook(final Integer anoDePublicacao, final List<Autor> autores, final String editora, final String nome, final String resumo) {
        super(anoDePublicacao, autores, editora, nome, resumo);
    }

    public FormatoArquivo getFormato() {
        return formato;
    }

    public void setFormato(final FormatoArquivo formato) {
        this.formato = formato;
    }
}
