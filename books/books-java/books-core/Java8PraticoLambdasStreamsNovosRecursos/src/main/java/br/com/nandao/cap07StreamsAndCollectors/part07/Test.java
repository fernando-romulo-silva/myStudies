package br.com.nandao.cap07StreamsAndCollectors.part07;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import br.com.nandao.Usuario;

// IntStream e a família de Streams
public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 90);

    static final List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

    public static void main(String[] args) {

        // Repare que, quando escrevemos o seguinte código, temos como retorno um Stream<Integer>:
        final Stream<Integer> stream1 = usuarios //
            .stream() //
            .map(Usuario::getPontos);

        // Podemos usar o IntStream aqui para evitar o autoboxing! Basta utilizarmos o método mapToInt:
        final IntStream stream2 = usuarios //
            .stream() //
            .mapToInt(Usuario::getPontos);

        // O mapToInt recebe uma função mais específica. Enquanto o map recebe uma Function,
        // o mapToInt recebe ToIntFunction, interface que o método apply sempre retorna int
        // e se chama applyAsInt.
        // No IntStream, existem métodos que simplificam bastante nosso trabalho quando estamos
        // trabalhando com inteiros, como max, sorted e average.
        // Observe como ficou mais simples obter a média de pontos dos usuários:

        final double pontuacaoMediaNova = usuarios.stream() //
            .mapToInt(Usuario::getPontos) //
            .average() //
            .getAsDouble();

        // versao velha
        double soma1 = 0;

        for (final Usuario u : usuarios) {
            soma1 += u.getPontos();
        }

        final double pontuacaoMediaVelha = soma1 / usuarios.size();

        // O que acontece se o número de usuários for zero? Teremos como resultado o
        // positivo infinito! Era isso que gostaríamos? Caso não, vamos adicionar um if,
        // pararetornar zero:

        // Versao Antiga
        double soma2 = 0;

        for (final Usuario u : usuarios) {
            soma2 += u.getPontos();
        }

        double pontuacaoMediaVelha2;

        if (usuarios.isEmpty()) {
            pontuacaoMediaVelha2 = 0;
        } else {
            pontuacaoMediaVelha2 = soma2 / usuarios.size();
        }

        // O Java 8 introduz a classe java.util.Optional
        // Há também algumas versões primitivas, como OptionalDouble e OptionalInt que ajudam nesses casos.
        // O average, que só existe em streams primitivos, devolve um OptionalDouble.Repare:

        final OptionalDouble media = usuarios.stream() //
            .mapToInt(Usuario::getPontos) //
            .average();

        final double pontuacaoMedia1 = media.orElse(0.0);

        // Pronto. Se a lista for vazia, o valor de pontuacaoMedia será 0.0.
        // Sem o uso do orElse, ao invocar o get você receberia um NoSuchElementException,
        // indicando que o Optional não possui valor definido.
        // Podemos escrever em uma única linha:

        final double pontuacaoMedia2 = usuarios.stream() //
            .mapToInt(Usuario::getPontos) //
            .average() //
            .orElse(0.0);

        // Ou ir ainda além, como lançar uma exception utilizando o método
        // orElseThrow. Ele recebe um Supplier de exceptions, aquela interface funcional
        // que parece bastante uma factory. Podemos então fazer:
        final double pontuacaoMedia3 = usuarios.stream() //
            .mapToInt(Usuario::getPontos) //
            .average() //
            .orElseThrow(IllegalStateException::new);

        // Podemos verificar o contrário: se realmente existe um valor dentro do opcional.
        // E, no caso de existir, passamos um Consumer como argumento:
        usuarios //
            .stream() //
            .mapToInt(Usuario::getPontos) //
            .average() //
            .ifPresent(valor -> System.out.println(valor));

        // O caso mais frequente é quando um valor pode ser null. Com o Optional,
        // você será sempre obrigado a trabalhar com a condição de aquele elemento não existir,
        // evitando um NullPointerException descuidado.
        // Por exemplo: queremos o usuário com maior quantidade de pontos. Podemos
        // usar o método max para tal, que recebe um Comparator:

        final Optional<Usuario> max = usuarios //
            .stream() //
            .max(Comparator.comparingInt(Usuario::getPontos));

        // Se a lista for vazia, não haverá usuário para ser retornado. Por isso, o resultado
        // é um Optional. Você deve verificar se há ou não um usuário presente nesse resultado,
        // usando os métodos que vimos. Pode ser um simples get, podendo receber um null,
        // ou algo mais rebuscado com o orElse ou ifPresent.
        // Você pode até mesmo continuar trabalhando com Optional de maneira lazy.
        // Se você quiser o nome do usuário com maior número de pontos, podemapear(transformar)
        // esse resultado:

        final Optional<String> maxNome = usuarios //
            .stream() //
            .max(Comparator.comparingInt(Usuario::getPontos)) //
            .map(u -> u.getNome());

    }
}
