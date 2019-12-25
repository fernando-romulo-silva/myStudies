package br.com.fernando.cap06_Declare_instancie_inicialize_e_use_array;

// Declare, instancie, inicialize e use um array multidimensional
public class Test02 {

    public static void test01() {
	// Declaração:

	// Um array de duas dimensões.
	int[][] table0;
	// Um array de três dimensões.
	int[][] cube0[];
	// Um array de quatro dimensões.
	int[][][] hipercube0[];

	// Inicialização:

	// Podemos inicializar as arrays com dimensões diferentes, como no caso a seguir onde inicializamos a primeira dimensão com 10 e a segunda com 15:
	int[][] table1 = new int[10][15];
	// Podemos também inicializar somente a primeira dimensão, deixando as outras para depois:
	int[][][] cube1 = new int[10][][];

	// Podemos inicializar diretamente com valores que conhecemos, e nesse caso colocamos todas as dimensões:
	int[][] test0 = new int[][] { { 1, 2, 3 }, { 3, 2, 1 }, { 1, 1, 1 } };
	// ou
	int[][] test1 = { { 1, 2, 3 }, { 3, 2, 1 }, { 1, 1, 1 } };

	//
	// Acesso:
	// O acesso tradicional é feito através de uma posição que desejamos, como no caso a seguir onde acessamos a primeira “linha”, segunda “coluna":
	System.out.println(test0[0][1]);

	int[][] weird = new int[2][];
	weird[0] = new int[20];
	weird[1] = new int[10];
	
	for (int i = 0; i < weird.length; i++) {
	    System.out.println(weird[i].length); // 20, 10
	}
    }
}
