package br.com.fernando.cap04_trabalhando_tipos_basicos;

// Criando e manipulando Strings
public class Test7 {

    public static void test01() {

	// Existem duas maneiras tradicionais de criar uma String , uma implícita e outra explícita:

	String implicit = "Java";
	String explicit = new String("Java");

	// Existem outras maneiras não tão comuns, como através de uma array:
	char[] name1 = new char[] { 'J', 'a', 'v', 'a' };
	String fromArray = new String(name1);

	// Ou ainda podemos criar uma String baseada em um StringBuilder ou StringBuffer :

	StringBuilder sb1 = new StringBuilder("Java");

	String nameBuilder = new String(sb1);

	StringBuffer sb2 = new StringBuffer("Java");

	String nameBuffer = new String(sb2);

	String name2 = null; // explicit null
	// Podemos concatenar Strings com o + :
	String name3 = "Java" + " " + "Exam";

	String nulled1 = null;
	System.out.println("value: " + nulled1); // value: null

	// E o contrário também tem o mesmo resultado:
	String nulled2 = null;
	System.out.println(nulled2 + " value"); // null value

	// Lembre-se da precedência de operadores. O exemplo a seguir mostra o
	// código sendo interpretado da esquerda pra direita (primeiro a soma):

	String value = 15 + 00 + " certification";
	System.out.println(value); // 15 certification
    }

    // String sao imutaveis
    public static void test02() {
	//
	String s = "caelum";
	s.toUpperCase();
	System.out.println(s);

	// todos os métodos que parecem modificar uma String na verdade devolvem uma nova.
	//
	String s2 = s.toUpperCase();
	System.out.println(s2);

	// Lembre-se que a String possui um array por trás e, seguindo o padrão
	// do Java, suas posições começam em 0:

	// 0=g, devolve ’g’
	char caracter0 = "guilherme".charAt(0);
	// 0=g 1=u, devolve ’u’
	char caracter1 = "guilherme".charAt(1);
	// 0=g 1=u 2=i, devolve ’i’
	char character2 = "guilherme".charAt(2);
    }

    // Principais métodos de String
    public static void test03() {
	// O método length imprime o tamanho da String :
	String s = "Java";
	System.out.println(s.length()); // 4
	// System.out.println(s.length); // não compila: não é atributo
	// System.out.println(s.size()); // não compila: não existe size em String Java

	// Já o método isEmpty diz se a String tem tamanho zero:
	System.out.println("".isEmpty()); // true
	System.out.println("java".isEmpty()); // false
	System.out.println(" ".isEmpty()); // false

	//
	// Devolvem uma nova String:
	// • String toUpperCase() tudo em maiúscula;
	//
	// • String toLowerCase() tudo em minúsculo;
	//
	// • String trim() retira espaços em branco no começo e no fim;
	//
	// • String substring(int beginIndex, int endIndex) devolve a substring a partir dos índices de começo e fim;
	//
	// • String substring(int beginIndex) semelhante ao anterior, mas toma a substring a partir do índice passado até o final da String;
	//
	// • String concat(String) concatena o parâmetro ao fim da String atual e devolve o resultado;
	//
	// • String replace(char oldChar, char newChar) substitui todas as ocorrências de determinado char por outro;
	//
	// • String replace(CharSequence target, CharSequence replacement) substitui todas as ocorrências de determinada CharSequence (como String) por outra.
    }

    // Comparação:
    public static void test04() {
	// • boolean equals(Object) compara igualdade caractere a caractere (herdado de Object );
	// • boolean equalsIgnoreCase(String) compara caractere a caractere ignorando maiúsculas/minúsculas;
	// • int compareTo(String) compara as 2 Strings por ordem lexicográfica (vem de Comparable );
	// • int compareToIgnoreCase(String) compara as 2 Strings por ordem lexicográfica ignorando maiúsculas/minúsculas.
	//
	// E aqui, todas as variações desses métodos. Não precisa saber o número exato que o compareTo retorna, basta saber que será negativo caso
	// a String na qual o método for invocado vier antes, zero se for igual, positivo se vier depois do parâmetro passado:
	//
	String text = "Certification";
	System.out.println(text.equals("Certification")); // true
	System.out.println(text.equals("certification")); // false
	System.out.println(text.equalsIgnoreCase("CerTifIcatIon"));// true
	System.out.println(text.compareTo("Aim")); // 2
	System.out.println(text.compareTo("Certification")); // 0
	System.out.println(text.compareTo("Guilherme")); // -4
	System.out.println(text.compareTo("certification")); // -32
	System.out.println(text.compareToIgnoreCase("certification"));// 0
    }

    // Buscas simples:
    public static void test05() {
	// • boolean contains(CharSequence) devolve true se a String contém a sequência de chars ;
	//
	// • boolean startsWith(String) devolve true se começa com a String do parâmetro;
	//
	// • boolean endsWith(String) devolve true se termina com a String do parâmetro;
	//
	// • int indexOf(char) e int indexOf(String) devolve o índice da primeira ocorrência do parâmetro;
	//
	// • int lastIndexOf(char) e int lastIndexOf(String) devolve o índice da última ocorrência do parâmetro.

	// O código a seguir exemplifica todos os casos desses métodos:
	String text = "Pretendo fazer a prova de certificação de Java";
	System.out.println(text.indexOf("Pretendo")); // imprime 0
	System.out.println(text.indexOf("Pretendia")); // imprime -1
	System.out.println(text.indexOf("tendo")); // imprime 3
	System.out.println(text.indexOf("a")); // imprime 10
	System.out.println(text.lastIndexOf("a")); // imprime 45
	System.out.println(text.lastIndexOf("Pretendia")); // imprime -1
	System.out.println(text.startsWith("Pretendo")); // true
	System.out.println(text.startsWith("Pretendia")); // false
	System.out.println(text.endsWith("Java")); // true
	System.out.println(text.endsWith("Oracle")); // false
    }
}
