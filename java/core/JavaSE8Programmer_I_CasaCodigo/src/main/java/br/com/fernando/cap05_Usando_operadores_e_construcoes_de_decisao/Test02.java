package br.com.fernando.cap05_Usando_operadores_e_construcoes_de_decisao;

// Use parênteses para sobrescrever a precedência de operadores
public class Test02 {
    
    public static void test01() {
	int a = 15 * 4 + 1; // 15 * 4 = 60, 60 + 1 = 61
	int b = 15 * (4 + 1); // 4 + 1 = 5, 15 * 5 = 75
	
	// Devemos tomar muito cuidado na concatenação de String e precedência:
	
	System.out.println(15 + 0 + " != 150"); // 15 != 150
	System.out.println(15 + (0 + " == 150")); // 150 == 150
	System.out.println(("guilherme" + " silveira").length()); // 18
	
	System.out.println("guilherme" + " silveira".length()); // guilherme9
    }

}
