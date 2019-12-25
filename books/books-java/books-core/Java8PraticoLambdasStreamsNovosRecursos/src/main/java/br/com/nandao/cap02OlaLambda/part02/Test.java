package br.com.nandao.cap02OlaLambda.part02;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import br.com.nandao.Usuario;

// Que entre o Lambda!
// Simplificando bastante, um lambda no Java é uma maneira mais simples de implementar
// uma interface que só tem um único método.
public class Test {

    final static Usuario user1 = new Usuario("Paulo Silveira", 150);
    final static Usuario user2 = new Usuario("Rodrigo Turini", 120);
    final static Usuario user3 = new Usuario("Guilherme Silveira", 190);
    final static List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

    public static void test1() {

        // O trecho (Usuario u) - > {System.out.println(u.getNome());}; é um lambda do Java 8.
        final Consumer<Usuario> mostrador1 = (final Usuario u) -> {
            System.out.println(u.getNome());
        };

        // Não está satisfeito? Caso o bloco dentro de { } contenha apenas uma instrução, podemos omiti-lo e remover também o ponto e vírgula:
        final Consumer<Usuario> mostrador2 = u -> {
            System.out.println(u.getNome());
        };

        // Agora fica até possível de escrever em uma única linha:
        final Consumer<Usuario> mostrador3 = u -> System.out.println(u.getNome());
        
        // Então u -> System.out.println(u.getNome()) infere pro mesmo
        // lambda que (Usuario u) -> {System.out.println(u.getNome());}, se
        // forem atribuídos a um Consumer<Usuario>. Podemos passar esse trecho de código 
        // diretamente para usuarios.forEach em vez de declarar a variável temporária mostrador:
        usuarios.forEach(u -> System.out.println(u.getNome()));
    }

    public static void main(final String... args) {
        test1();
    }
}
