package br.com.nandao.cap11ModeloPagamentos.part08;

import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.nandao.cap11ModeloPagamentos.part01.Customer;
import br.com.nandao.cap11ModeloPagamentos.part01.Payment;
import br.com.nandao.cap11ModeloPagamentos.part01.Product;

// Relatórios com datas
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

	// Podemos agrupar por LocalDate, usando um groupingBy(p ->p.getDate().toLocalDate()), ou em um intervalo ainda maior,
	// como por ano e mês. Para isso usamos o YearMonth:
	final Map<YearMonth, List<Payment>> paymentsPerMonth = payments.stream() //
	    .collect(Collectors.groupingBy(p -> YearMonth.from(p.getDate())));

	paymentsPerMonth.entrySet().stream().forEach(System.out::println);

	// E se quisermos saber, também por mês, quanto foi faturado na loja? Basta agrupar
	// com o mesmo critério e usar a redução que conhecemos: somando todos os
	// preços de todos os produtos de todos pagamentos.

	final Map<YearMonth, BigDecimal> paymentsValuePerMonth = payments.stream() //
	    .collect(Collectors.groupingBy(p -> YearMonth.from(p.getDate()), //
	                                   Collectors.reducing( //
	                                                        BigDecimal.ZERO, //
	                                                        p -> p.getProducts().stream() //
	                                                            .map(Product::getPrice) //
	                                                            .reduce(BigDecimal.ZERO, BigDecimal::add), BigDecimal::add)));

	paymentsValuePerMonth.keySet().stream().forEach(System.out::println);

    }
}
