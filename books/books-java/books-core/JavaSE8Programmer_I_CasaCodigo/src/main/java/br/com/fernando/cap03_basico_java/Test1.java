package br.com.fernando.cap03_basico_java;

// Defina o escopo de variáveis
public class Test1 {

    public static void test01(String s) {

	// Chamamos de locais as variáveis declaradas dentro de blocos, como den-
	// tro de métodos ou construtores. Antes de continuar, vamos estabelecer uma
	// regra básica: o ciclo de vida de uma variável local vai do ponto onde ela foi
	// declarada até o fim do bloco onde ela foi declarada

	int x = 10; // method local variable

	if (x >= 10) { // if - opening
	    int y = 50; // if local variable
	    System.out.print(y);
	} // if - closing

	// can't access 'y' variable

	for (int i = 0, j = 0; i < 10; i++)
	    j++;

	// System.out.println(j); // compilation error
    }

    // ==========================================================================

    // Variáveis de instância
    public static void test02() {

    }

    static class Person1 {

	// instance or object variable
	String name;

	public void setName(String n) {
	    // explicit (this) instance variable access
	    this.name = n;
	}
    }

    // ==========================================================================

    public static void test03() {
	Person2 p = new Person2();
	// instance reference access: 15
	System.out.println(p.id);
	// class reference access: 15
	System.out.println(Person2.id);
    }

    // Variáveis estáticas (class variables)
    static class Person2 {

	static int id = 15;
    }
    // ==========================================================================

    // Variáveis com o mesmo nome
    public static void test04(String par) {
	int a = 0;
	// int a = 10;// compile error

	// System.out.println(par); // which one?
    }

    static class Bla {

	static int a;

	// int a; // compile error
    }

    // Apesar de parecer estranho, é permitido declarar variáveis locais ou pa-
    // râmetros com o mesmo nome de variáveis de instância ou de classe. Essa
    // técnica é chamada de shadowing.

    static class Person3 {

	static int x = 0;

	int y = 0;

	public static void setX(int x) {
	    Person3.x = x; // type (class) explicit access
	}

	public void setY(int y) {
	    this.y = y; // instance (this) explicit access
	}
    }

    // Quando não usamos o this ou o nome da classe para usar a variável, o
    // compilador sempre utilizará a variável de “menor” escopo:

    static class X {

	int a = 100;

	public void method() {
	    int a = 200; // shadowing
	    System.out.println(a); // 200
	}
    }


    public static void main(String[] args) {
	test01("test");
    }
}
