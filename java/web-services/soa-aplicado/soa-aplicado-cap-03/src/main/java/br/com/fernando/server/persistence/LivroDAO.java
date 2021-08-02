package br.com.fernando.server.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import br.com.fernando.core.Autor;
import br.com.fernando.core.EBook;
import br.com.fernando.core.Livro;

public class LivroDAO {

    private static List<Livro> livros;

    static {
        livros = new ArrayList<>();
        livros.add(new Livro(2012, new ArrayList<>(Arrays.asList(new Autor("Paulo Silveira", new Date()), new Autor("Adriano Almeida", new Date()))), "Casa do Código", "Guia do Programador", "Vá do \"nunca programei\" ..."));

        livros.add(new Livro(2012, new ArrayList<>(Arrays.asList(new Autor("Vinícius Baggio Fuentes", new Date()))), "Casa do Código", "Ruby on Rails", "Crie rapidamente aplicações web"));

        final EBook soaBook = new EBook(2012, new ArrayList<>(Arrays.asList(new Autor("Alexandre Saudate", new Date()))), "Casa do Código", "SOA Aplicado", "Aprenda SOA de forma prática");
        livros.add(soaBook);
    }

    public List<Livro> listarLivros() {
        return livros;
    }

    public List<Livro> listarLivros(final Integer numeroDaPagina, final Integer tamanhoDaPagina) {
        final List<Livro> livros = listarLivros();

        int indiceInicial = numeroDaPagina * tamanhoDaPagina;
        int indiceFinal = indiceInicial + tamanhoDaPagina;

        indiceFinal = indiceFinal > livros.size() ? livros.size() : indiceFinal;
        indiceInicial = indiceInicial > indiceFinal ? indiceFinal : indiceInicial;

        return livros.subList(indiceInicial, indiceFinal);

    }

    public void criarLivro(final Livro livro) {
        livros.add(livro);
    }

}
