package br.com.fernando.cap05_Usando_operadores_e_construcoes_de_decisao;

import java.util.ArrayList;
import java.util.List;

// Use operadores Java
public class Test01 {

    // operador de atribuição
    public static void test01() {
	// long age = ; // não compila, onde está o valor?
	// long = 15; // não compila, onde está o nome da variável?
	long age = 15; // compila
	age = 15;
	// compila desde que a variável tenha sido declarada correta anteriormente

	// int é um número médio, será que ele “cabe” em uma
	// variável do tipo long (número grande)? Sim, logo o código compila. Mais exemplos:
	int a = 10;
	// tipos iguais
	long b = 20;
	// int cabe em um long
	float c = 10f; // tipos iguais
	double d = 20.0f; // float cabe em um double
	double e = 30.0; // tipos iguais
	// float f = 40.0; // erro, double não cabe em um float.
	// int g = 10l; // erro, long não cabe em int
	float h = 10l; // inteiros cabem em decimais
	double i = 20; // inteiros cabem em decimais
	// long j = 20f; // decimais não cabem em inteiros

	byte b1 = 10;
	// byte b2 = 200; // não compila, estoura byte
	char c1 = 10;
	// char c2 = -3; // não compila, char não pode ser negativo
    }

    // Atribuição e referência
    public static void test02() {
	// Quando trabalhamos com referências, temos que lembrar do polimorfismo:
	List<String> names = new ArrayList<String>();
	// ou
	List<String> lastnames = new ArrayList<>();

	// Lembre que as atribuições em Java são por cópia de valor, sempre. No
	// tipo primitivo, copiamos o valor, em referências a objetos, copiamos o valor
	// da referência (não duplicamos o objeto):

	// copia o valor da referência, o objeto é o mesmo
	List<String> names2 = names;
	names2.add("Guilherme");

	// true
	System.out.println(names.size() == names2.size());

	int age = 15;
	int age2 = age; // copia o valor
	age2 = 20;
	System.out.println(age == age2); // false
    }

    // Operadores aritméticos
    public static void test03() {
	// (soma, subtração, multiplicação e divisão).
	int two = 2;
	int ten = 10;
	// Fazendo uma soma com o operador "+".
	int twelve = two + ten;
	// Fazendo uma subtração com o operador "-".
	int eight = ten - two;
	// Fazendo uma multiplicação com o operador "*".
	int twenty = two * ten;
	// Fazendo uma divisão com o operador "/".
	int five = ten / two;

	// Além desses, há um operador para a operação aritmética “resto da divi-
	// são”. Esse operador só faz sentido para variáveis primitivas numéricas inteiras.

	int three = 3;
	int dez = 10;
	// Calculando o resto da divisão de 10 por 3.
	int one = ten % three;

	//
	// O resultado de uma operação aritmética é um valor. A dúvida que surge
	// é qual será o tipo dele. Para descobrir o tipo do valor resultante de uma ope-
	// ração aritmética, devem-se considerar os tipos das variáveis envolvidas.
	// A regra é a seguinte: o resultado é do tipo mais abrangente entre os das
	// variáveis envolvidas ou, no mínimo, o int .

	int age = 15;
	long years = 5;

	// ok, o maior tipo era long
	long afterThoseYears = age + years;
	// não compila, o maior tipo era long, devolve long
	// int afterThoseYears2 = age + years;
	// Mas devemos lembrar da exceção: o mínimo é um int :
	byte b = 1;
	short s = 2;
	// devolve no mínimo int, compila
	int i = b + s;
	// não compila, ele devolve no mínimo int
	// byte b2 = i + s;
	// compila forçando o casting, correndo risco de perder
	// informação
	byte b2 = (byte) (i + s);
    }

    // Divisão por zero
    public static void test04() {
	int i = 200;
	int v = 0;
	// Dividir (ou usar mod ) um inteiro por zero lança uma ArithmeticException
	// compila, mas exception
	System.out.println(i / v);

	// Se o operando for um float ou double, isso gera infinito positivo ou negativo (depende do sinal do operador)
	// compila e roda, infinito positivo
	System.out.println(i / 0.0);
	//
	// Ainda existe o valor NaN (Not a Number), gerado pela radiciação de um número negativo e por algumas contas com números infinitos.

	double positiveInfinity = 100 / 0.0;
	double negativeInfinity = -100 / 0.0;
	// número não definido (NaN)
	System.out.println(positiveInfinity + negativeInfinity);
    }

