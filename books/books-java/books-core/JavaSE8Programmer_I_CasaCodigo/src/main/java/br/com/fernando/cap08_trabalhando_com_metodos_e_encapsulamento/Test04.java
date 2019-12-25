package br.com.fernando.cap08_trabalhando_com_metodos_e_encapsulamento;

// Diferencie o construtor padrão e construtores definidos pelo usuário
public class Test04 {

    // Quando não escrevemos um construtor na nossa classe, o compilador nos
    // dá um construtor padrão. Esse construtor, chamado de default não recebe
    // argumentos, tem a mesma visibilidade da classe e tem a chamada a super().

    static class A1 {
    }

    // na verdade, acaba sendo:
    static class A2 {

	A2() {
	    super();
	}
    }

    // Caso você adicione um construtor qualquer, o construtor default deixa de existir:
    static class B1 {

	B1(String s) {
	}
    }

    public static void test01() {
	new A1(); // default constructor, ok
	// new B1(); // no default constructor, compile error
	new B1("CDC"); // string constructor
    }

    // Dentro de um construtor você pode acessar e atribuir valores aos atributos,
    // suas variáveis membro:

    static class Test {

	// Os valores inicializados com a declaração das variáveis são inicializados
	// antes do construtor, justamente por isso o valor inicial de i é 0, o valor padrão
	// de uma variável int membro.
	// Vale lembrar que variáveis membro são inicializadas automaticamente
	// para: numéricas 0, boolean false , referências null .

	int i;

	Test() {
	    System.out.println(i); // default, 0
	    i = 15; // i = 15
	    System.out.println(i); // 15
	}

	public static void test01() {
	    new Test();
	}

	// Você pode entrar em loop infinito, cuidado, como no caso a seguir
	// onde o compilador não tem como detectar o loop, resultando em um
	// StackOverflow :

	static class Test2 {

	    Test2() {
		new Test2(); // StackOverflow
	    }

	    public static void main(String[] args) {
		new Test2();
	    }
	}
    }
}
