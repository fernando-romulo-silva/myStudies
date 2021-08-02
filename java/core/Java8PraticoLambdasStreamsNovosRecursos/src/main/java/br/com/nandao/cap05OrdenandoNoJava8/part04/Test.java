package br.com.nandao.cap05OrdenandoNoJava8.part04;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import br.com.nandao.Usuario;

// Conhecendo melhor o Comparator.comparing
public class Test {

    // Dado um tipo T, o comparing recebe um lambda que devolve um tipo U. Isso é definido pela nova interface do Java 8, a Function: 

    public static <T, U extends Comparable<? super U>> Comparator<T> comparing(final Function<? super T, ? extends U> keyExtractor) {
        Objects.requireNonNull(keyExtractor);
        return (Comparator<T> & Serializable) (c1, c2) -> keyExtractor.apply(c1).compareTo(keyExtractor.apply(c2));
    }
    // Depois disso, ele usa a sintaxe do lambda para criar um Comparator, que já  utilizamos no começo deste capítulo

    public static void test1() {

        final Usuario user1 = new Usuario("Paulo Silveira", 150);
        final Usuario user2 = new Usuario("Rodrigo Turini", 120);
        final Usuario user3 = new Usuario("Guilherme Silveira", 190);

        final List<Usuario> usuarios1 = Arrays.asList(user1, user2, user3);

        final Function<Usuario, String> extraiNome = u -> u.getNome();

        // A chamada do Function.apply faz com que o nosso  u.getNome() seja invocado. Repare que apply é o único método abstrato da
        // interface Function e que o comparing gerou um Comparator que também  não previne o caso de a chave de comparação ser nula

        final Comparator<Usuario> comparator = Comparator.comparing(extraiNome);
        usuarios1.sort(comparator);

    }

    public static void main(final String[] args) {
        test1();
    }
}
