package br.com.nandao.cap06MethodReferences.part02;

import static java.util.Comparator.comparing;

import java.util.Arrays;
import java.util.List;

import br.com.nandao.Usuario;

// Comparando de uma forma ainda mais enxuta
public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 190);

    static final List<Usuario> usuarios1 = Arrays.asList(user1, user2, user3);

    public static void main(final String[] args) {

        // Vimos que podemos facilmente ordenar nossa lista de usuários usando o mé-
        // todo comparing da classe Comparator, passando uma expressão lambda como parâmetro:
        usuarios1.sort(comparing(u -> u.getNome()));
        // Vamos ver como esse código ficaria usando uma referência ao método getNome,
        // no lugar da expressão u -> u.getNome():
        usuarios1.sort(comparing(Usuario::getNome));

    }
}
