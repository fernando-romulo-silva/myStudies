package br.com.fernando.cap07_Usando_lacos;

// Crie e use laços do tipo while
public class Test01 {

    //
    public static void test01() {

	int i = 1;

	// Uma linguagem como o Java oferece alguns tipos de laços para o progra-
	// mador escolher. O comando while é um deles.
	while (i < 10) {
	    System.out.println(i);
	    i++;
	}

	while (i < 10)
	    System.out.println(i++);

	// É necessário tomar cuidado para não escrever um while infinito, ou seja,
	// um laço que não terminaria se fosse executado.

	// Quando fica false?
	while (i < 10) {
	    System.out.println(i);
	}

	// Em casos em que é explícito que o loop será infinito, o compilador é esperto e
	// não deixa compilar caso tenha algum código executável após o laço:
	a();

	// Agora, caso a variável não seja final, o compilador não tem como saber
	// se o valor irá mudar ou não, por mais explícito que possa parecer, e o código
	// compila normalmente:
	b();

	// Caso o compilador detecte código dentro de um laço nunca será execu-
	// tado, também teremos um erro de compilação:

	// unreachable statement, compile error.
	// while(false) { /* code */ }

	// unreachable statement, compile error.
	// while(1 > 2) { /* code */ }

	// Lembre-se que o compilador só consegue analisar operações com literais
	// ou com constantes. No caso a seguir, o código compila, mesmo nunca sendo
	// executado:

	int a = 1;
	int b = 2;
	
	while (a > b) { // ok
	    System.out.println("OI");
	}
    }

    static int a() {
	while (true) { // true, true, true ...
	    System.out.println("do something");
	}

	// return 1; // compile error
    }

    static int b() {
	boolean rodando = true; // não final
	while (rodando) { // true? false?
	    System.out.println("do something");
	}
	return 1; // ok
    }

}
