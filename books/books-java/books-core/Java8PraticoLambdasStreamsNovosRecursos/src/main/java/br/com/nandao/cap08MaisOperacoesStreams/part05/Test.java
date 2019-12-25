package br.com.nandao.cap08MaisOperacoesStreams.part05;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.IntBinaryOperator;

import br.com.nandao.Usuario;

// Operações de redução
public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 90);

    static final List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

    public static void main(String[] args) {

        // Operações que utilizam os elementos da stream para retornar um valor final são
        // frequentemente chamadas de operações de redução (reduction). Um exemplo é o
        // average, que já havíamos visto:

        final double pontuacaoMedia = usuarios.stream() //
            .mapToInt(Usuario::getPontos) //
            .average() //
            .getAsDouble();

        System.out.println(pontuacaoMedia);

        // Há outros métodos úteis como o average: o count, o min, o max e o sum.
        // Esse último, como o average, encontra-se apenas nos streams primitivos. O min
        // e o max pedem um Comparator como argumento. Todos, com exceção do sum e
        // count, trabalham com Optional. Por exemplo:

        final Optional<Usuario> max = usuarios.stream() //
            .max(Comparator.comparing(Usuario::getPontos));

        final Usuario maximaPontuacao = max.get();

        System.out.println(maximaPontuacao);

        // Se desejarmos somar todos os pontos dos usuários, fazemos:
        final int total = usuarios.stream() //
            .mapToInt(Usuario::getPontos) //
            .sum();

        System.out.println(total);

        // Essa soma é executada através de uma operação de redução que podemos deixar
        // bem explícita. Como ela funciona? Ela pega o primeiro elemento, que é a pontuação
        // do primeiro usuário do stream, e guarda o valor acumulado até então, com uma
        // operação de soma. Também precisamos ter um valor inicial que, para o caso da
        // soma, é zero.
        //
        // Podemos quebrar essa operação de soma para enxergar melhor o que é uma operação
        // de redução. Repare nas definições:

        final int valorInicial = 0;

        // IntBinaryOperator é uma interface funcional que define o método
        // applyAsInt, que recebe dois inteiros e devolve um inteiro;
        final IntBinaryOperator operacao = (a, b) -> a + b;

        final int total2 = usuarios.stream() //
            .mapToInt(Usuario::getPontos) //
            .reduce(valorInicial, operacao);

        System.out.println(total2);

        // Qual é a vantagem de usarmos a redução em vez do sum? Nenhuma. O importante
        // é conhecê-lo para poder realizar operações que não se encontram no Stream.
        // Por exemplo? Multiplicar todos os pontos:

        final int multiplicacao = usuarios.stream() //
            .mapToInt(Usuario::getPontos) //
            .reduce(1, (a, b) -> a * b); //
        
        System.out.println(multiplicacao);
        
        // Há também alguns casos especiais em que invocar o map pode ser custoso, e o
        // melhor seria fazer a operação de soma diretamente. Esse não é o nosso caso, mas só
        // para enxergarmos o exemplo, a soma sem o map ficaria assim:
        
        final int total3 = usuarios.stream() //
        .reduce(0, (atual, u) -> atual + u.getPontos(), Integer::sum);
        
        System.out.println(total3);
        
        // Esse overload do reduce recebe mais um lambda que, no nosso caso, é o
        // Integer::sum. Esse lambda a mais serve para combinar os valores de reduções
        // parciais, no caso de streams paralelos.

    }

}
