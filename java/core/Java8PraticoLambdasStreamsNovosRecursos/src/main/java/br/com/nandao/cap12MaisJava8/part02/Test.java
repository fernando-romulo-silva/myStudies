package br.com.nandao.cap12MaisJava8.part02;

import java.security.PrivilegedAction;
import java.util.concurrent.Callable;

// Qual é o tipo de uma expressão Lambda?
public class Test {

    public static void test1() {
	// Toda expressão lambda tem e precisa ter um tipo. Como vimos rapidamente, o
	// seguinte trecho de código não funciona:
	//
	// Object o = () -> {
	// 	System.out.println("eu sou um runnable!");
	// };
	// new Thread(o).start();
	//
	// Pois Object não é uma interface funcional. Sempre precisamos ter um tipo
	// que seja uma interface funcional, envolvido na atribuição. Por exemplo, quando
	// definimos esse Runnable:

	final Runnable r = () -> {
	    System.out.println("eu sou um runnable!");
	};
	new Thread(r).start();
	//
	//
	// Conhecer o contexto foi um passo muito importante para o compilador poder
	// inferir o tipo de uma expressão lambda, pois como já vimos, uma mesma expressão
	// pode ter tipos diferentes:
	final Callable<String> c = () -> "retorna uma String";
	final PrivilegedAction<String> p = () -> "retorna uma String";
    }
    
    // O mesmo vale para method references
    public static void test2() throws Exception {
	final Callable<String> callable = () -> "executando uma ação";
	// Essa situação também acontece com o recurso de method reference. A mesma
	// referência pode representar tipos diferentes, contanto que sejam equivalentes:
	final Callable<String> c = callable::call;
    }

    public static void main(String[] args) {

    }
}
