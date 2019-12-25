package br.com.nandao.cap11ModeloPagamentos.part09;

import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import br.com.nandao.cap11ModeloPagamentos.part01.Customer;
import br.com.nandao.cap11ModeloPagamentos.part01.Payment;
import br.com.nandao.cap11ModeloPagamentos.part01.Product;
import br.com.nandao.cap11ModeloPagamentos.part01.Subscription;

// Sistema de assinaturas
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

    static final BigDecimal monthlyFee = new BigDecimal("99.90");

    static final Subscription s1 = new Subscription(monthlyFee, yesterday.minusMonths(5), paulo);

    static final Subscription s2 = new Subscription(monthlyFee, yesterday.minusMonths(8), today.minusMonths(1), rodrigo);

    static final Subscription s3 = new Subscription(monthlyFee, yesterday.minusMonths(5), today.minusMonths(2), adriano);

    static final List<Subscription> subscriptions = Arrays.asList(s1, s2, s3);

    public static void main(String[] args) {

	// Como calcular quantos meses foram pagos através daquela assinatura?
	// Basta usar o que conhecemos da API de java.time. Mas depende do caso. Se a assinatura
	// ainda estiver ativa, calculamos o intervalo de tempo entre begin e a data de
	// hoje:

	final long meses1 = ChronoUnit.MONTHS.between(s1.getBegin(), LocalDateTime.now());

	// E se a assinatura terminou? Em vez de enchermos nosso código com ifs, tiramos proveito do Optional:
	final long meses2 = ChronoUnit.MONTHS.between(s1.getBegin(), s1.getEnd().orElse(LocalDateTime.now()));

	// Para calcular o valor gerado por aquela assinatura, basta multiplicar esse número de meses pelo custo mensal:
	final BigDecimal total = s1.getMonthlyFee() //
	    .multiply(new BigDecimal(ChronoUnit.MONTHS.between( //
	                                                        s1.getBegin(), //
	                                                        s1.getEnd().orElse(LocalDateTime.now()) //
	)));

	// Dada uma lista de subscriptions, fica fácil somar todo o total pago:
	final BigDecimal totalPaid = subscriptions.stream() //
	    .map(Subscription::getTotalPaid) //
	    .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

}
