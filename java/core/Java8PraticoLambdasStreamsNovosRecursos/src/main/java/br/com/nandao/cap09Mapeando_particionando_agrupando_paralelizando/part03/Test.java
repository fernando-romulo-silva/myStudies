package br.com.nandao.cap09Mapeando_particionando_agrupando_paralelizando.part03;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import br.com.nandao.Usuario;

// Executando o pipeline em paralelo
public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150, true);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120, true);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 190);
    static final Usuario user4 = new Usuario("Sergio Lopes", 120);
    static final Usuario user5 = new Usuario("Adriano Almeida", 100);

    static final List<Usuario> usuarios1 = Arrays.asList(user1, user2, user3, user4, user5);

    public static void test1() {
        // As collections oferecem uma implementação de Stream diferente, o stream paralelo.
        // Ao usar um stream paralelo, ele vai decidir quantasthreads deve utilizar, como
        // deve quebrar o processamento dos dados e qual será a forma de unir o resultado final
        // em um só. Tudo isso sem você ter de configurar nada. Basta apenas invocar
        // parallelStream em vez de Stream:

        final List<Usuario> filtradosOrdenados = usuarios1.parallelStream() //
            .filter(u -> u.getPontos() > 100) //
            .sorted(Comparator.comparing(Usuario::getNome)) //
            .collect(Collectors.toList());

        System.out.println();
        System.out.println(filtradosOrdenados);
    }

    public static void test2() {

        // Seu código vai rodar mais rápido? Não sabemos. Se a coleção for pequena, o
        // overhead de utilizar essa abordagem certamente tornará a execução bem mais lenta.
        // É necessário tomar cuidado com o uso dos streams paralelos. Eles são uma forma
        // simples de realizar operações com a API de Fork/Join: o tamanho do input precisa
        // ser grande.

        final long sum = //
            LongStream.range(0, 1_000_000_000) //
                .parallel() //
                .filter(x -> x % 2 == 0) //
                .sum();

        System.out.println(sum);
    }

    public static void main(String[] args) {

    }

}
