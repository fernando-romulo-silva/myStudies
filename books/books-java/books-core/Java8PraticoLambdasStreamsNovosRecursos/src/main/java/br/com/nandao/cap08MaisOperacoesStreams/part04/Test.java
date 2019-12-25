package br.com.nandao.cap08MaisOperacoesStreams.part04;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import br.com.nandao.Usuario;

// Enxergando a execução do pipeline com peek
public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 90);

    static final List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

    public static void main(String[] args) {

        // Podemos pedir para que o stream execute um tarefa toda vez que processar um elemento.
        // Fazemos isso através do peek:

        usuarios.stream() //
            .filter(u -> u.getPontos() > 100) //
            .peek(System.out::println) //
            .findAny();

        // Bem diferente de um forEach, que devolve void e é uma operação terminal,
        // o peek devolve um novo Stream e é uma operação intermediária. Ele não forçará
        // a execução do pipeline. O seguinte código simplesmente não imprime nada:

        usuarios.stream() //
            .filter(u -> u.getPontos() > 100) //
            .peek(System.out::println);

        // Por quê? Pois o peek devolve um novo Stream, onde está marcado para imprimir
        // todos os elementos processados. Ele só vai processar elementos quando encontrar
        // uma operação terminal, como o findAny, o collect ou o forEach.
        //
        // Com o peek, podemos ver se outras operações conseguem tirar vantagem do
        // lazyness. Experimente fazer o mesmo truque com o sorted:

        System.out.println();

        // Aqui o peek imprime o primeiro usuario ordenado (Guilherme)
        usuarios.stream() //
            .sorted(Comparator.comparing(Usuario::getNome)) // 1º Operacao executa - operacao stateful
            .peek(System.out::println) // 2º Operacao NAO executa - operacao intermediate ficou no pipeline
            .findFirst(); // 3º Operacao executar - operacao terminal

        System.out.println();

        // Aqui o peek imprime todos os usuários, mesmo se só queremos fazer findAny.
        usuarios.stream() //
            .peek(System.out::println) // 1º Operacao NAO executa - fica no pipeline
            .sorted(Comparator.comparing(Usuario::getNome)) // 2º Operacao executa - operacao stateful
            .findFirst(); // 3º Operacao executar - operacao terminal

        // O q aconteceu?
        // Dizemos que o sorted é um método intermediário stateful. Operações stateful
        // podem precisar processar todo o pipeline de stream, mesmo que sua operação terminal não demande isso.

    }

}
