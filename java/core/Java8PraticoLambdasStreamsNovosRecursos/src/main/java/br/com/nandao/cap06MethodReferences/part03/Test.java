package br.com.nandao.cap06MethodReferences.part03;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import br.com.nandao.Usuario;

// Compondo comparators
public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 190);

    static final List<Usuario> usuarios1 = Arrays.asList(user1, user2, user3);

    public static void test1() {

        // Assim como ordenamos pelo nome, vimos que podemos ordenar os usuários pelos  pontos:

        usuarios1.sort(comparingInt(u -> u.getPontos()));

        // Utilizamos o comparingInt em vez do comparing para evitar o boxing desnecessário.
        // Usando a nova sintaxe, podemos fazer:

        usuarios1.sort(comparingInt(Usuario::getPontos));

        // E se quisermos um critério de comparação mais elaborado? Por exemplo: ordenar
        // pelos pontos e, no caso de empate, ordenar pelo nome.
        // Isso é possível graças a alguns métodos default existentes em Comparator,
        // como o thenComparing. Vamos criar um Comparator que funciona dessa forma:

        final Comparator<Usuario> c = comparingInt(Usuario::getPontos) //
                .thenComparing(Usuario::getNome);

        // Existem também variações desse método para evitar o boxing de primitivos,
        // como thenComparingInt

        usuarios1.sort(Comparator.comparingInt(Usuario::getPontos) //
                .thenComparing(Usuario::getNome));

        // Há outro método que você pode aproveitar para compor comparators, porém
        // passando o composto como argumento: o nullsLast.

        usuarios1.sort(Comparator.nullsLast(Comparator.comparing(Usuario::getNome)));

        // Com isso, todos os usuários nulos da nossa lista estarão posicionados no fim, e
        // o restante ordenado pelo nome! Há também o método estático nullsFirst
    }

    // comparator.reversed()
    public static void test2() {

        // E se desejar ordenar por pontos, porém na ordem decrescente? Utilizamos o
        // método default reversed() no Comparator:

        usuarios1.sort(comparing(Usuario::getPontos).reversed());
    }

    public static void main(final String[] args) {

    }
}
