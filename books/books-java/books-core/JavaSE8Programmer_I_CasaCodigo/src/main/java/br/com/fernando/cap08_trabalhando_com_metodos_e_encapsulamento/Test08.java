package br.com.fernando.cap08_trabalhando_com_metodos_e_encapsulamento;

// Determine o efeito que ocorre com referências a objetos e a tipos primitivos
// quando são passados a outros métodos e seus valores mudam
public class Test08 {

    // O requisito para entender passagem de parâmetro no Java é saber como
    // funciona a pilha de execução e o heap de objetos.
    // A pilha de execução é o “lugar” onde são empilhados os métodos invocados na
    // mesma ordem em que foram chamados.
    // O heap é o “lugar” onde são guardados os objetos criados durante a execução.

    public static void main(String[] args) {
	int i = 2;
	test(i);
    }

    private static void test(int i) {
	for (int j = 0; j < i; j++) {
	    new String("j = " + j);
	}
    }

    // Pilha de Execucao
    //
    //
    // test i == 2
    // j = 0..1
    // main -> main i = 2

    // Passagem de parâmetros primitivos
    //
    // Ao executar a classe Teste , será impresso o valor 2 . É necessário perceber
    // que as duas variáveis com o nome i estão em métodos diferentes.
    // Há um i no main() e outro i no teste() . Alterações em uma das variáveis
    // não afetam o valor da outra.
    //
    // Passagem de parâmetros de referência
    //
    static class Test {

	public static void main(String[] args) {
	    Exam exam = new Exam();
	    exam.timeLimit = 100;
	    teste(exam);
	    System.out.println(exam.timeLimit); // 210
	}

	static void teste(Exam exam) {
	    exam.timeLimit = 210;
	}
    }

    static class Exam {

	double timeLimit;
    }
    
    // sse exemplo é bem interessante e causa muita confusão. O que será im presso na saída, 
    // ao executar a classe Test , é o valor 210 . Os dois métodos   têm variáveis com o mesmo nome ( exam ). 
    // Essas variáveis são realmente independentes, ou seja, mudar o valor de uma não afeta o valor da outra.
    //
    // Por outro lado, como são variáveis não primitivas, elas guardam referências e, neste caso, são 
    // referências que apontam para o mesmo objeto. Modificações nesse objeto podem ser executadas através 
    // de ambas as referências. 
    // Note que só existe um único objeto que foi instanciado, as duas referências possuem um valor que 
    // referencia esse mesmo objeto.

}
