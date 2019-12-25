package br.com.nandao.cap08MaisOperacoesStreams.part09;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import br.com.nandao.Grupo;
import br.com.nandao.Usuario;

// FlatMap
public class Test {

    static Stream<String> lines(final Path p) {
        try {
            return Files.lines(p);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void test1() throws Exception {

        final URL resource1 = Test.class.getResource("/");

        // Podemos achatar um Stream de Streams com o flatMap. Basta trocar a invocação,
        // que teremos no final um Stream<String>:
        final Stream<String> strings = Files //
                .list(Paths.get(resource1.toURI())) // 
                .filter(p -> p.toString().endsWith(".txt")) //
                .flatMap(p -> lines(p));

        System.out.println(strings.collect(Collectors.toList()));

        System.out.println();

        // Isso pode ser encadeado em vários níveis. Para cada String podemos invocar
        // String.chars() e obter um IntStream (definiram assim para evitar o boxing
        // para Stream<Character>). Se fizermos map(s -> s.chars()), obteremos
        // um indesejado Stream<IntStream>. Precisamos passar esse lambda para o
        // flatMaptoInt:

        final IntStream chars = Files.list(Paths.get(resource1.toURI())) //
                .filter(p -> p.toString().endsWith(".txt")) //
                .flatMap(p -> lines(p)) //
                .flatMapToInt((final String s) -> s.chars()); //

        System.out.println("Qtos caracters(incluindo espacos): " + chars.count());

    }

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 90);

    public static void test2() {

        final Grupo englishSpeakers = new Grupo();
        englishSpeakers.add(user1);
        englishSpeakers.add(user2);

        final Grupo spanishSpeakers = new Grupo();
        spanishSpeakers.add(user2);
        spanishSpeakers.add(user3);

        // Se temos esses grupos dentro de uma coleção:
        final List<Grupo> groups = Arrays.asList(englishSpeakers, spanishSpeakers);
        //
        // Pode ser que queiramos todos os usuários desses grupos. Se fizermos um simples
        // groups.stream().map(g -> g.getUsuarios().stream()), teremos
        // um Stream<Stream<Usuario>>, que não desejamos. O flatMap vai desembrulhar
        // esses Streams, achatando-os.

        groups.stream() //
                .flatMap(g -> g.getUsuarios().stream()) //
                .distinct()//
                .forEach(System.out::println);

        // Temos como resultado todos os usuários de ambos os grupos, sem repetição.
        // Se tivéssemos coletado o resultado do pipeline em um Set, não precisaríamos do
        // distinct.

    }

    public static void main(final String[] args) throws Exception {
        test2();
    }
}
