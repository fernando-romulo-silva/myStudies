package br.com.fernando.cap08_trabalhando_com_metodos_e_encapsulamento;

// Aplique a palavra chave static a métodos e campos
public class Test02 {

    public static class Car {

	public static int totalCars = 15;

	public static int getTotalCars() {
	    return totalCars;
	}

	// O que não podemos fazer é usar um método/atributo de instância de
	// dentro de um método estático:

	private int weight;

	public static int getWeight() {
	    // return weight; // compile error
	    return 0;
	}

	// Repare que a variável estática pode acessar um método estático, e esse método
	// acessar algo ainda não definido e ter um resultado inesperado à primeira vista:

	static int b = getMethod();

	public static int getMethod() {
	    return a;
	}

	static int a = 15;

	// O valor de b será 0, e não 15, uma vez que a variável a ainda não foi inicia-
	// lizada e possui seu valor padrão quando da execução do método getMethod .
	//
	// Outro caso interessante é que uma variável estática pode acessar outra
	// estática. O exemplo a seguir mostra uma situação válida:
	static int first = 10;

	static int second = first + 5; // ok
    }

    public static void test01() {

	// O modificador estático diz que determinado atributo ou método pertence à
	// classe, e não a cada objeto. Com isso, você não precisa de uma instância para
	// acessar o atributo, basta o nome da classe.

	Car.totalCars = 5;

	// Um método estático é um método da classe, podendo ser chamado sem uma instância:

	int total = Car.getTotalCars();
	System.out.println(total); // 5

	// Um detalhe importante é que membros estáticos podem ser acessados
	// através de instâncias da classe (além do acesso direto pelo nome da classe).
	Car c = new Car();
	int i = c.getTotalCars();
    }

    // Já o exemplo a seguir mostra a tentativa de uso de uma variável estática
    // que só será inicializada posteriormente na invocação do inicializa:
    static int another;

    static void inicializa() {
	another = 10;
    }

    static int one = another + 1; // 0 + 1 = 1

    // Caso uma classe possua um método estático, ela não pode possuir outro método
    // não estático com assinatura que a sobrescreveria (mesmo que em classe mãe/filha):

    static class A {

	static void a() { // compile error

	}

	// void a() {} // compile error
    }

    static class B {

	static void a() {

	}
    }

    static class C extends B {

	// void a() { } // compile error
    }

    // Outro ponto importante a tomar nota é que o binding do método é feito
    // em compilação, portanto, o método invocado não é detectado em tempo de
    // execução. Leve em consideração:
    static class A2 {

	static void method() {
	    System.out.println("a");
	}
    }

    static class B2 extends A2 {

	static void method() {
	    System.out.println("b");
	}
    }

    // Caso o tipo referenciado de uma variável seja A em tempo de compilação,
    // o método será o da classe A . Se for referenciado como B , será o método da
    // classe B :

    public static void test02() {
	A2 a = new A2();
	a.method(); // a

	B2 b = new B2();
	b.method(); // b

	A2 a2 = b;
	a2.method(); // a

    }

    // A inicialização de uma variável estática pode invocar também métodos estáticas:

    static int idade = grabAge();

    static int grabAge() {
	return 18;
    }

}
