package br.com.nandao.cap02OlaLambda.part01;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import br.com.nandao.Usuario;

// Olá, Lambda!
public class Test {

    final static Usuario user1 = new Usuario("Paulo Silveira", 150);
    final static Usuario user2 = new Usuario("Rodrigo Turini", 120);
    final static Usuario user3 = new Usuario("Guilherme Silveira", 190);
    final static List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

    // Loops da maneira antiga
    public static void test1() {

        for (final Usuario u : usuarios) {
            System.out.println(u.getNome());
        }
    }

    // Um novo método em todas as coleções: forEach

    public static void test2() {
        // A partir do Java 8 temos acesso a um novo método nessa nossa lista: o forEach.
        // De onde ele vem? Veremos mais adiante. Iniciaremos por utilizá-lo. Podemos fazer algo como:
        // usuarios.forEach(...);
        // Para cada usuário, o que ele deve fazer? Imprimir o nome. Mas qual é o argumento que esse método forEach recebe?
        // Ele recebe um objeto do tipo java.util.function.Consumer, que tem um único método, o accept.

        final Mostrador mostrador1 = new Mostrador();
        usuarios.forEach(mostrador1);

        // Sabemos que é uma prática comum utilizar classes anônimas para essas tarefas
        // mais simples. Em vez de criar uma classe Mostrador só pra isso, podemos fazer  tudo de uma tacada só:

        final Consumer<Usuario> mostrador2 = new Consumer<Usuario>() {
            @Override
            public void accept(final Usuario u) {
                System.out.println(u.getNome());
            }
        };

        usuarios.forEach(mostrador2);
    }

    public static void main(final String... args) {
        test1();
    }
}

class Mostrador implements Consumer<Usuario> {

    @Override
    public void accept(final Usuario u) {
        System.out.println(u.getNome());
    }

}