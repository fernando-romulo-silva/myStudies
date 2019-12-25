package br.com.nandao.cap06MethodReferences.part05;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import br.com.nandao.Usuario;

// Referenciando métodos que recebem argumentos
public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 190);

    static final List<Usuario> usuarios1 = Arrays.asList(user1, user2, user3);

    public static void main(String[] args) {

        // Também podemos referenciar métodos que recebem argumentos, como por exemplo
        // o println da instância PrintStream out da classe java.lang.System.
        // Para imprimir todos os itens de uma lista de usuários, podemos fazer um
        // forEach que recebe como parâmetro uma referência ao método println:

        usuarios1.forEach(System.out::println);

        // temos um código equivalente a essa expressão lambda:
        final Consumer<Usuario> lambda = u -> System.out.println(u);

        // Ou seja, o compilador sabe que ao iterar um uma lista de usuários, a cada
        // iteração do método forEach teremos um objeto do tipo Usuario, e infere que
        // esse é o parâmetro que deverá ser passado ao method reference.
        // Nosso código traduzido para Java 7, por exemplo, seria equivalente ao código
        // a seguir:

        for (final Usuario u : usuarios1) {
            System.out.println(u);
        }
    }
}