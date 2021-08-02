package br.com.nandao.cap05OrdenandoNoJava8.part02;

import java.util.Arrays;
import java.util.List;

import br.com.nandao.Usuario;

// O método List.sort
public class Test {

    public static void test1() {

        final Usuario user1 = new Usuario("Paulo Silveira", 150);
        final Usuario user2 = new Usuario("Rodrigo Turini", 120);
        final Usuario user3 = new Usuario("Guilherme Silveira", 190);

        final List<Usuario> usuarios1 = Arrays.asList(user1, user2, user3);

        // Podemos ordenar uma lista de usuários de forma ainda mais sucinta:

        usuarios1.sort((u1, u2) -> u1.getNome().compareTo(u2.getNome()));

    }

    public static void main(final String[] args) {

    }
}
