package br.com.nandao.cap09Mapeando_particionando_agrupando_paralelizando.part01;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

// 9.1 Coletores gerando mapas
public class Test {

    static Stream<String> lines(final Path p) {
        try {
            return Files.lines(p);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void main(String[] args) throws Exception {

        final URL resource1 = Test.class.getResource("/");

        // Poderíamos ter um Stream com a quantidade de linhas de cada arquivo. Para
        // isso, em vez de fazer um flatMap para aslinhas, fazemos um map para a quantidade
        // de linhas, usando o count do Stream:

        final LongStream lines1 = //
            Files.list(Paths.get(resource1.toURI())) //
                .filter(p -> p.toString().endsWith(".txt")) //
                .mapToLong(p -> lines(p).count());//

        System.out.println("");
        System.out.println(lines1.count());

        // Se quisermos uma List<Long> com os valores desse LongStream, fazemos
        // um collect como já conhecemos.

        final List<Long> lines2 = //
            Files.list(Paths.get(resource1.toURI()))//
                .filter(p -> p.toString().endsWith(".txt"))//
                .map(p -> lines(p).count())//
                .collect(Collectors.toList());

        System.out.println("");
        System.out.println(lines2);

        // De qualquer maneira, o resultado não parece muito útil: um monte de longs.
        // O que precisamos com mais frequência é saber quantas linhas tem cada arquivo, por
        // exemplo. Podemos fazer um forEach e popular um Map<Path, Long>, no qual
        // a chave é o arquivo e o valor é a quantidade de linhas daquele arquivo:

        final Map<Path, Long> linesPerFile = new HashMap<>(); //

        Files.list(Paths.get(resource1.toURI())) //
            .filter(p -> p.toString().endsWith(".txt")) //
            .forEach(p -> linesPerFile.put(p, lines(p).count()));

        System.out.println("");
        System.out.println(linesPerFile);

        // o forEach utiliza uma variável declarada fora do seu escopo, mudando seu estado,
        // o que chamamos de efeito colateral. Isso diminui a possibilidade de otimizações,
        // em especial para a execução em paralelo.
        // Podemos criar esse mesmo mapa com um outro coletor mais específico para esse
        // tipo de tarefa, o toMap:

        final Map<Path, Long> lines = //
            Files.list(Paths.get(resource1.toURI())) //
                .filter(p -> p.toString().endsWith(".txt")) //
                .collect(Collectors.toMap( //
                                           p -> p, //
                                           p -> lines(p).count() //
            ) //
            ); //

        System.out.println("");
        System.out.println(lines);

        // O toMap recebe duas Functions. A primeira produzirá a chave (no nosso
        // caso o próprio Path) e a segunda produzirá o valor (a quantidade de linhas). Como
        // é comum precisarmos de um lambda que retorna o próprio argumento (o nosso p -> p),
        // podemos utilizar Function.identity() para deixar mais claro.
        // Se quisermos gerar um mapa de cada arquivo para toda a lista de linhas contidas
        // nos arquivos, podemos utilizar um outro coletor e gerar um Map<Path,List<String>>:

        final Map<Path, List<String>> content = //
            Files.list(Paths.get(resource1.toURI())) //
                .filter(p -> p.toString().endsWith(".txt")) //
                .collect(Collectors.toMap( //
                                           Function.identity(), //
                                           p -> lines(p).collect(Collectors.toList())));
        
        
        System.out.println("");
        System.out.println(content);
        
    }

}
