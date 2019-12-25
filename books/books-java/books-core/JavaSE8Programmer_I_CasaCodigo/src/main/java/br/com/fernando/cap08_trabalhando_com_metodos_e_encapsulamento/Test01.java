package br.com.fernando.cap08_trabalhando_com_metodos_e_encapsulamento;

// Crie métodos com argumentos e valores de retorno
public class Test01 {

    public static void test01() {
	// Classes, enums e interfaces podem ter métodos definidos em seus corpos.
	//
	// A assinatura de um método é composta pelas seguintes partes:
	//
	// • um modificador de visibilidade (nem que seja implícito, package, private);
	// • um tipo de retorno;
	// • um nome seguindo as regras de identificadores;
	// • um conjunto de parâmetros (pode ser vazio), cada um com seu nome e seu tipo.
	//
	// Outros modificadores da assinatura são opcionais e somente alguns são cobrados na prova:
	//
	// • final em caso de herança, o método não pode ser sobrescrito nas classes filhas;
	//
	// • abstract obriga as classes filhas a implementarem o método. O método abstrato não pode ter corpo definido;
	//
	// • static o método deixa de ser um membro da instância e passa a ser acessado diretamente através da classe;
	//
	// • synchronized não cai nesta prova. lock da instância;
	//
	// • native não cai nesta prova. Permite a implementação do método em código nativo (JNI);
	//
	// • strictfp não cai nesta prova. Ativa o modo de portabilidade matemática para contas de ponto flutuante.
	//
	// • throws <EXCEPTIONS> após a lista de parâmetros, podemos indicar quais exceptions podem ser jogadas pelo método.

	//
	// A ordem dos elementos na assinatura dos métodos é sempre a seguinte, sendo que os modificadores podem aparecer
	// em qualquer ordem dentro da seção,
	//
	// <MODIFICADORES> <TIPO_RETORNO> <NOME> (<PARÂMETROS>) <THROWS_EXCEPTIONS>
    }

    // Parametros
    public static void test02() {

    }

    // Em Java, usamos parâmetros em métodos e construtores. Definimos uma
    // lista de parâmetros sempre declarando seus tipos e nomes e separando por vírgula:
    static class Param1 {

	void test(int a, int b) {
	}
    }

    // Enquanto a declaração das variáveis é feita na declaração do método, a
    // inicialização dos valores é feita por quem chama o método. Note que, em Java,
    // não é possível ter valores default para parâmetros e todos são obrigatórios,
    // não podemos deixar de passar nenhum, como mostra o exemplo a seguir:

    static class Param2 {

	void test() {
	    // print("guilherme"); // compile error
	    // print(33); // compile error
	    print("guilherme", 33); // ok
	}

	void print(String name, int age) {
	    System.out.println(name + " " + age);
	}
    }

    // O único modificador possível de ser marcado em parâmetros é final ,
    // para indicar que aquele parâmetro não pode ter seu valor modificado depois
    // da chamada do método (considerado boa prática):
    static class Param3 {

	void test(final int a) {
	    // a = 10; // compile error
	}
    }

    // Promoção em parâmetros
    public static void test03() {

	// Temos que saber que nossos parâmetros também estão sujeitos à promoção de primitivos e ao polimorfismo.

	// O primeiro método espera um double . Mas se chamarmos passando
	// um int , um float ou qualquer outro tipo compatível mais restrito, este
	// será promovido automaticamente a double e a chamada funciona:
	Param4 p = new Param4();
	p.primitive(10);
	p.primitive(10L);
	p.primitive(10F);
	p.primitive((short) 10);
	p.primitive((byte) 10);
	p.primitive('Z');

	// A mesma coisa ocorre com o método que recebe Object,
	// podemos passar qualquer um que é um Object , ou seja, qualquer objeto:
	p.reference(new Test01());
	p.reference(new Param1());

    }

    static class Param4 {

	void primitive(double d) {
	}

	void reference(Object o) {
	}
    }

    // retornando valores
    public static void test04() {

	// Todo método pode retornar um valor ou ser definido como “retornando”
	// void , quando não devolve nada:
    }

    static final class A {

	int number() {
	    return 5;
	}

	void nothing() {
	    return;
	}

	// No caso de métodos de tipo de retorno void (nada), podemos omitir a última instrução:
	void nothingToDo() {
	    // return; // optional
	}

	// Um método desse tipo também pode ter um retorno antecipado, um early return :
	void nothing(int i) {

	    if (i >= 0)
		return; // early return

	    System.out.println("negative");
	}

	// Não podemos ter nenhum código que seria executado após um retorno,
	// uma vez que o compilador detecta que o código jamais será executado:
	void nothingToDo(int i) {
	    if (i >= 0) {
		return;
		// System.out.println(">= 0"); // compile error
	    }

	    System.out.println("< 0");
	}

	// Todo método que possui um tipo de retorno definido (isto é, diferente de void ),
	// deve retornar algo ou jogar uma Exception em cada um dos caminhos de saída possíveis do método,
	// caso contrário o código não compila.

	String method0(int a) {
	    if (a > 0) {
		return "> 0";
	    } else if (a < 0) {
		return "< 0";
	    }

	    // Podemos jogar uma exception ou colocar um return :
	    throw new IllegalStateException(); // compile error if comment this line
	}

	// Métodos que não retornam nada não podem ter seu resultado atribuído a uma variável:

	void method1() {
	    System.out.println("hi");
	}

	void method2() {
	    // int i = method(); // compile error
	}

	int method3() {
	    System.out.println("hi");
	    return 5;
	}

	// Pelo outro lado, mesmo que um método retorne algo, seu retorno pode ser ignorado:
	void method4() {
	    int i = method3(); // i = 5, ok
	    method3(); // ok
	}
    }
}
