package br.com.nandao.cap06MethodReferences.part01;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import br.com.nandao.Usuario;

// Tornando todos os usuários moderadores
public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 190);

    static final List<Usuario> usuarios1 = Arrays.asList(user1, user2, user3);

    public static void test1() {

        // Por exemplo, como faríamos para tornar moderadores todos os elementos de uma lista de usuários? 
        // Poderíamos fazer um forEach simples chamando o método tornaModerador de cada elemento iterado:

        usuarios1.forEach(u -> u.tornaModerador());

        // Mas é possível executar essa mesma lógica de uma forma muito mais simples,
        // apenas informando ao nosso forEach qual método deverá ser chamado. 
        // Podemos fazer isso usando um novo recurso da linguagem, o method reference.

        usuarios1.forEach(Usuario::tornaModerador);

        // Já vimos que o método forEach espera receber um Consumer<Usuario>
        // como parâmetro, mas por que eu posso passar uma referência de método no lugar
        // dessa interface funcional? Você já deve imaginar a resposta: da mesma forma como
        // uma expressão lambda, o method reference é traduzido para uma interface funcional!
        // Portanto, o seguinte código compila:

        final Consumer<Usuario> tornaModerador = Usuario::tornaModerador;
        usuarios1.forEach(tornaModerador);
    }


}
