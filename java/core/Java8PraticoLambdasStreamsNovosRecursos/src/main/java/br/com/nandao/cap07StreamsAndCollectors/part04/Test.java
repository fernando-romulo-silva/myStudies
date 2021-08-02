package br.com.nandao.cap07StreamsAndCollectors.part04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import br.com.nandao.Usuario;

// Collctors
public class Test {

    // Podemos usar o método collect para resgatar esses elementos do nosso
    // Stream<Usuario> para uma List. Porém, repare sua assinatura:
    //
    // <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner);
    //
    // Tres argumentos:
    // O primeiro é uma factory que vai criar o objeto que será devolvido no final da coleta
    // O segundo é o método que será invocado para adicionar cada elemento.
    // O terceiro pode ser invocado se precisarmos adicionar mais de um elemento ao mesmo tempo (por exemplo,
    // se formos usar uma estratégia de coletar elementos paralelamente, como veremos no futuro).

    public static void main(String[] args) {

        final Usuario user1 = new Usuario("Paulo Silveira", 150);
        final Usuario user2 = new Usuario("Rodrigo Turini", 120);
        final Usuario user3 = new Usuario("Guilherme Silveira", 90);

        final List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

        // Para fazer essa transformação simples, eu teria que escrever um código como esse:

        final Supplier<ArrayList<Usuario>> supplier = ArrayList::new;

        final BiConsumer<ArrayList<Usuario>, Usuario> accumulator = ArrayList::add;

        final BiConsumer<ArrayList<Usuario>, ArrayList<Usuario>> combiner = ArrayList::addAll;

        final List<Usuario> maisQue100 = usuarios //
            .stream() // Stream
            .filter(u -> u.getPontos() > 100) // filtro
            .collect(supplier, accumulator, combiner); // colect

        // Bastante complicado, não acha? Poderíamos tentar simplificar deixando o có-
        // digo em um único statement, mas ainda sim teríamos uma operação complicada e
        // perderíamos um pouco na legibilidade:

        usuarios.stream() //
            .filter(u -> u.getPontos() > 100) //
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        // A interface Collector nada mais é que alguém que tem um supplier, um accumulator
        // e um combiner. A vantagem é que existem vários Collectors prontos.
        // Podemos simplificar bastante nosso código, passando como parâmetro em nosso
        // método collect o Collectors.toList(), uma das implementações dessa nova interface.

        usuarios //
            .stream() //
            .filter(u -> u.getPontos() > 100) //
            .collect(Collectors.toList());
        
        // Collectors.toList devolve um coletor bem parecido com o qual criamos
        // na mão, com a diferença de que ele devolve uma lista que não sabemos se é mutável,
        // se é thread-safe ou qual a sua implementação.
    }

}
