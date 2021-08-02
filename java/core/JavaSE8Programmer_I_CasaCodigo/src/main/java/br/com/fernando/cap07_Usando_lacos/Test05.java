package br.com.fernando.cap07_Usando_lacos;

// Use break e continue
public class Test05 {

    @SuppressWarnings("unused")
    public static void test01() {

	// O break serve para parar o laço totalmente.
	// Já o continue interrompe apenas a iteração atual.

	int i = 1;

	while (i < 10) {
	    i++;
	    if (i == 5)
		break; // sai do while com i valendo 5
	    System.out.println(i);
	}

	System.out.println("End");

	// Vamos comparar com o continue :

	i = 1;

	while (i < 10) {
	    i++;

	    if (i == 5)
		continue; // vai para a condição com o i valendo 5

	    System.out.println(i);
	}

	// break quebra o laço atual, enquanto o continue vai para a próxima iteração do laço.
	//
	//
	// Tome cuidado, pois um laço que tenha um while infinito do tipo true
	// e que contenha um break é compilável, já que o compilador não sabe se o
	// código poderá parar, possivelmente sim:

	while (true) {
	    if (1 == 2)
		break; // lies, lies...

	    System.out.println("infinite loop");
	}

	// Os controladores de laços, break e continue podem ser aplicados no for.
	// O break se comporta da mesma maneira que no while e no do ..while ,
	// parar o laço por completo. Já o continue faz com que a iteração
	// atual seja abortada, executando em seguida a parte de atualização do for , e
	// em seguida a de condição. Vamos ver o exemplo a seguir:

	for (i = 1; i < 10; i++) {
	    if (i == 8) {
		break; // sai do for sem executar mais nada do laço.

	    }
	    if (i == 5) {
		// pula para a atualização sem executar o resto do corpo.
		continue;
	    }
	    System.out.println(i);
	}
    }

    // Rotulos em lacos (labeled loops)
    public static void test02() {

	// Às vezes, encontramos a necessidade de “encaixar” um laço dentro de outro.
	// Por exemplo, um for dentro de um while ou de outro for . Nesses casos,
	// pode ser preciso manipular melhor a execução dos laços encaixados
	// com os controladores de laços, break e continue .

	for (int i = 1; i < 10; i++) { // laço externo

	    for (int j = 1; j < 10; j++) { // laço interno
		if (i * j == 25) {
		    break; // qual for será quebrado?
		}
	    }

	}

	// Podemos adicionar labels (rótulos) a algumas estruturas de código, e usá-
	// los posteriormente para referenciarmos essas estruturas. Para declarar um
	// label usamos um nome qualquer (mesma regra de nomes de variáveis etc.)
	// seguido de dois pontos ( : ). Por exemplo, podemos dar um label para um
	// for como o que segue:

	external : for (int i = 1; i < 10; i++) {

	    internal : for (int j = 1; j < 10; j++) {

		if (i * j == 25) {
		    break external; // quebrando o for externo
		}

		if (i * j == 16) {
		    continue internal; // pulando um iteração do for interno
		}
	    }
	}

    }

    // Um rótulo ou label pode estar presente antes de um statement qualquer,
    // mas só podemos utilizar um statement de break ou continue caso o rótulo
    // esteja referenciando um for , while ou switch :
    void labelAnywhere() {
	myLabel : System.out.println("hi");
    }

    void continueSomeRandomLabelDoesNotCompile() {
	myLabel : System.out.println("hi");
	if (1 < 10) {
	    System.out.println();
	    // continue myLabel; // erro de compilação
	}
    }

    // Cuidado, mesmo dentro de um for ou similar, o continue e o break
    // só funcionarão se forem relativos a um label dentro do qual estão, e do tipo
    // for , do...while , switch ou while . Vale lembrar que switch só aceita break .

    void labelAnywhereIsOkBreakAnywhereIsNotOk() {
	myLabel : System.out.println("hi");
	for (int i = 0; i < 10; i++) {
	    // break myLabel; // compile error
	}
    }

    void anotherLoopLabel() {
	myLabel : for (int i = 0; i < 10; i++) {
	    System.out.println("hi");
	}

	for (int i = 0; i < 10; i++) {
	    // break myLabel; // compile error
	}
    }

    // Rótulos podem ser repetidos desde que não exista conflito de escopo:
    void repeatedLabel() {
	myLabel : for (int i = 0; i < 10; i++) {
	    break myLabel;
	}
	myLabel : for (int i = 0; i < 10; i++) {
	    break myLabel;
	}
    }

    void sameNameNestedLabelsDoesNotCompile() {
	myLabel : for (int i = 0; i < 10; i++) {
	    // compile error
	    // myLabel : for (int j = 0; j < 10; j++) {
	    // // break myLabel;
	    // }
	}
    }

    // Um mesmo statement pode ter dois labels:
    void twoLabelsInTheSameStatement() {
	first : second : for (int i = 0; i < 10; i++) {
	    System.out.println(i);
	}
    }

    void testLoops() {

	for (int i = 0; i < 4; i++) {
	    System.out.println("Before switch");
	    
	    mario : guilherme : switch (i) {
		case 0:
		case 1:
		    System.out.println("Case " + i);
		    
		    for (int j = 0; j < 3; j++) {
			System.out.println(j);
			if (j == 1)
			    break mario;
		    }
		case 2:
		    System.out.println("At i = " + i);
		    continue;
		case 3:
		    System.out.println("At 3");
		    break;
		default:
		    System.out.println("Weird...");
		    break;
	    }
	    
	    System.out.println("After switch");
	}
    }

}
