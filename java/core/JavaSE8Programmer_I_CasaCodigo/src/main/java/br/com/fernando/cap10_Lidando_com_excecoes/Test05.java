package br.com.fernando.cap10_Lidando_com_excecoes;

import java.util.ArrayList;

// Reconheça classes de exceções comuns e suas categorias
public class Test05 {

    // Para a prova, é necessário conhecer algumas exceptions clássicas do Java
    public static void test01() {
	// ArrayIndexOutOfBoundsException
	// Ocorre quando se tenta acessar uma posição que não existe em um array.
	int[] array = new int[10];
	array[10] = 10; // ArrayIndexOutOfBoundsException.

	//
	// IndexOutOfBoundsException ocorre qdo tentamos acessar uma posição não existente em uma lista ,
	// a exception é diferente, no caso IndexOutOfBoundsException:
	ArrayList<String> lista = new ArrayList<String>();
	String valor = lista.get(2); // IndexOutOfBoundsException
	//
	//
	// NullPointerException
	// Toda vez que o operador . é utilizado em uma referência nula
	String s = null;
	s.length(); // NullPointerException

	// ClassCastException
	// Quando é feito um casting em uma referência para um tipo incompatível
	// com o objeto que está na memória em tempo de execução
	Object o = "SCJP"; // String
	Integer i = (Integer) o; // ClassCastException.

	// NumberFormatException
	// Um problema comum que o programador enfrenta no dia a dia é ter que
	// “transformar” texto em números.
	String s2 = "ABCD1";
	int i2 = Integer.parseInt(s); // NumberFormatException

	// IllegalArgumentException
	// Qualquer método deve verificar se os valores passados nos seus parâmetros
	// são válidos. Se um método constata que os parâmetros estão inválidos,
	// ele deve informar quem o invocou que há problemas nos valores passados na invocação
	try {
	    divideAndPrint(5, 0);
	} catch (IllegalArgumentException e) {
	    System.err.println("illegal!");
	}
	//
	// IllegalStateException
	// A biblioteca do Java já tem uma classe pronta para essa situação, a classe é a
	// IllegalStateException. Ela significa que o estado atual do objeto não permite que
	// o método seja executado.
	Person person = new Person();

	// ExceptionInInitializerError
	// No momento em que a máquina virtual é disparada, ela não carrega todo o conteúdo
	// do classpath, em outras palavras, ela não carrega em memória todas as classes
	// referenciadas pela sua aplicação.
	// É totalmente possível que algum erro de execução seja gerado no bloco estático.
	// Se isso acontecer, a JVM vai “embrulhar” esse erro em um ExceptionInInitializerError e dispará-lo.

	// StackOverflowError
	// Todos os métodos invocados pelo programa Java são empilhados na Pilha
	// de Execução. Essa pilha tem um limite, ou seja, ela pode estourar:
	loopMethod();

	// NoClassDefFoundError
	//
	// Na etapa de compilação, todas as classes referenciadas no código-fonte
	// precisam estar no classpath. Na etapa de execução também. O que será que
	// acontece se uma classe está no classpath na compilação mas não está na execução?

	// OutOfMemoryError
	// Durante a execução de nosso código, o Java vai gerenciando e limpando
	// a memória usada por nosso programa automaticamente, usando o garbage
	// collector (GC). O GC vai remover da memória todas as referências de objetos
	// que não são mais utilizados, liberando o espaço para novos objetos. Mas o que
	// acontece quando criamos muito objetos, e não os liberamos?
	ArrayList<String> objetos = new ArrayList<String>();
	String atual = "";
	while (true) {
	    atual += " ficou maior";
	    objetos.add(atual);
	}
    }

    public static void divideAndPrint(int i, int j) {
	if (j == 0) { // division by 0 ???
	    throw new IllegalArgumentException();
	}
	System.out.println(i / j);
    }

    static class Person {

	boolean sleeping = false;

	void sleep() {
	    this.sleeping = true;
	    System.out.println("sleeping...");
	}

	void wakeup() {
	    this.sleeping = false;
	    System.out.println("waking up...");
	}

	void walk() {
	    if (this.sleeping) {
		throw new IllegalStateException("Sleeping!!");
	    }
	    System.out.println("walking...");
	}
    }

    static class A {

	static {
	    // trecho a ser executado no carregamento da classe.
	}
    }

    static void loopMethod() {
	loopMethod();
    }
}
