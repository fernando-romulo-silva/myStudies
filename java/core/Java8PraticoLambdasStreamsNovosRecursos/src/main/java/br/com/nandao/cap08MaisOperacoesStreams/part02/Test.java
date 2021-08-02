package br.com.nandao.cap08MaisOperacoesStreams.part02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.nandao.Usuario;

// Muitas operacoes em Stream sao Lazy!
public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 90);

    static final List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

    public static void main(String[] args) {
        
        // Quando manipulamos um Stream, normalmente encadeamos diversas operações
        // computacionais. Esse conjunto de operações realizado em um Stream é conhecido
        // como pipeline. 

        usuarios.stream() // inicio do stream
            .filter(u -> u.getPontos() > 100) // operacao intermediaria
            .sorted(Comparator.comparing(Usuario::getNome)) // operacao intermediaria
            .collect(Collectors.toList()); // operacao terminal 

        // Os métodos filter e sorted devolvem um Stream. No momento dessas
        // invocações, esses métodos nem filtram, nem ordenam: eles apenas devolvem novos
        // streams em que essa informação é marcada. Esses métodos são chamados de operações
        // intermediárias. Os novos streams retornados sabem que devem ser filtrados
        // e ordenados (ou o equivalente) no momento em que uma operação terminal for invocada.
        //
        // O collect é um exemplo de operação terminal e só nesse momento o stream
        // realmente vai começar a executar o pipeline de operações pedido.
        
    }
}
