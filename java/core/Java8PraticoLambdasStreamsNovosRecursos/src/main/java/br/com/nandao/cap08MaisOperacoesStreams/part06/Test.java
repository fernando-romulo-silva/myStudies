package br.com.nandao.cap08MaisOperacoesStreams.part06;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import br.com.nandao.Usuario;

// Conhecendo mais métodos do Stream
public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 90);

    static final List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

    // Trabalhando com iterators
    public static void test1() {

        // for (final Usuario u : usuarios.stream()) { Erro!
        // }

        // Mas por que decidiram fazer com que Stream não seja um Iterable? Pois
        // as diversas operações terminais de um Stream o marcam como já utilizado. Sua
        // segunda invocação lança um IllegalStateException. Vale lembrar que se você
        // invocar duas vezes usuarios.stream() não haverá problema, pois abrimos dois
        // streams diferentes a cada invocação!
        //
        // Porém, podemos percorrer os elementos de um Stream através de um
        // Iterator. Para isso, podemos invocar o método iterator:

        final Iterator<Usuario> i = usuarios.stream().iterator();

        // A interface Iterator já existe há bastante tempo no Java e define os métodos
        // hasNext, next e remove. Com o Java8, também podemos percorrer um iterator
        // utilizando o método forEachRemaining que recebe um Consumer como parâmetro:

        usuarios.stream() //
            .iterator() //
            .forEachRemaining(System.out::println);

        // Um motivo para usar um Iterator é quando queremos modificar os objetos
        // de um Stream. Quando utilizarmos streams paralelos, veremos que não devemos
        // mudar o estado dos objetos que estão nele, correndo o risco de terresultados não determinísticos.
        // Outro motivo é a compatibilidade de APIs. Pode ser que você precise
        // invocar um método que recebe Iterator.

    }

    // Testando Predicates
    public static void test2() {
        // Há outras situações em que queremos testar predicados
        // mas não precisamos da lista filtrada. Por exemplo, se quisermos saber se há algum
        // elemento daquela lista de usuários que é moderador:

        final boolean hasModerator = usuarios.stream() //
            .anyMatch(Usuario::isModerador);

        // Aqui o Usuario::isModerador tem o mesmo efeito que u ->
        // u.isModerador(), gerando um predicado que testa se um usuário é moderador
        // e devolve um booleano. O processamento dessa operação vai parar assim que
        // o stream encontrar algum usuário que é moderador.
        // Assim como o anyMatch, podemos descobrir se todos os usuários são moderadores
        // com allMatch ou se nenhum deles é, com o noneMatch.
    }

    // Há muitos outros métodos e detalhes!
    public static void test3() {

        // Você pode utilizar o count para saber quantos elementos há no Stream, skip
        // para pular os n próximos elementos e limit para cortar o número de elementos.
        // Também há formas de você criar um Stream sem a existência de uma coleção.
        //
        // Na própria interface Stream há os métodos estáticos empty e of. O primeiro
        // claramente cria um Stream vazio, e o of depende do que você passar como argumento,
        // como por exemplo Stream.of(user1, user2, user3) retorna um Stream<Usuario>.
        // Você também pode concatená-los com Stream.concat.
        //
        // Fora da própria interface também podemos produzir Streams. Por exemplo,
        // você pode usar a API de regex para devolver um Stream<String> através do
        // Pattern.splitAsStream, ou ainda pegar um Stream das linhas de um arquivo
        // com o Files.lines. Se estiver trabalhando diretamente com um array, pode usar o Arrays.stream.
        
        // Não deixe de conhecer a API e investigar bastante essa importante interface sempre
        // que tiver novas ideias e sentir falta de algum método: ele provavelmente existe.
        //
        // Outro ponto relevante: como alguns Streams podem ser originados de recursos
        // de IO, ele implementa a interface AutoCloseable e possui o close. 
        // Um exemplo é usar os novos métodos da classe java.nio.file.Files, incluída no
        // Java 7 e com novidades no Java 8, para gerar um Stream. 
        // Nesse caso, é fundamental tratar as exceções e o finally, ou então usar o try with resources. 
        // Em outras situações, quando sabemos que estamos lidando com streams gerados por coleções,
        // essa preocupação não é necessária.
    }

    public static void main(String[] args) {
        test1();
    }
}
