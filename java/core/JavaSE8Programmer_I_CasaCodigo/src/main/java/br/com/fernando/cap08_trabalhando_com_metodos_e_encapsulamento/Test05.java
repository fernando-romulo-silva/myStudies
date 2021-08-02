package br.com.fernando.cap08_trabalhando_com_metodos_e_encapsulamento;

// Crie e sobrecarregue construtores
public class Test05 {

    // Quando existem dois construtores na mesma classe, um construtor pode
    // invocar o outro através da palavra chave this .
    static class Test1 {

	public Test1() {
	    System.out.println("simple");
	}

	// A instrução this do construtor, caso presente, deve ser sempre a primeira dentro do construtor:
	public Test1(int i) {
	    // System.out.println("test"); // compile error
	    this(); // simple
	    // Justo por isso não é possível ter duas chamadas a this :
	    // this();
	}
    }

    // Note que loops não compilam:
    class Test2 {

	public Test2(String s) {
	    // this(s, s); // compile error, loop
	}

	public Test2(String s, String s2) {
	    // this(s); // compile error, loop
	}
    }

    // Quando um método utiliza varargs , se ele possui uma variação do mé-
    // todo sem nenhum argumento e invocarmos sem argumento, ele chamará o
    // método sem argumentos (para manter compatibilidade com versões anterio-
    // res do Java). Por esse motivo ao invocar o método method do código a seguir
    // será impresso 0 args .

    void desativa(String... clients) {
	System.out.println("varargs");
    }

    void desativa() {
	System.out.println("0 args");
    }

    void method() {
	desativa(); // 0 args
    }

    // A instrução this pode envolver instruções:
    // A instrução não pode ser um método da própria classe, pois o objeto não  foi construído ainda:
    static class Test {

	Test() {
	    this(value());
	}

	private static String value() {
	    return "value...";
	}

	Test(String s) {
	    System.out.println(s);
	}

	public static void main(String[] args) {
	    new Test();
	}
    }

}
