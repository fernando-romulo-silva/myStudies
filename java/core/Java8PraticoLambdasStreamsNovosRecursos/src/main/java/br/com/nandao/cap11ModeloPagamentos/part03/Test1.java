package br.com.nandao.cap11ModeloPagamentos.part03;

import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import br.com.nandao.cap11ModeloPagamentos.part01.Customer;
import br.com.nandao.cap11ModeloPagamentos.part01.Payment;
import br.com.nandao.cap11ModeloPagamentos.part01.Product;

// Reduzindo BigDecimal em somas
public class Test1 {
    static final Customer paulo = new Customer("Paulo Silveira");
    static final Customer rodrigo = new Customer("Rodrigo Turini");
    static final Customer guilherme = new Customer("Guilherme Silveira");
    static final Customer adriano = new Customer("Adriano Almeida");

    static final Product bach = new Product("Bach Completo", Paths.get("/music/bach.mp3"), new BigDecimal(100));
    static final Product poderosas = new Product("Poderosas Anita", Paths.get("/music/poderosas.mp3"), new BigDecimal(90));
    static final Product bandeira = new Product("Bandeira Brasil", Paths.get("/images/brasil.jpg"), new BigDecimal(50));
    static final Product beauty = new Product("Beleza Americana", Paths.get("beauty.mov"), new BigDecimal(150));
    static final Product vingadores = new Product("Os Vingadores", Paths.get("/movies/vingadores.mov"), new BigDecimal(200));
    static final Product amelie = new Product("Amelie Poulain", Paths.get("/movies/amelie.mov"), new BigDecimal(100));

    static final LocalDateTime today = LocalDateTime.now();
    static final LocalDateTime yesterday = today.minusDays(1);
    static final LocalDateTime lastMonth = today.minusMonths(1);

    static final Payment payment1 = new Payment(asList(bach, poderosas), today, paulo);
    static final Payment payment2 = new Payment(asList(bach, bandeira, amelie), yesterday, rodrigo);
    static final Payment payment3 = new Payment(asList(beauty, vingadores, bach), today, adriano);
    static final Payment payment4 = new Payment(asList(bach, poderosas, amelie), lastMonth, guilherme);
    static final Payment payment5 = new Payment(asList(beauty, amelie), yesterday, paulo);

    static final List<Payment> payments = asList(payment1, payment2, payment3, payment4, payment5);

    public static void main(String[] args) {

        // Se preço fosse um int, poderíamos usar o mapToDouble e invocar o sum do DoubleStream resultante.
        // Não é o caso. Teremos um Stream<BigDecimal> e ele não possui um sum.
        //
        // Nesse caso precisaremos fazer a redução na mão, realizando a soma de
        // BigDecimal. Podemos usar o (total, price) -> total.add(price), mas
        // fica ainda mais fácil usando um method reference:

        payment1.getProducts().stream() //
            .map(Product::getPrice) //
            .reduce(BigDecimal::add) //
            .ifPresent(System.out::println);

        // E se precisarmos somar todos os valores de todos os pagamentos da lista
        // payments? Novamente nos deparamos com diversas opções. Podemos usar o
        // mesmo código anterior, usando o map de payments:

        final Stream<BigDecimal> pricesStream = //
            payments.stream() //
                .map( //
                      p -> p.getProducts().stream() //
                          .map(Product::getPrice) //
                          .reduce(BigDecimal.ZERO, BigDecimal::add) //
            );

        // Repare que o código dentro do primeiro map é o mesmo que o do código que
        // usamos para calcular a soma dos valores do payment1.
        //
        // Precisamos repetir a operação de reduce para somar esses valores intermediários.
        // Isto é, realizamos a soma de preços dos produtos de cada pagamento, agora vamos somar cada um desses subtotais:

        final BigDecimal total = payments.stream() //
            .map( //
                  p -> p.getProducts().stream() //
                      .map(Product::getPrice) //
                      .reduce(BigDecimal.ZERO, BigDecimal::add) //
            ) //
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        //
        //

        final Stream<BigDecimal> priceOfEachProduct = payments.stream() //
            .flatMap(//
                     p -> p.getProducts().stream() //
                         .map(Product::getPrice) //
        );

        // Se está difícil ler este código, leia-o passo a passo. O importante é enxergar essa função:
        final Function<Payment, Stream<BigDecimal>> mapper = p -> p.getProducts().stream().map(Product::getPrice);

        // Essa função mapeia um Payment para o Stream que passeia por todos os seus produtos.
        // E é por esse exato motivo que precisamos invocar depois o flatMap e não o map,
        // caso contrário obteríamos um Stream<Stream<BigDecimal>>.

        // Para somar tudo:
        final BigDecimal totalFlat = //
            payments.stream() //
                .flatMap(p -> p.getProducts().stream().map(Product::getPrice)) //
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
}
