package br.com.fernando.cap10_Lidando_com_excecoes;

// Descreva o que são exceções e para que são utilizadas em Java
public class Test02 {

    // As exceções à regra, as exceptions, são a alternativa para o controle de fluxo:
    // em vez de usarmos ifs para controlar o fluxo que foge do padrão, é possível
    // usar as exceptions para esse papel. Veremos adiante como tratar erros,
    // como o acesso a posições inválidas, tentar acessar variáveis com valores inválidos etc.

    public static void execute1(Integer[] ages) {
	System.out.println(ages[0]);
    }
    
    // O que acontece se o array enviado para o método é vazio? Mas e se o valor fosse nulo, 
    // teríamos que nos preocupar com esse caso específico para não imprimir nulo:

    public void execute2(Integer[] ages) {
	
	if (ages[0] == null) {
	    return;
	}
	
	System.out.println(ages[0]);
    }
    
    // Pense como seria difícil tratar todas as situações possíveis que fogem do
    // padrão de comportamento que estamos desejando. Nesse caso, o comportamento padrão, 
    // aquilo que acontece 99% das vezes e que esperamos que aconteça é que a posição acessada 
    // dentro do array seja válido.
    //
    // Não queremos ter que verificar toda vez se o valor é válido, e não queremos entupir nosso código
    // com diversos ifs para diversas condições.
    //
    // O importante é lembrar que as exceptions permitem que isolemos o tratamento de um comportamento 
    // por blocos, separando o bloco de lógica de nosso negócio do bloco de tratamentos de erros 
    // (sejam eles Exceptions ou Errors, como veremos adiante). O stack trace de uma Exception também
    // ajuda a encontrar onde exatamente o problema ocorreu e o que estava sendo executado naquela 
    // Thread naquele instante.
    
}
