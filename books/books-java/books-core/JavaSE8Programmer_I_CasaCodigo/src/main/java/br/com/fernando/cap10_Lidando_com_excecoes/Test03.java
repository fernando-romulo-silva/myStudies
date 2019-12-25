package br.com.fernando.cap10_Lidando_com_excecoes;

// Crie um bloco try-catch e determine como exceções alteram o fluxo normal de um programa
public class Test03 {

    // O programador pode definir um tratamento para qualquer tipo de erro de execução.
    // Antes de definir o tratamento, propriamente, é necessário determinar o trecho
    // de código que pode gerar um erro na execução. Isso tudo é feito com o comando try-catch .

    public static void test01() {
	try {
	    // trecho que pode gerar um erro na execução.
	} catch (Throwable t) { // pegando todos os possíveis erros de execução.
	    // tratamento para o possível erro de execução.
	}

	// A sintaxe do try-catch tem um bloco para o programador definir o trecho de código
	// que pode gerar um erro de execução. Esse bloco é determinado pela palavra try.
	// O programador também pode definir quais tipos de erro ele quer pegar para tratar.
	// Isso é determinado pelo argumento do catch.
	// Por fim, o tratamento é definido pelo bloco que é colocado após o argumento do catch .
	//
	// Durante a execução, se um erro acontecer, a JVM redireciona o fluxo de
	// execução da linha do bloco do try que gerou o erro para o bloco do catch .
	// Importante! As linhas do bloco do try abaixo daquela que gerou o erro não serão executadas.
	//
	// Para a prova, é fundamental saber quando o programador pode ou não pode usar o try-catch .
	// A única restrição de uso do try-catch envolve as checked exceptions.
	// Qual é a regra? O programador só pode usar try-catch em uma checked exception se o código
	// do bloco do try pode realmente lançar a checked exception em questão.

	try {
	    new java.io.FileInputStream("a.txt"); // se comentar, codigo nao compila
	    
	    // Lembre-se sempre do polimorfismo, portanto, pegar IOException é o mesmo 
	    // que pegar todas as filhas de IOException também. (metodo lanca FileNotFoundException)
	} catch (java.io.IOException e) {
	    // tratamento de FileNotFoundException.
	}

	// O código a seguir não tem nenhum problema, pois o programador pode usar o try-catch em
	// qualquer situação para os erros de execução que não são checked exceptions.

	try {
	    System.out.println("Ok");
	} catch (RuntimeException e) { // pegando RuntimeException (unckecked).
	    // tratamento.
	}

	// Quando a exception é pega, o fluxo do programa é sair do bloco try e entrar no bloco catch ,
	// portanto, o código a seguir imprime peguei e continuando normal :
	String name = null;
	try {
	    name.toLowerCase();
	    System.out.println("segunda linha do try");
	} catch (NullPointerException ex) {
	    System.out.println("peguei");
	}
	System.out.println("continuando normal");

	
	// Mas, se a exception que ocorre não é a que foi definida no catch, a chamada do método para e volta, 
	// jogando a exception como se não houvesse um try/catch .
    }
    
    // Bloco finally
    public static void test02() {
	
    }
}
