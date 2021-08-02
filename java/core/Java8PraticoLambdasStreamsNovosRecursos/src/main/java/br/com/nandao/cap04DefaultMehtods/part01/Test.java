package br.com.nandao.cap04DefaultMehtods.part01;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import br.com.nandao.Usuario;

// O método forEach na interface Iterable
// Como adicionar um método em uma interface e garantir que todas as implementações
// o possuam implementado? Com um novo recurso, declarando código dentro de um método de uma interface!

public class Test {

    @FunctionalInterface
    interface Validador<T> {

        boolean valida(T t);

        // Método com código dentro de interfaces!
        default void forEach(final Consumer<? super T> action) {
            Objects.requireNonNull(action);
        }
    }

    // Repare que, por ser um método default, a classe pode ser explicitamente anotada com @FunctionalInterface.
    // O método andThen pode ser usado para compor instâncias da interface
    // Consumer para que possam ser executadas sequencialmente, por exemplo:    
    public static void test1() {

        final Usuario user1 = new Usuario("Paulo Silveira", 150);
        final Usuario user2 = new Usuario("Rodrigo Turini", 120);
        final Usuario user3 = new Usuario("Guilherme Silveira", 190);

        final List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

        final Consumer<Usuario> mostraMensagem = u -> System.out.println("antes de imprimir os nomes");
        final Consumer<Usuario> imprimeNome = u -> System.out.println(u.getNome());

        usuarios.forEach(mostraMensagem.andThen(imprimeNome));

    }

}
