package br.com.nandao.cap05OrdenandoNoJava8.part03;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.nandao.Usuario;

// Métodos estáticos na interface Comparator
public class Test {

    // É isso mesmo: além de poder ter métodos default dentro de uma interface, agora
    // podemos ter métodos estáticos. A interface Comparator possui alguns deles.
    public static void test1() {

        final Usuario user1 = new Usuario("Paulo Silveira", 150);
        final Usuario user2 = new Usuario("Rodrigo Turini", 120);
        final Usuario user3 = new Usuario("Guilherme Silveira", 190);

        final List<Usuario> usuarios1 = Arrays.asList(user1, user2, user3);

        final Comparator<Usuario> comparator1 = Comparator.comparing(u -> u.getNome());

        usuarios1.sort(comparator1);

        usuarios1.sort(Comparator.comparing(u -> u.getNome()));
    }

    // Indexando pela ordem natural
    public static void test2() {

        final List<String> palavras = Arrays.asList("Casa do Código", "Alura", "Caelum");

        Collections.sort(palavras);

        // Tentar fazer, no Java 8 palavras.sort() não compila. Não há esse método
        // sort em List que não recebe parâmetro. Nem teria como haver, pois o compilador
        // não teria como saber se o objeto invocado é uma List de Comparable ou de suas filhas.

        // Tem que usar com comparator, alguns ja tem
        palavras.sort(Comparator.naturalOrder());
        // ou
        palavras.sort(Comparator.reverseOrder());
    }

    public static void main(final String[] args) {

    }

}
