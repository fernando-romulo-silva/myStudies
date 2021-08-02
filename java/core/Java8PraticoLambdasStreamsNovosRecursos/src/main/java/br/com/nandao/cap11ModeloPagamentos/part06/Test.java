package br.com.nandao.cap11ModeloPagamentos.part06;

import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.nandao.cap11ModeloPagamentos.part01.Customer;
import br.com.nandao.cap11ModeloPagamentos.part01.Payment;
import br.com.nandao.cap11ModeloPagamentos.part01.Product;

// Quais são os produtos de cada cliente?
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

	// Em um primeiro momento, podemos ter, para cada Customer,
	// sua List<Payment>, bastando agrupar os payments com
	// groupingBy(Payment::getCustomer):

	final Map<Customer, List<Payment>> customerToPayments = payments //
	    .stream() //
	    .collect(Collectors.groupingBy(Payment::getCustomer));

	// Não estamos interessados nos payments de um Customer, e sim nas listas de
	// Product dentro de cada um desses Payments.
	// Uma implementação inocente vai gerar uma List<List<Product>> dentro
	// do valor do Map:

	final Map<Customer, List<List<Product>>> customerToProductsList = payments.stream() //
	    .collect(Collectors.groupingBy(//
	                                   Payment::getCustomer, //
	                                   Collectors.mapping(Payment::getProducts, Collectors.toList()) //
	)//
	);

	final Set<Entry<Customer, List<List<Product>>>> entrySet = customerToProductsList.entrySet();

	entrySet.stream() //
	    .sorted(Comparator.comparing(e -> e.getKey().getName())) //
	    .forEach(System.out::println);

	// Queremos esse mesmo resultado, porém com as listas achatadas em uma só.

	final Map<Customer, List<Product>> customerToProducts2steps = entrySet.stream() //
	    .collect( //
	              Collectors.toMap(Map.Entry::getKey, //
	                               e -> e.getValue().stream() //
	                                   .flatMap(List::stream) //
	                                   .collect(Collectors.toList()) //
		      ) //
	);//

	customerToProducts2steps.entrySet() //
	    .stream() //
	    .sorted(Comparator.comparing(e -> e.getKey().getName())) //
	    .forEach(System.out::println);

    }

}
