package br.com.fernando.cap11_java_basics;

// Trabalhando com saída no console
public class Test02 {

    static class Test {

	public String toString() {
	    return "My test object...";
	}
    }

    public static void test01() {

	// O out é um atributo estático e público da classe System , do tipo
	// PrintStream . A classe PrintStream possui vários métodos que per-
	// mitem escrever diversos tipos de dados de maneira padronizada em um
	// OutputStream .

	System.out.print("hello world");

	System.out.print(false); // a boolean
	System.out.print(10.3); // a double value
	System.out.print("Some text"); // some text

	System.out.print(new Test()); // My test object...

	System.out.print(false); // a boolean

	// Existe um único caso especial que foge à regra; além de primitivos, strings
	// e Object , existe também uma sobrecarga que recebe um array de char .
	// Todos os outros arrays são tratados como Object

	char[] c = { 'a', 'b', 'c' };
	int[] i = { 1, 2, 3 };
	System.out.print(c); // abc
	System.out.print(i); // [I@9d8643e (ou similar)]

	// O método print apenas imprime o valor passado. Se invocado várias
	// vezes em sequência, todos os valores serão impressos em uma única linha:
	System.out.print(false);
	System.out.print(10.3);
	System.out.print("Some text");

	// Podemos imprimir cada conteúdo em uma linha diferente, concatenando
	// um "\n" após cada impressão. Para este caso, existe o método println ,
	// que adiciona uma quebra de linha após cada chamada:
	System.out.println(false);
	System.out.println(10.3);
	System.out.println("Some text");

	// O println possui as mesmas sobrecargas que o método print , além
	// de uma versão sem nenhum parâmetro, que apenas quebra uma linha:
	System.out.print("foo");
	System.out.println(); // line break
	System.out.print("bar");
    }

    // Formatando a impressão
    public static void test02() {

	// Na versão 5 do Java, foram incluídos dois métodos para permitir a impressão
	// no console de modo formatado, format e printf . Ambos se comportam exatamente
	// da mesma maneira, sendo que o printf foi incluído provavelmente apenas para
	// manter uma sintaxe próxima à da linguagem C ,

	// O printf recebe dois parâmetros: o primeiro é uma String que pode
	// conter apenas texto normal ou incluir caracteres especiais de formatação;
	// o segundo é um varargs de objetos a serem usados na impressão.

	System.out.printf("Hello %s, have a nice day!", "Mario");

	// Para indicar como a formatação deve ser feita, usamos a seguinte estrutura,
	// que veremos com mais detalhes a partir de agora:
	//
	// %[index$][flags][width][.precision]type
	//
	// Todas as opções entre [ ] são opcionais. Somos obrigados a informar ape-
	// nas o caractere de % e o tipo do argumento que será concatenado.
	//
	// Type é o tipo de argumento que será passado e suporta os seguintes valores:
	// • b boolean
	// • c char
	// • d Números inteiros
	// • f Números decimais
	// • s String
	// • n Quebra de linha
	// Vejamos alguns exemplos:

	System.out.printf("%s %n", "Foo"); // foo
	System.out.printf("%b %n", false); // false
	System.out.printf("%d %n", 42); // 42
	System.out.printf("%d %n", 1024L); // 1024
	System.out.printf("%f %n", 23.9F); // 23.900000
	System.out.printf("%f %n", 40.0); // 40.000000

	// Repare que podemos passar mais de uma instrução por impressão, sendo que cada

	System.out.printf("%s, it’s %b, the result is %d", "yes", true, 100);

	// O index é um número inteiro delimitado pelo caractere $ , que indica
	// qual dos argumentos deve ser impresso nessa posição se desejarmos fugir do
	// padrão sequencial. Por exemplo:
	System.out.printf("%2$s %1$s", "World", "Hello"); // Hello World

	// width indica a quantidade mínima de caracteres para imprimir. Completa
	// com espaços à esquerda caso o valor seja menor que a largura mínima.
	// Caso seja maior, não faz nada:

	System.out.printf("[%5d]%n", 22); // [ 22]
	System.out.printf("[%5s]%n", "foo"); // [ foo]
	System.out.printf("[%5s]%n", "foofoo"); // [foofoo]

	// flags são caracteres especiais que alteram a maneira como a impressão é
	// feita. Para a prova, é importante conhecer alguns, dentre os quais os dois que
	// indicam se o número é positivo ou negativo:

	// • + Sempre inclui um sinal de positivo (+) ou negativo (-) em números.
	// • ( Números negativos são exibidos entre parênteses.
	//
	// Dois de alinhamento à esquerda ou direita:
	// • - Alinha à esquerda. Precisa de tamanho para ser usado.
	// • 0 Completa a esquerda com zeros. Precisa de tamanho para ser usado.

	System.out.printf("[%05d]%n", 22); // [00022]
	System.out.printf("[%-5s]%n", "foo"); // [foo ]

	// Só é possível completar com zeros quando estamos formatando números.
	// Tentar usar esta flag com strings lança uma FormatFlagsConversionMismatchException

	// Temos uma flag para separar casa de milhares e decimais:
	// • , Habilita separadores de milhar e decimal.
	
	System.out.printf("%+d %n", 22); // +22
	System.out.printf("%,f %n", 1234.56); //1,234.560000
	System.out.printf("%(f %n", -1234.56); //(1234.560000)
	
	// precision indica quantas casas queremos depois da vírgula, basta usar um . seguido 
	// do número de caracteres. Vale lembrar que só é possível mudar a precisão quando
	// estamos formatando números decimais.
	
	System.out.printf("[%.2f]%n", 22.5); //[22.50]
    }

}
