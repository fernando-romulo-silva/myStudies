package br.com.fernando.cap05_Usando_operadores_e_construcoes_de_decisao;

// Utilize o if e if/else
public class Test04 {

    public static void test01() {
	// As linguagens de programação devem oferecer aos programadores maneiras
	// para controlar o fluxo de execução dos programas.

	boolean authenticated = true;

	if (authenticated) {
	    System.out.println("Valid");
	} else {
	    System.out.println("Invalid");
	}

	// Atenção dobrada ao código a seguir:
	boolean a = true;

	if (a = false) {
	    System.out.println("false!");
	}

	// Neste caso, o código compila, mas não imprime nada. Após a atribuição,
	// o valor da variável a é false , e o if não é executado.

	//
	// Grande parte das perguntas sobre estruturas de if/else são pegadinhas,
	// usando a indentação como forma de distração:

	if (authenticated)
	    System.out.println("Accepted");
	else
	    System.out.println("Declined");
	System.out.println("Try again");

	// A mensagem "Tente novamente" sempre é impressa, independente do valor da variável autentico .
	// Esse foi um exemplo bem simples, vamos tentar algo mais complicado.
	// Tente determinar o que é impresso:
	int valor = 100;

	if (valor > 200)
	    if (valor < 400)
		if (valor > 300)
		    System.out.println("a");
		else
		    System.out.println("b");
	    else
		System.out.println("c");
    }

    // Unreachable Code e Missing return

    public static int method1() {
	return 5;
	// System.out.println("Will it run?"); // nao compila, codigo inacessivel
    }

    public static int method2(int x) {
	if (x > 200) {
	    return 5;
	}
	
	// return 0; // se comentar aqui, nao compila
	throw new RuntimeException(); // ou aqui
    }

    public static void test02() {
	// Um código Java não compila se o compilador perceber que aquele código
	// não será executado sob hipótese alguma
    }

}
