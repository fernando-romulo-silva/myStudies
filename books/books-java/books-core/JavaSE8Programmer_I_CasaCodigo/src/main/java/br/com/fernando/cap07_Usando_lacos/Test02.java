package br.com.fernando.cap07_Usando_lacos;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

// Crie e use laços do tipo for, incluindo o enhanced for
public class Test02 {

    public static void test01() {
	// Observando um pouco os códigos que utilizam while , dá para perceber
	// que eles são formados por quatro partes: inicialização, condição, comandos e atualização.

	int index = 1; // Inicialização
	while (index < 10) { // Condição
	    System.out.println(index); // Comandos
	    index++; // Atualização
	}

	// A inicialização é importante para que o laço execute adequadamente.
	// Mesmo com essa importância, a inicialização fica separada do while .
	// A atualização é fundamental para que não aconteça um “loop infinito”.
	// Porém, a sintaxe do while não a coloca em evidência.
	//
	//
	for (int j = 1; j < 10; j++) {
	    System.out.println(j);
	}

	// O for tem três argumentos separados por ; . O primeiro é a inicialização, o segundo, a condição, e o terceiro, a atualização.
	//
	// A inicialização é executada somente uma vez no começo do for .
	// A condição é verificada no começo de cada rodada (iteração). A atualização é executada
	// no fim de cada iteração.
	// Todos os três argumentos do for são opcionais. Desta forma, você poderia escrever o seguinte código:
	m1();

	// O que acontece com esse laço? Para responder essa pergunta é necessário saber quais são os
	// “valores default” colocados nos argumentos do for ,quando não é colocado nada pelo programador.
	// A inicialização e a atualização ficam realmente vazias. Agora, a condição recebe por padrão o valor
	// true. Então, o código anterior depois de compilado fica assim:
	m2();

	// É permitido declarar diversas variáveis de um mesmo tipo ou inicializar diversas variáveis.
	// Na inicialização, não é permitido declarar variáveis de tipos diferentes.
	// Mas é possível inicializar variáveis de tipos diferentes. Veja os exemplos:

	// Declarando três variáveis do tipo int e inicializando as três.
	// Repare que o "," separa as declarações e inicializações.
	m3();

	// Declarando três variáveis de tipos diferentes
	m4();

	// Na atualização, é possível fazer diversas atribuições separadas por , .
	// a cada volta do laço, incrementamos o i e decrementamos o j
	m5();

	// Como já citamos anteriormente, não é possível inicializar variáveis de tipos diferentes:
	// for (int i=1, long j=0; i< 10; i++){ } // compile error

	//
	// No campo de condição, podemos passar qualquer expressão que resulte
	// em um boolean . São exatamente as mesmas regras do if e while .
	// No campo de atualizacao, no podemos so usar os operadores de incremento, podemos executar
	// trecho de codigos

	for (int i = 0; i < 10; i += 3) { // somatório
	    // code
	}

    }

    public static void m1() {
	for (;;) {
	    // CODE
	}
    }

    public static void m2() {
	for (; true;) { // true, true, true ...
	    // CODE
	}
    }

    public static void m3() {
	for (int i = 1, j = 2, k = 3;;) {
	    // CODIGO
	}
    }

    public static void m4() {

	int a;
	double b;
	boolean c;

	// Inicializando as três variáveis já declaradas
	for (a = 1, b = 2.0, c = true;;) {

	}
    }

    public static void m5() {
	for (int i = 1, j = 2;; i++, j--) {
	    // code
	}
    }

    // ===================================================================================

    // Enhanced for
    public static void test02() {
	int[] numbers = { 1, 2, 3, 4, 5, 6 };

	for (int num : numbers) { // enhanced for
	    System.out.println(num);
	}

	// Nesse caso, declaramos uma variável que irá receber cada um dos mem-
	// bros da coleção ou array que estamos percorrendo. O próprio for irá a cada
	// iteração do laço atribuir o próximo elemento da lista à variável. Seria o equi-
	// valente a fazer o seguinte:

	for (int i = 0; i < numbers.length; i++) {
	    int num = numbers[i]; // declaração da variável e atribuição
	    System.out.println(num);
	}

	// Se fosse uma coleção, o código fica mais simples ainda se comparado com o for original:

	List<String> names = Arrays.asList("Peter", "Paul");

	// percorrendo a lista com o for simples
	for (Iterator<String> iterator = names.iterator(); iterator.hasNext();) {
	    String name = iterator.next();
	    System.out.println(name);
	}

	// enhanced for
	for (String string : names) {

	}

	// Existem, porém, algumas limitações no enhanced for. Não podemos, por
	// exemplo, modificar o conteúdo da coleção que estamos percorrendo usando a variável que declaramos:

	// tentando remover nomes da lista
	for (String name : names) {
	    name = null;
	}

	// Ao executar esse código, você perceberá que a coleção não foi modificada,
	// nenhum elemento mudou de valor para null.
	// Outra limitação é que não há uma maneira natural de saber em qual iteração estamos,
	// já que não existe nenhum contador. Para saber em qual linha estamos,
	// precisaríamos de um contador externo. Também não é possível percorrer duas coleções ao mesmo tempo,
	// já que não há um contador centralizado. Para todos esses casos, é recomendado usar o for simples.
    }

}
