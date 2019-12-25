package br.com.nandao.cap11ModeloPagamentos.part02;

import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import br.com.nandao.cap11ModeloPagamentos.part01.Customer;
import br.com.nandao.cap11ModeloPagamentos.part01.Payment;
import br.com.nandao.cap11ModeloPagamentos.part01.Product;

// Ordenando nossos pagamentos
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

        // O primeiro desafio é fácil. Ordenar os pagamentos por data e imprimi-los, para
        // que fique clara a nossa base de dados. Para isso, podemos encadear o sorted e o
        // forEach do stream dessa coleção:

        payments.stream() //
            .sorted(Comparator.comparing(Payment::getDate)) //
            .forEach(System.out::println);

    }

}
