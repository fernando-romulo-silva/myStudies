package br.com.fernando.cap05_Usando_operadores_e_construcoes_de_decisao;

// Utilize o switch
public class Test05 {

    public static void test01() {
	// Suponha que um programa tenha que reagir diferentemente para três casos possíveis.

	int option = 1;

	// O valor de cada case deve ser compatível com o tipo do argumento do
	// switch , caso contrário será gerado um erro de compilação na linha do case inválido.
	switch (option) {
	    case 1:
		System.out.println("number 1");
	    case 2:
		System.out.println("number 2");
	    case 3:
		System.out.println("number 3");
	}

	// Argumento String, e cases String
	String s = "Oi";
	switch (s) {
	    case "Oi":
		System.out.println("Olá");
	    case "Hi":
		System.out.println("Hello");
	}

	// Argumento Byte, e cases byte
	Byte b = 10;
	switch (b) {
	    case 10:
		System.out.println("TEN");
	}

	// argumento do switch int, e cases string, não compila
	int mix = 20;

	switch (mix) {
	    // case "10": System.out.println(10); // compile error
	    case 20:
		System.out.println(20);
	}

	// Você pode usar qualquer tipo primitivo menor que um int como argumento
	// do switch , desde que os tipos dos cases sejam compatíveis:

	byte value1 = 20;
	switch (value1) {
	    // case 32768: System.out.println(10); // compile error
	}

	// Em cada case , só podemos usar como valor um literal, uma variável
	// final atribuída com valor literal, ou expressões envolvendo os dois.
	// Nem mesmo null é permitido:

	int value = 20;
	final int FIVE = 5;
	int thirty = 30;

	// Para ser considerada uma constante em um case , a variável, além
	// de ser final, também deve ter sido inicializada durante a sua declaração.

	final int TWENTY; // final, mas não inicializada

	TWENTY = 20; // inicializada

	switch (value) {
	    case FIVE: // constante
		System.out.println(5);
	    case 10: // literal
		System.out.println(10);
	    case FIVE * 4: // operação com constante e literal
		System.out.println(20);

		// case TWENTY: System.out.println("20!"); // compile error pq nao eh constante inicializada na criacao

		// case thirty: System.out.println(30); // compile error, variável

		// case thirty + FIVE: System.out.println(35); // compile error, operação envolvendo variável

		// case null: System.out.println("null"); // compile error, explicit null
	}

	// O switch também aceita a definição de um caso padrão, usando a palavra default .
	int optionNew = 4;

	switch (optionNew) {
	    case 1:
		System.out.println("1");
		// pode aparecer no meio
	    default:
		System.out.println("DEFAULT");
	    case 2:
		System.out.println("2");
	    case 3:
		System.out.println("3");
	}

	// Um comportamento contraintuitivo do switch é que, quando executado,
	// se algum case “bater”, tudo que vem abaixo é executado também, todos
	// os case s e o default , se ele estiver abaixo. Esse comportamento também
	// vale se cair no default . Por exemplo, o código anterior imprime:

	int v = 1;
	switch (v) {
	    case 1:
	    case 2:
	    case 3:
		System.out.println("1,2,3 => Hi!");
	}

	// Para mudar esse comportamento e não executar o que vem abaixo de um
	// case que bater ou do default , é necessário usar o comando break em
	// cada case .

	int v2 = 4;
	switch (v2) {
	    case 1:
		System.out.println("1");
		break;
	    case 2:
		System.out.println("2");
		break;
	    default:
		System.out.println("DEFAULT");
		break;
	    case 3:
		System.out.println("3");
		break;
	}
    }

}
