package br.com.nandao.cap09Mapeando_particionando_agrupando_paralelizando.part02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.nandao.Usuario;

// groupingBy e partitioningBy
public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150, true);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120, true);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 190);
    static final Usuario user4 = new Usuario("Sergio Lopes", 120);
    static final Usuario user5 = new Usuario("Adriano Almeida", 100);

    static final List<Usuario> usuarios1 = Arrays.asList(user1, user2, user3, user4, user5);

    public static void main(String[] args) {

        // Queremos um mapa em que a chave seja a pontuação do usuário e o valor seja
        // uma lista de usuários que possuem aquela pontuação. Isto é, um Map<Integer,List<Usuario>>

        final Map<Integer, List<Usuario>> pontuacao1 = new HashMap<>();
        for (final Usuario u : usuarios1) {
            pontuacao1.computeIfAbsent( //
                                        u.getPontos(), //
                                        user -> new ArrayList<>() //
            ).add(u);
        }

        System.out.println();
        System.out.println(pontuacao1);

        // O método computeIfAbsent vai chamar a Function do lambda no caso de
        // não encontrar um valor para a chave u.getPontos() e associar o resultado
        // (a nova ArrayList) a essa mesma chave. Isto é, essa invocação do computeIfAbsent
        // faz o papel do if que fizemos no código anterior.
        //
        // Mas o que realmente queremos é trabalhar com Streams. Poderíamos escrever
        // um Collector ou trabalhar manualmente com o reduce, mas há um
        // Collector que faz exatamente esse trabalho:

        final Map<Integer, List<Usuario>> pontuacao2 = usuarios1 //
            .stream() //
            .collect(Collectors.groupingBy(Usuario::getPontos));

        System.out.println();
        System.out.println(pontuacao2);

        // A saída é a mesma! O segredo é o Collectors.groupingBy, que é uma
        // factory de Collectors que fazem agrupamentos

        final Map<Boolean, List<Usuario>> moderadores1 = usuarios1 //
            .stream()//
            .collect(Collectors.partitioningBy(Usuario::isModerador));

        System.out.println();
        System.out.println(moderadores1);

        // O partitioningBy nada mais é do que uma versão mais eficiente do groupingBy para ser
        // usada ao agrupar booleans.
        //
        //
        // Tanto o partitioningBy quanto o groupingBy possuem uma sobrecarga
        // que permite passar um Collector como argumento. Há um Collector que sabe
        // coletar os objetos ao mesmo tempo que realiza uma transformação de map:
        // Em vez de guardar os objetos dos usuários, poderíamos guardar uma lista com
        // apenas o nome de cada usuário, usando o mapping para coletar esses nomes em
        // uma lista:
        //
        final Map<Boolean, List<String>> nomesPorTipo = usuarios1 //
            .stream() //
            .collect( //
                      Collectors.partitioningBy( //
                                                 Usuario::isModerador, //
                                                 Collectors.mapping(Usuario::getNome, //
                                                                    Collectors.toList())));

        System.out.println();
        System.out.println(nomesPorTipo);

        // Queremos particionar por moderação, mas ter como valor não os usuários,
        // mas sim a soma de seus pontos. Também existe um coletor para realizar essas somatórias,
        // que pode ser usado em conjunto com o partitioningBy e groupingBy:

        final Map<Boolean, Integer> pontuacaoPorTipo = usuarios1 //
            .stream() //
            .collect( //
                      Collectors.partitioningBy( //
                                                 Usuario::isModerador, //
                                                 Collectors.summingInt(Usuario::getPontos)));

        System.out.println();
        System.out.println(pontuacaoPorTipo);

        // Conhecer bem toda a factory Collectors certamente vai ajudar suas manipulações
        // de coleções. Perceba que não usamos mais loops para processar os elementos.
        // Até mesmo para concatenar todos os nomes dos usuários há um coletor:
        final String nomes = usuarios1 //
            .stream() //
            .map(Usuario::getNome) //
            .collect(Collectors.joining(", "));

        System.out.println();
        System.out.println(nomes);

    }

}
