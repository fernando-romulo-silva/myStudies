package br.com.fernando.cap05_Usando_operadores_e_construcoes_de_decisao;

// Teste a igualdade entre Strings e outros objetos usando == e equals()
public class Test03 {

    public static void test01() {

	String name1 = new String("Mario");
	String name2 = new String("Mario");

	System.out.println(name1 == name2); // false

	// Até aqui tudo bem. Mas vamos alterar um pouco nosso código, mudando
	// a maneira de criar nossas Strings, e rodar novamente:

	String name3 = "Mario";
	String name4 = "Mario";

	System.out.println(name3 == name4); // o que imprime? true

	// Pool de Strings
	// O Java mantém um pool de objetos do tipo String . Antes de criar uma
	// nova String, primeiro o Java verifica neste pool se uma String com o mesmo
	// conteúdo já existe; caso sim, ele a reutiliza, evitando criar dois objetos exata-
	// mente iguais na memória. Como as duas referências estão apontando para o
	// mesmo objeto do pool, o == retorna true .
	// Mas por que isso não aconteceu antes, com nosso primeiro exemplo? O
	// Java só coloca no pool as Strings criadas usando literais. Strings criadas com
	// o operador new não são colocadas no pool automaticamente.

	String s1 = "string"; // será colocada no pool
	String s2 = "string";// será colocada no pool
	String s3 = new String("string"); // NAO será colocada no pool

	System.out.println(s1 == s2); // true, mesma referencia
	System.out.println(s1 == s3); // false, referências diferentes
	System.out.println(s1.equals(s3)); // true, mesmo conteúdo

	// Quando concatenamos literais, a String resultante também será colocada no pool
	// apenas usando literais em ambos os lados da concatenação.
	String ab = "a" + "b";
	System.out.println("ab" == ab); // true

	// os métodos de String sempre criam novos objetos?
	// Nem sempre. Se o retorno do método for exatamente o conteúdo atual do objeto, nenhum objeto novo é criado

	String str = "HELLO WORLD";
	String upper = str.toUpperCase();
    }

    // Contando Strings
    public static void test02() {
	// Cria 2 objetos, um literal (que vai para o pool) e o outro com o new
	String h = new String("hello ");

	// nenhum objeto criado, usa o mesmo do pool
	String h1 = "hello ";

	// novo objeto criado e inserido no pool
	String w = "world";

	// nenhum objeto criado, usa do pool
	System.out.println("hello ");

	// criado um novo objeto resultante da concatenação, mas este não vai para o pool
	System.out.println(h1 + "world");

	// Novo objeto criado e colocado no pool (Hello com H maiúsculo).
	System.out.println("Hello " == h1);

	// * Cuidado com String já colocadas no pool *S
    }

    // O método equals

    static class Client1 {

	private String name;

	Client1(String name) {
	    this.name = name;
	}
    }

    static class Client2 {

	private String name;

	Client2(String name) {
	    this.name = name;
	}

	// Cuidado ao sobrescrever o método equals : ele deve ser público, e
	// deve receber Object . Caso você receba uma referência a um objeto do
	// tipo Client , seu método não está sobrescrevendo aquele método padrão
	// da classe Object , mas sim criando um novo método (overload)

	public boolean equals(Object o) {
	    if (!(o instanceof Client2)) {
		return false;
	    }
	    Client2 second = (Client2) o;
	    return this.name.equals(second.name);
	}

	// nao sobreescreve
	public boolean equals(Client2 second) {
	    return this.name.equals(second.name);
	}
    }

    public static void test03() {
	// Para comparar duas referências, podemos sempre usar o operador ==

	Client1 c1 = new Client1("guilherme");
	Client1 c2 = new Client1("mario");

	System.out.println(c1 == c2); // false
	System.out.println(c1 == c1); // true

	Client1 c3 = new Client1("guilherme");
	System.out.println(c1 == c3); // false, pois não é a mesma referência: são objetos diferentes na memória

	// Para comparar os objetos de uma outra maneira, que não através da referência, podemos utilizar o método equals
	// Isso é, existe um método em Object que você pode reescrever para definir um critério de comparação de igualdade.

	Client2 c11 = new Client2("guilherme");
	Client2 c21 = new Client2("mario");

	System.out.println(c11.equals(c21)); // false
	System.out.println(c11.equals(c11)); // true

	Client2 c31 = new Client2("guilherme");
	System.out.println(c11.equals(c31)); // true
    }

}
