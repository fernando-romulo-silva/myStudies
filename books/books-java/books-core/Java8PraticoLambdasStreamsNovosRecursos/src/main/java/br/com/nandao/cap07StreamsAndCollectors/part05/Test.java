package br.com.nandao.cap07StreamsAndCollectors.part05;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.nandao.Usuario;

// Avançado: por que não há um toList em Stream?
public class Test {

    public static void main(String[] args) {

        final Usuario user1 = new Usuario("Paulo Silveira", 150);
        final Usuario user2 = new Usuario("Rodrigo Turini", 120);
        final Usuario user3 = new Usuario("Guilherme Silveira", 90);

        final List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

        // Também podemos utilizar o método toSet para coletar as informações desse Stream em um Set<Usuario>:

        final Set<Usuario> maisQue100 = usuarios.stream() //
            .filter(u -> u.getPontos() > 100).collect(Collectors.toSet());

        // Há ainda o método toCollection, que permite que você escolha a implementação
        // que será devolvida no final da coleta:
        final Set<Usuario> set = usuarios //
            .stream() //
            .collect(Collectors.toCollection(HashSet::new));
        
        // O toCollection recebe um Supplier<T>, que já vimos. 
        // A interface Supplier é como uma factory, possuindo um único método ( get) que não recebe argumento e devolve T. 
        // Relembrando method references com construtores, 
        // fazer toCollection(HashSet::new) é o mesmo que toCollection(() -> new HashSet<Usuario>()) nesse caso.
        //
        // Você também pode invocar toArray que devolve um array de Object em
        // um Stream, ou invocar o toArray passando a forma de construir uma array
        // (através de um IntSupplier). Um exemplo seria Usuario[] array =
        // stream.toArray(Usuario[]::new).

    }

}
