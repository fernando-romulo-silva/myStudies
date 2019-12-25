package br.com.nandao.cap12MaisJava8.part01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import br.com.nandao.Usuario;

// Novos detalhes na linguagem
public class Test {

    public static class Repositorio {

	void adiciona(final List<Usuario> usuarios) {

	}

    }

    // Operador diamante melhorado
    public static void test1() {

	final Repositorio repositorio = new Repositorio();

	// Podemos fazer, a partir do agora antigo Java 7:
	final List<Usuario> lista2 = new ArrayList<>();

	// Porém o recurso era bastante limitado: basicamente apenas podia ser usado
	// junto com a declaração da variável referência.

	repositorio.adiciona(new ArrayList<>());

	// Para compilar no Java 7, você precisaria fazer repositorio.adiciona(new ArrayList<Usuario>()),
	// pois o compilador não conseguia inferir o tipo declarado na assinatura do método, que neste caso é Usuario.
	//
	// Se você conhece a API de Collections, sabe que ela possui o método
	// Collections.emptyList(). Podemos utilizá-lo neste exemplo para resolver o
	// problema sem ter que explicitamente inferir o tipo Usuario, visto que o compilador
	// não fará essa inferência quando utilizamos o operador diamante.
	// Nosso código ficará assim:

	repositorio.adiciona(Collections.emptyList());

	// Porém o seguinte erro será exibido ao tentar compilar esse código (java 7):
	// The method adiciona(List<Usuario>) in the type Capitulo12 is not applicable for the arguments (List<Object>)
	//
	// Isso acontece pois o compilador inferiu o tipo genérico como sendo um Object.
	// Para que esse código compile precisaríamos fazer:
	repositorio.adiciona(Collections.<Usuario>emptyList());

	// Já no Java 8, os dois casos funcionam perfeitamente, dadas as melhorias na inferência.
	// Agora eu posso escrever e compilar o seguinte código sem nenhuma preocupação, afinal
	// o compilador vai extrair essa informação genérica pelo parâmetro esperado no método.

	repositorio.adiciona(new ArrayList<>());
	repositorio.adiciona(Collections.emptyList());
    }

    public static interface PrivilegedAction<T> {

	T run();
    }

    // Situações de ambiguidade
    public static void test2() {

	// Como a interface funcional Supplier<T> tem apenas o método get que não
	// recebe nenhum parâmetro e retorna o tipo genérico, a expressão () -> "retorna
	// uma String" pode ter seu tipo inferido sem nenhum problema

	final Supplier<String> supplier1 = () -> "retorna uma String";

	// Para esse caso nem será preciso criar um caso de ambiguidade, pois dentro da
	// própria API do Java temos a interface PrivilegedAction<T> que possui essa
	// estrutura, repare na interface PrivilegedAction

	// Sabendo disso, podemos declarar a mesma expressão () -> "retorna uma String" com esses dois tipos!
	final Supplier<String> supplier2 = () -> "retorna uma String";
	final PrivilegedAction<String> p = () -> "retorna uma String";

	// Mas isso não será um problema para o compilador, claro, afinal essa expressão
	// tem seu tipo bem determinado em sua declaração.
	//
	// Isso nem mesmo será um problema quando passarmos a mesma expressão como
	// parâmetro para um método como esse:

	// metodo(() -> "retorna uma String"); // The method metodo(Supplier<String>) is ambiguous for the type Test
	//
	// Neste caso será necessário recorrer ao casting para ajudar nessa inferência. Algo como:
	metodo((Supplier<String>) () -> "retorna uma String");
    }

    private static void metodo(Supplier<String> supplier) {
	// faz alguma lógica e invoca supplier.get()
    }

    // Tudo bem, esse pode não ser um caso tão comum, mas é interessante saber que
    // esse tipo de ambiguidade pode acontecer. Ao adicionar esse segundo método, o compilador
    // não vai conseguir mais inferir o tipo dessa expressão
    private void metodo(PrivilegedAction<String> action) {
	// faz alguma lógica e invoca action.run()
    }

    // Conversões entre interfaces funcionais
    public static void test3() {
	// Outro detalhe interessante sobre a inferência de tipos é que não existe uma conversão
	// automática entre interfaces funcionais equivalentes.
	// O seguinte código não compila:
	final PrivilegedAction<String> action = () -> "executando uma ação";
	// execute(action);
	// The method execute(Supplier<String>) in the type Test is not applicable 
	// for the arguments (Test.PrivilegedAction<String>)
	//
	// Isso não deve funcionar, afinal o método execute(Supplier<String>)
	// não é aplicável para o argumento PrivilegedAction<String>. Para que
	// essa conversão seja possível podemos utilizar method reference nessa instância de
	// PrivilegedAction, assim estamos explicitamente indicando ao compilador que desejamos essa conversão:
	
	final PrivilegedAction<String> action2 = () -> "executando uma ação";
	execute(action2::run);
    }

    private static void execute(Supplier<String> supplier) {
	System.out.println(supplier.get());
	
    }

    public static void main(String[] args) {
	test1();
    }
}