    // Operadores de comparação
    public static void test05() {

	// • == igual
	// • != diferente

	// Além disso, os valores numéricos ainda podem ser comparados em relação à ordem.
	// • > maior
	// • < menor
	// • >= maior ou igual
	// • <= menor ou igual
	//
	// Uma comparação pode devolver dois valores possíveis: verdadeiro ou falso.
	// No Java, uma comparação sempre devolve um valor boolean
	System.out.println(1 == 1); // true.
	System.out.println(1 != 1); // false.
	System.out.println(2 < 1); // false.
	System.out.println(2 > 1); // true.
	System.out.println(1 >= 1); // true.
	System.out.println(2 <= 1); // false.
	//
	// // true.
	System.out.println(1 == 1.0);
	// true.
	System.out.println(1 == 1);
	// true. 1.0 float é 1.0 double
	System.out.println(1.0f == 1.0d);
	// true. 1.0 float é 1 long
	System.out.println(1.0f == 1l);
	//
	// Os valores não primitivos (referências) e os valores boolean devem ser
	// comparados somente com dois comparadores, o de igualdade ( == ) e o de desigualdade ( != ).
	//
	// não compila, tipo não primitivo só aceita != e ==
	// System.out.println("Mario" > "Guilherme");
	// não compila, boolean só aceita != e ==
	// System.out.println(true < false);
	//
	// não compila, boolean é boolean
	// System.out.println(true == 1);
	//
	// compila, ’a’ tem valor numérico também
	System.out.println('a' > 1);

	// Cuidado, é muito fácil comparar atribuição com comparação e uma pe gadinha aqui pode passar despercebida, como no exemplo a seguir:
	int a = 5;
	System.out.println(a = 5); // não imprime true, imprime 5

	//
    }

    // Operadores lógicos
    public static void test06() {

	// Em lógica, as operações mais importantes são: e , ou , ou exclusivo e negação .
	System.out.println(1 == 1 & 1 > 2); // false. e
	System.out.println(1 == 1 | 2 > 1); // true. ou
	System.out.println(1 == 1 ^ 2 > 1); // false. exclusivo
	System.out.println(!(1 == 1)); // false. negacao

	// operadores de curto circuito
	// Quando aplicamos a operação lógica "e" , ao achar o primeiro termo falso não precisamos avaliar o restante da expressão.

	System.out.println(1 != 1 && 1 > 2);
	// false, o segundo termo não é avaliado.

	System.out.println(1 == 1 || 2 > 1);
	// true, o segundo termo não é avaliado.

	// A maior dificuldade com operadores de curto circuito é se a segunda parte
	// causa efeitos colaterais (um incremento, uma chamada de método). Avaliar
	// ou não (independente da resposta) pode influenciar no resultado final do programa.

	System.out.println(1 == 2 & method("hi"));
	// imprime hi, depois false
	System.out.println(1 == 2 && method("bye"));
	// não imprime bye, imprime false

	int i = 10;
	System.out.println(i == 2 & i++ == 0);
	// imprime false, soma mesmo assim

	System.out.println(i);
	// imprime 11

	int j = 10;
	System.out.println(j == 2 && j++ == 0);
	// imprime false, não soma

	System.out.println(j);
	// imprime 10
    }

    public static boolean method(String msg) {
	System.out.println(msg);
	return true;
    }

    // Operadores de incremento e decremento
    public static void test07() {
	int i = 5;

	// 5 - pós-incremento, i agora vale 6
	System.out.println(i++);
	// 6 - pós-decremento, i agora vale 5
	System.out.println(i--);
	// 5
	System.out.println(i);

	// E incrementos e decrementos antecipados:
	i = 5;

	System.out.println(++i);
	System.out.println(--i);
	System.out.println(i);
	// 6 - pré-incremento
	// 5 - pré

	// Cuidado com os incrementos e decrementos em relação a pré e pós.
	//
	// Existem ainda operadores para realizar operações e atribuições de uma só vez:

	int a = 10;
	// para somar 2 em a
	a = a + 2;
	// podemos obter o mesmo resultado com:
	a += 2;

	// exemplos de operadores:
	i = 5;

	i += 10; // soma e atribui
	System.out.println(i); // 15

	i -= 10; // subtrai e atribui
	System.out.println(i); // 5

	i *= 3; // multiplica e atribui
	System.out.println(i);

	// 15
	i /= 3; // divide a atribui
	System.out.println(i);

	// 5
	i %= 2; // divide por 2, e atribui o resto
	System.out.println(i);

	// 1
	System.out.println(i += 3); // soma 3 e retorna o resultado: 4

	//
	//

	byte b1 = 3; // compila, dá um desconto
	// b1 = b1 + 4; // não compila, conta com int devolve int
	byte b2 = 3; // compila, dá um desconto
	b2 += 4; // compila também, compilador gente boa!

	a = 10;
	a += ++a + a + ++a;

	// Um outro exemplo de operador pós-incremento, cujo resultado é 1 e 2:
	int j = 0;
	int h = (j++ * j + j++);
	System.out.println(h);
	System.out.println(j);
    }

    // Operador ternário - Condicional
    public static void test08() {
	// A estrutura do operador ternário é a seguinte: variável = teste_booleano ? valor_se_verdadeiro : valor_se_falso;
	int i = 5;
	System.out.println(i == 5 ? "match" : "oops"); // match
	System.out.println(i != 5 ? 1 : 2);
	// 2
	String mensagem = i % 2 == 0 ? "even" : "odd"; // even
    }

    // Operador de referencia
    public static void test09() {
	String s = new String("Caelum");
	// Utilizando o operador "." para acessar um
	// objeto String e invocar um método.
	int length = s.length();
    }
    
    // Concatenação de Strings
    public static void test10() {
	String s = "teste" + " mais";
    }
}
