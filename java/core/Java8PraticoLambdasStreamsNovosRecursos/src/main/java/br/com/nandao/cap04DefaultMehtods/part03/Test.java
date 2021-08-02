package br.com.nandao.cap04DefaultMehtods.part03;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.nandao.Usuario;

// Mais um novo método em Collection: removeIf
//
// A interface Collection ganhou novos métodos com implementação default. Vimos
// o forEach. Um outro é o removeIf, que recebe um Predicate. O
// Predicate é uma interface funcional que permite testar objetos de um determinado
// tipo. Dado um Predicate, o removeIf vai remover todos os elementos
// que devolverem true para esse predicado.
public class Test {

    public static void test1() {

        final Predicate<Usuario> predicado = new Predicate<Usuario>() {
            @Override
            public boolean test(final Usuario u) {
                return u.getPontos() > 160;
            }
        };

        final Usuario user1 = new Usuario("Paulo Silveira", 150);
        final Usuario user2 = new Usuario("Rodrigo Turini", 120);
        final Usuario user3 = new Usuario("Guilherme Silveira", 190);

        final List<Usuario> usuarios = new ArrayList<>();

        usuarios.add(user1);
        usuarios.add(user2);
        usuarios.add(user3);

        usuarios.removeIf(predicado);
        usuarios.forEach(u -> System.out.println(u));

        // Podemos também usar aqui o lambda. Não precisamos nem mesmo declarar a
        // variável predicado! Podemos passar o u.getPontos() > 160 direto para o
        // removeIf:

        usuarios.removeIf(u -> u.getPontos() > 160);
    }

    public static void main(final String[] args) {
        test1();
    }

}
