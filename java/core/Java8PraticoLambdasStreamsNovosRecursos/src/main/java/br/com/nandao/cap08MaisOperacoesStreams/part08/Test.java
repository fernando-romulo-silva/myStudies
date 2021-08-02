package br.com.nandao.cap08MaisOperacoesStreams.part08;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Praticando o que aprendemos com java.nio.file.Files
public class Test {

    public static void test1() throws Exception {
        // A classe java.nio.file.Files entrou no Java 7 para facilitar a manipulação de
        // arquivos e diretórios, trabalhando com a interface Path. É uma das classes que
        // agora possuem métodos para trabalhar com Stream

        final URL resource1 = Test.class.getResource("/br/com/nandao/cap08MaisOperacoesStreams/part08");

        Files.list(Paths.get(resource1.toURI())) //
                .forEach(System.out::println);

        // Quer apenas os arquivos java? Pode usar um filter:
        Files.list(Paths.get(resource1.toURI()))//
                .filter(p -> p.toString().endsWith(".java")) //
                .forEach(System.out::println);

        // E se quisermos todo o conteúdo dos arquivos? Vamos tentar usar o
        // Files.lines para ler todas as linhas de cada arquivo.
        //
        // Files.list(Paths.get("./br/com/nandao/cap08MaisOperacoesStreams/part08")) //
        //         .filter(p -> p.toString().endsWith(".java")) //
        //         .map(p -> Files.lines(p)) //
        //         .forEach(System.out::println);
        //
        // Infelizmente esse código não compila. O problema é que Files.lines lança
        // IOException. Mesmo que o método que invoca o map lance essa exception,
        // não compilará, pois nesse caso é a implementação do lambda que estará lançando
        /// IOException. O map recebe uma Function, que tem o método apply e que
        // não lança exception alguma na assinatura.
        //
    }

    // Uma solução seria escrever uma classe anônima ou um lambda definido com as
    // chaves e com try/catch por dentro. Outra seria fazer um método estático simples,
    // que faz o wrap da chamada para evitar a checked exception:
    static Stream<String> lines(final Path p) {
        try {
            return Files.lines(p);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void test2() throws Exception {
        // Em vez de invocarmos map(p -> Files.lines(p)), invocamos o nosso
        // próprio lines, que não lança checked exception:

        final URL resource1 = Test.class.getResource("/br/com/nandao/cap08MaisOperacoesStreams/part08");

        Files.list(Paths.get(resource1.toURI())) // cria um Stream
                .filter(p -> p.toString().endsWith(".class")) // cria um Stream
                .map(p -> lines(p)) // cria um strem
                .forEach(System.out::println);

        // O problema é que, com esse map, teremos um Stream<Stream<String>>,
        // pois a invocação de lines(p) devolve um Stream<String> para cada Path do
        // nosso Stream<Path> original! 
        // Isso fica mais claro de observar se não usarmos o forEach e atribuirmos o resultado a uma variável:   

        final Stream<Stream<String>> strings = Files.list(Paths.get(resource1.toURI())) //
                .filter(p -> p.toString().endsWith(".java")) //
                .map(p -> lines(p)); //

        System.out.println(strings.collect(Collectors.toList()));

    }

    public static void main(final String[] args) throws Exception {
        test2();
    }

}
