package br.com.nandao.cap04DefaultMehtods.part02;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import br.com.nandao.Usuario;

// A interface Consumer não tem só um método!
//
// Tomamos o cuidado, nos capítulos anteriores, de deixar claro que uma interface funcional
// é aquela que possui apenas um método abstrato! Ela pode ter sim mais métodos, desde que sejam métodos default.
public class Test {

    @FunctionalInterface
    public interface ConsumerTest<T> {
        void accept(T t);

        default ConsumerTest<T> andThen(final ConsumerTest<? super T> after) {
            Objects.requireNonNull(after);
            return (final T t) -> {
                accept(t);
                after.accept(t);
            };
        }
    }

    // Repare que, por ser um método default, a classe pode ser explicitamente anotadacom @FunctionalInterface.
    // O método andThen pode ser usado para compor instâncias da interface
    // Consumer para que possam ser executadas sequencialmente, por exemplo:

    public static void main(final String... args) {

        final Usuario user1 = new Usuario("Paulo Silveira", 150);
        final Usuario user2 = new Usuario("Rodrigo Turini", 120);
        final Usuario user3 = new Usuario("Guilherme Silveira", 190);

        final List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

        final Consumer<Usuario> mostraMensagem = u -> System.out.println("antes de imprimir os nomes");
        final Consumer<Usuario> imprimeNome = u -> System.out.println(u.getNome());

        usuarios.forEach(mostraMensagem.andThen(imprimeNome));
    }
}
