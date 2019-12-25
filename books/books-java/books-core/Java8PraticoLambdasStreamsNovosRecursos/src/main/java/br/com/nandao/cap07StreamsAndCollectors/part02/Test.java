package br.com.nandao.cap07StreamsAndCollectors.part02;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import br.com.nandao.Usuario;

public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 190);

    static final List<Usuario> usuarios1 = Arrays.asList(user1, user2, user3);

    public static void test1() {

        // Como filtro uma coleção? Posso fazer um laço e, para cada elemento, usar um if
        // para saber se devemos ou não executar uma tarefa.
        // Para tornar moderadores os usuários com mais de 100 pontos podemos fazer:

        for (final Usuario usuario : usuarios1) {
            if (usuario.getPontos() > 100) {
                usuario.tornaModerador();
            }
        }

        // O Stream traz para o Java uma forma mais funcional de trabalhar com as nossas coleções,
        // usando uma interface fluente!

        final Stream<Usuario> stream = usuarios1.stream();

        // Usamos o metodo filter. Ele recebe um parâmetro do tipo Predicate<Usuario>, com um único
        // método de teste. É a mesma interface que usamos no Collection.removeIf.

        stream.filter(u -> {
            return u.getPontos() > 100;
        });

        // Podemos remover o return e com isso as chaves não são mais necessárias,
        // como já havíamos visto:
        stream.filter(u -> u.getPontos() > 100);

        // Claro, podemos ainda simplificar essa operação removendo a variável temporária:
        usuarios1 //
            .stream() //
            .filter(u -> u.getPontos() > 100);
    }

    public static void test2() {
        final Usuario user1 = new Usuario("Paulo Silveira", 150);
        final Usuario user2 = new Usuario("Rodrigo Turini", 120);
        final Usuario user3 = new Usuario("Guilherme Silveira", 90);

        final List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

        usuarios.stream().filter(u -> u.getPontos() > 100);

        usuarios.forEach(System.out::println);

        System.out.println();
        System.out.println();
        System.out.println();

        // E a saída desse código será:
        // Usuario Paulo Silveira
        // Usuario Rodrigo Turini
        // Usuario Guilherme Silveira

        // Por que na saída apareceu o Guilherme Silveira, sendo que ele não tem
        // mais de 100 pontos? Ele não aplicou o filtro na lista de usuários! Isso porque o
        // método filter, assim como os demais métodos da interface Stream, não alteram
        // os elementos do stream original! É muito importante saber que o Stream não tem
        // efeito colateral sobre a coleção que o originou.

        final Stream<Usuario> stream = usuarios.stream().filter(u -> u.getPontos() > 100);

        stream.forEach(System.out::println);

        // O Stream então é uma outra coleção? Certamente não. A mais clara diferença
        // é que um Stream nunca guarda dados. Ele não tem uma estrutura de dados interna
        // para armazenar cada um dos elementos: ele na verdade usa uma coleção ou algum
        // outro tipo de fonte para trabalhar com os objetos e executar uma série de operações

        // Vamos utilizar o retorno do filter para encaixar diretamente o forEach:
        usuarios.stream() //
            .filter(u -> u.getPontos() > 100) // filtro
            .forEach(Usuario::tornaModerador); // executando tornaModerador
        
       // forEach devolve void, nao temos a referencia ao Stream resultante do filter.
    }

    public static void main(String[] args) {
        test2();
    }

}
