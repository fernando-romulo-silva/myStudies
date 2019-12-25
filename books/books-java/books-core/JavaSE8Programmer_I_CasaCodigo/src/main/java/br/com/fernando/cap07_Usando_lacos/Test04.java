package br.com.fernando.cap07_Usando_lacos;

// Compare os tipos de laços
public class Test04 {

    // Comparando while e do .. while
    public static void test01() {
	// No caso do while e do while , ambos são muito similares, sendo a
	// principal diferença o fato do do .. while ter a condição testada somente
	// após executar o código de dentro do loop pelo menos uma vez.

	int i = 20;
	// imprime 20, já que só faz o teste após a execução do código
	do {
	    System.out.println(i);
	    i++;
	} while (i < 10);

	int j = 20;
	// não imprime nada, já que testa antes de executar o bloco
	while (j < 10) {
	    System.out.println(i);
	    i++;
	}
    }

    // Comparando for e enhanced for
    public static void test02() {
	// Apesar de ser estruturalmente mais complexo, o for simples é mais
	// poderoso que o enhanced for . Com o enhanced for , não podemos:
	//
	// • Percorrer mais de uma coleção ao mesmo tempo;
	// • Remover os elementos da coleção;
	// • Inicializar um array.
	//
	// Caso desejemos fazer uma iteração de leitura, por todos os elementos
	// da coleção, aí sim o enhanced for é a melhor opção.
    }

    // Comparando while e for
    public static void test03() {

	// Geralmente optamos por usar for quando sabemos a quantidade de vezes
	// que queremos que o laço seja executado. Pode ser percorrer todos os elementos
	// de uma coleção, (onde sabemos a quantidade de vezes que o loop será
	// executado por saber o tamanho da coleção) ou simplesmente executar o laço
	// uma quantidade fixa de vezes.
	// Usamos o while ou do .. while quando não sabemos a quantidade
	// de vezes em que o laço será executado, mas sabemos uma condição que,
	// enquanto for verdadeira, fará com que o laço seja repetido.

	// O exemplo a seguir mostra um código no qual conhecemos a condição
	// de parada, mas não faz sentido nenhum ter uma variável inicializada ou uma
	// condição de incremento, então escolhemos um while :

	// while(account.getAmount() > 0) {
	// // account.withdraw(1000);
	// }

	// Note que, caso queira contar quantas vezes foi sacado, faria sentido usar um for :
	
	// int total;
	// for(total = 0; account.getAmount() > 0; total++) {
	// // account.withdraw(1000);
	// }
	// System.out.println("Withdraw " + total + " times");

    }

}
