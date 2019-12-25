package br.com.fernando.cap09_Trabalhando_com_heranca;

// Implementando herança
public class Test01 {

    // Em Java, podemos usar herança simples entre classes com o extends . A
    // nomenclatura usada é de classe mãe (parent class) e classe filha (child class),
    // ou superclasse e subclasse.

    class Parent {
    }

    class Child extends Parent {
    }

    class Grandchild extends Child {
    }

    // Vale lembrar que toda classe que não define de quem está herdando herda de Object :
    class Explicit extends Object {
    }

    class Implicit {
	// extends Object
    }

    // Mas não podemos herdar de duas classes explicitamente:
    class Simple1 {
    }

    class Simple2 {
    }
    // class Complex extends Simple1, Simple2 { } // compile error

    // Para podermos herdar de uma classe, a classe mãe precisa ser visível pela
    // classe filha e pelo menos um de seus construtores também.
    // Além disso, a classe mãe não pode ser final :
    //
    // Herança de métodos e atributos
    //
    // Todos os métodos e atributos de uma classe mãe são herdados, independente das visibilidades.
    // Dependendo da visibilidade e das classes envolvidas, a classe filha não consegue enxergar o membro herdado.
    //
    // Métodos estáticos e herança
    // Não existe herança de métodos estáticos. Mas quando herdamos de uma classe com métodos estáticos,
    // podemos chamar o método da classe mãe usando o nome da filha (embora não seja uma boa prática):

    static class W {

	static void method01() {
	    System.out.println("w1");
	}

	static void method02() {
	    System.out.println("w2");
	}
    }

    static abstract class Z extends W {
	// Por não existir herança, o modificador abstract não é aceito em mé-
	// todos estáticos como ilustra o exemplo a seguir:
	//
	// static abstract void run(); // compile error

	//
	//
	static void method02() {
	    System.out.println("z");
	}
    }

    public static void test01() {
	W.method01(); // w1
	Z.method01(); // w1

	// Já o uso do super não compila pois no contexto estático não existe objeto onde o método está sendo chamado:
	// super.method(); // compile error
	//
	// Podemos até escrever na subclasse um método estático de mesmo nome,
	// mas isso não é sobrescrita (alguns chamam de redefinição de um método):
	
	W.method02(); // w2
	Z.method02(); // z
    }
    
    // Construtores e herança
    // Não existe herança de construtores. O que existe é a classe filha chamar o construtor da mãe.
    //
    // Sobrescrita de atributos
    // Não existe sobrescrita de atributos. Podemos, sim, ter um atributo na classe filha com mesmo nome da mãe, mas não chamamos de sobrescrita.
    // Nesses casos, o objeto vai ter 2 atributos diferentes, um da mãe (acessível com super ) e um na filha (acessível com this ).

}
