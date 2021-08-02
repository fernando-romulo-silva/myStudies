package br.com.fernando.cap07_Usando_lacos;

public class Test03 {

    // Crie e uso laços do tipo do/while
    public static void test01() {

	int i = 1;

	do { // executa ao menos 1 vez

	    System.out.println(i);
	    i++;

	} while (i < 10); // se der true, volta e executa novamente.

	// A condição do do .. while só é verificada no final de cada iteração
	// e não no começo, como no while . Repare que ao final do bloco do ..
	// while existe um ponto e vírgula. Esse é um detalhe que passa desapercebido
	// muitas vezes, mas que resulta em erro de compilação se omitido

	//
	//

	// compila normal
	//
	// Assim como no while , caso tenhamos apenas uma linha, as chaves po-
	// dem ser omitidas. Caso exista mais de uma linha dentro do do .. while
	// e não existam chaves, teremos um erro de compilação:
	do
	    System.out.println(i++);
	while (i < 10);

    }

}
