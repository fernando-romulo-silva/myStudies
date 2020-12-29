package br.com.fernando.client;

import java.net.URL;
import java.util.List;

import br.com.fernando.core.Livro;

public class Client {

    public static void main(final String[] args) throws Exception {

        final ListagemLivrosService livrosService = new ListagemLivrosService(new URL("http://localhost:8080/soa-aplicado/livros"));

        final ListagemLivros listagemLivros = livrosService.getListagemLivrosPort();

        final List<Livro> livros = listagemLivros.listarLivrosPaginacao(0, 1);

        for (final Livro livro : livros) {
            System.out.println("Nome do livro: " + livro.getNome());
        }
    }
}
