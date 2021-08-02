package br.com.nandao.cap05OrdenandoNoJava8.part05;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

import br.com.nandao.Usuario;

// Ordenando por pontos e o autoboxing
public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 190);

    static final List<Usuario> usuarios1 = Arrays.asList(user1, user2, user3);

    public static void test1() {

        // Vc pode fazer em uma linha
        usuarios1.sort(Comparator.comparing(u -> u.getPontos()));

        // Para enxergar melhor o que acontece, podemos quebrar esse código em mais   linhas e variáveis locais, como já vimos:
        final Function<Usuario, Integer> extraiPontos = u -> u.getPontos();

        final Comparator<Usuario> comparator = Comparator.comparing(extraiPontos);
    }

    // Autoboxing nos lambdas

    public static void test2() {

        // Há um problema aqui. O extraiPontos gerado terá um método apply que
        // recebe um Usuario e devolve um Integer, em vez de um int. Isso gerará
        // um autoboxing toda vez que esse método for invocado. E ele poderá ser invocado
        // muitíssimas vezes pelo sort, através do compare do Comparator devolvido pelo Comparator.comparing.

        final ToIntFunction<Usuario> extraiPontos = u -> u.getPontos();
        final Comparator<Usuario> comparator = Comparator.comparingInt(extraiPontos);
    }

    public static void main(final String[] args) {

    }
}
