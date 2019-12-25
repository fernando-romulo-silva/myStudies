package br.com.fernando.cap06_Declare_instancie_inicialize_e_use_array;

// Declare, instancie, inicialize e use um array unidimensional
public class Test01 {

    static class Exam {

	Integer timeLimit;
    }

    static class PracticalExam extends Exam {

    }

    static class Client {

	String name;

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}
    }

    public static void test01() {
	// Arrays de tipos primitivos

	// Declaração de um array para guardar variáveis do tipo int.
	int[] ages1;

	// Declaração de um array para guardar variáveis do tipo long.
	long[] size1;

	// Podemos declarar a array com o [] logo após ao nome da variável:
	// Declaração de um array para guardar variáveis do tipo double.
	double weight1[];

	// Declaração de um array para guardar variáveis do tipo long.
	long[] size2;

	// Inicializacao

	// O new, operador que cria objetos, é utilizado para construir um array.

	// System.out.println(age); // compile error

	// E como instancio um array?

	ages1 = new int[10];
	weight1 = new double[50];

	// E temos alguns casos extremos, como criar uma array de tamanho zero compila e roda:
	// int[] numbers = new int[0];
	//
	// Já criar uma array com tamanho negativo compila mas joga uma NegativeArraySizeException durante sua execução.
	int[] numbers1;
	numbers1 = new int[-1];

	// Durante a declaração de uma referência para um array, temos a oportunidade de
	// criá-lo de uma maneira mais fácil se já sabemos o que queremos colocar dentro.

	int[] numbers2 = new int[] { 1, 2, 5, 7, 5 };

	// Mas temos que tomar um pouco de cuidado com esse modo mais simples
	// de declarar o array. Só podemos fazer como no exemplo anterior quando
	// declaramos e inicializamos o array na mesma linha.

	int[] numbers3 = { 1, 2, 5, 7, 5 }; // ok
	int[] numbers4;
	// numbers4 = {1,2,5,7,5}; // compile error
	//
	// Acesso
	//
	// As posições de um array são indexadas (numeradas) de 0 até a capacidade
	// do array menos um. Para acessar uma das variáveis do array, é necessário
	// informar sua posição.

	// Coloca o valor 10 na primeira variável do array ages.
	int ages2[] = new int[10];
	ages2[0] = 10;

	// Coloca o valor 73.14 na última variável do array weights.
	double weights2[] = new double[50];
	weights2[49] = 73.14;

	// O que acontece se alguém tentar acessar uma posição que não existe?
	weights2[50] = 88.4; // ArrayIndexOutOfBoundsException

	//
	// Percorrendo

	int[] ages3 = { 33, 30, 13 };

	for (int i = 0; i < ages3.length; i++) {
	    ages3[i] = i;
	}

	// ou

	for (int age : ages3) {
	    System.out.println(age);
	}
	//
	// Array de referências

	final Exam[] exams1 = new Exam[10];

	// Lembrando que o new inicia as variáveis implicitamente e que o valor
	// padrão para variáveis não primitivas é null , todas as dez posições do array
	// desse código estão null imediatamente após o new .
	//
	// Erro de execução ao tentar aplicar o operador "."
	// em uma referência com valor null.
	// NullPointerException
	exams1[0].timeLimit = 10;

	Exam[] exams2 = new Exam[10];

	for (int i = 0; i < exams2.length; i++) {
	    exams2[i] = new Exam();
	    exams2[i].timeLimit = 210;
	}

	// ou

	for (Exam exam : exams2) {
	    System.out.println(exam.timeLimit);
	}

	// o polimorfismo funciona normalmente, portanto funciona igualmente para interfaces.
	Exam[] exams3 = new Exam[2];
	exams3[0] = new Exam();
	exams3[1] = new PracticalExam();

	Client guilherme = new Client();
	guilherme.setName("guilherme");
	Client[] clients = new Client[10];
	clients[0] = guilherme;

	System.out.println(guilherme.getName()); // guilherme
	System.out.println(clients[0].getName());

	// Casting de arrays
	// Não há casting de arrays de tipo primitivo, portanto não adianta tentar:

	int[] values1 = new int[10];
	// long[] vals = values1; // compile error

	// Já no caso de referências, por causa do polimorfismo é possível fazer a atribuição sem casting de um array para outro tipo de array:
	String[] values2 = new String[2];
	values2[0] = "Certification";
	values2[1] = "Java";
	Object[] vals = values2;

	for (Object val : vals) {
	    System.out.println(val); // Certification, Java
	}

	//
	//
	// E o casting compila normalmente mas, ao executarmos, um array de
	// Object não é um array de String e levamos uma ClassCastException :
	Object[] values3 = new Object[2];
	values3[0] = "Certification";
	values3[1] = "Java";
	String[] vals2 = (String[]) values3;

	for (Object val : vals2) {
	    System.out.println(val);
	}

	// Isso pois a classe dos dois é distinta e a classe pai de array de string
	// não é um array de objeto, e sim, um Object (lembre-se: todo array herda de Object ):

	Object[] objects = new Object[2];
	String[] strings = new String[2];
	System.out.println(objects.getClass().getName());
	// [ Ljava.lang.Object;
	System.out.println(strings.getClass().getName());
	// [ Ljava.lang.String;
	System.out.println(strings.getClass().getSuperclass());
	// java.lang.Object

    }

}
