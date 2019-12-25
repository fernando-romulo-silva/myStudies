package br.com.nandao.cap11ModeloPagamentos.part04;

import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.nandao.cap11ModeloPagamentos.part01.Customer;
import br.com.nandao.cap11ModeloPagamentos.part01.Payment;
import br.com.nandao.cap11ModeloPagamentos.part01.Product;

// Produtos mais vendidos
public class Test {

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

        // Para tal, criamos um Stream com todos os Product vendidos. Mais uma vez entra o flatMap:
        final Stream<Product> products1 = payments.stream() //
            .map(Payment::getProducts) //
            .flatMap(p -> p.stream());

        // Em vez de p -> p.stream(), há a possibilidade de passar o lambda como method reference:
        // List::stream:
        final Stream<Product> products2 = payments.stream() //
            .map(Payment::getProducts) //
            .flatMap(List::stream);

        // Sempre podemos juntar dois maps (independente de um deles ser flat) em um único map:
        final Stream<Product> products3 = payments.stream() //
            .flatMap(p -> p.getProducts().stream());

        // E também não há diferença de performance significativa. Fica a seu cargo utilizar
        // o que considerar mais legível. Como a API e o Java 8 são muito recentes, boas prá-
        // ticas ainda não surgiram para dizer qual das duas abordagens é mais adequada para
        // facilitar a manutenção. Pessoalmente acreditamos que as duas são suficientemente claras.
        //
        // Precisamos gerar um Map de Product para Long. Esse Long indica quantas
        // vezes o produto foi vendido. Usaremos o groupingBy, agrupando todos esses
        // produtos pelo próprio produto, mapeando-o pela sua contagem:

        final Map<Product, Long> topProducts = payments.stream() //
            .flatMap(p -> p.getProducts().stream()) //
            .collect( //
                      Collectors.groupingBy(Function.identity(), //
                                            Collectors.counting()) //
        ); //

        topProducts.entrySet().stream().forEach(System.out::println);
        //

        topProducts.entrySet().stream() //
            .max(Comparator.comparing(Map.Entry::getValue)) //
            .ifPresent(System.out::println);

    }

}
