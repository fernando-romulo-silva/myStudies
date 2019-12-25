package br.com.fernando.cap09_Trabalhando_com_heranca;

// Use super e this para acessar objetos e construtores
public class Test05 {

    static class Parent01 {

        public Parent01(String msg) {
            System.out.println(msg);
        }
    }

    static class Child01 extends Parent01 {

        public Child01(String name) {
            super("parent");
            System.out.println("child");
        }
    }

    public static void test01() {
        // Se nenhum construtor da mãe foi escolhido através da palavra
        // super(...), o compilador coloca automaticamente super(); no começo
        // do nosso construtor, sem nem olhar para a classe mãe.
        // vai primeiro imprimir "parent" e depois "child"
        Child01 child = new Child01("test");
    }

    static class Parent02 {

        public Parent02() {
            System.out.println("parent");
        }
    }

    static class Child02 extends Parent02 {

        public Child02(String name) {
            // super(); // implicit!
            System.out.println("child");
        }
    }

    public static void test02() {
        // Vai primeiro imprimir “parent” e só depois “child”.
        Child02 child = new Child02("test");
    }

    // Uma outra possibilidade, no caso de termos mais de um construtor, é
    // chamarmos outro construtor da própria classe através do this():
    public static void test03() {
        // Agora vai produzir “parent”, “child 1” e “child 2”.

        Child03 child = new Child03("test");

        // Atenção, a chamada do construtor com super ou this só pode aparecer
        // como primeira instrução do construtor. Portanto, só podemos fazer uma
        // chamada desses tipos.

    }

    static class Parent03 {

        public Parent03() {
            System.out.println("parent");
        }
    }

    static class Child03 extends Parent03 {

        public Child03() {
            // super(); // implicit
            System.out.println("child 1");
        }

        public Child03(String name) {
            this();
            System.out.println("child 2");
        }

        public Child03(int age) {
            super();
            // this(); // compile error
        }

        public Child03(long l) {
            this();
            // this(); // compile error
        }
    }

    // this e variáveis membro
    // Por vezes, temos variáveis membro com o mesmo nome de variáveis locais.
    // O acesso sempre será a variável local, exceto quando colocamos o this,
    // que indica que a variávelmembro será acessada.

    int i = 5;

    void run(int i) {
        System.out.println(i);
        System.out.println(this.i);
    }

    public static void test04() {
        // O código a seguir imprimirá 3 e depois 5:
        new Test05().run(3);
    }

    // Como mostramos, caso a variável seja escondida por uma variável com
    // mesmo nome em uma classe filha, podemos diferenciar o acesso à variável
    // membro da classe filha ou da pai, explicitando this ou super:
    //
    class A {

        int i = 5;
    }

    class Test extends A {

        int i = 10;

        void run(int i) {
            System.out.println(i); // 3
            System.out.println(this.i); // 10
            System.out.println(super.i); // 5
        }
    }

    // Lembre-se que o binding de uma variável ao tipo é feito em compilação,
    // portanto se tentarmos acessar a variável speed fora do Car através de uma
    // referência a Car, o valor alterado é o da variável Car.speed:
    static class Vehicle {

        double speed = 30;
    }

    static class Car extends Vehicle {

        double speed = 50;

        void print() {
            System.out.println(speed); // 1000 // 50
            System.out.println(this.speed); // 1000 // 50
            System.out.println(super.speed); // 30 // 1000
        }
    }

    public static void test05() {
        final Car c = new Car();
        c.speed = 1000;
        c.print();
    }

    // E se fizermos o mesmo através de uma referência a Vehicle, alteramos
    // a speed do Vehicle:
    public static void test06() {
        Car c = new Car();
        ((Vehicle) c).speed = 1000;
        c.print();
    }

    // Estático não tem this nem super
    // Contextos estáticos não possuem nem this nem super, uma vez que
    // o código não é executado dentro de um objeto:

    static class S {

        int i = 5;
    }

    static class T extends S {

        int i = 10;

        public static void test() {
           //  this.i = 5; // this? compile error
           //  super.i = 10; // super? compile error
        }
    }
    
    // Por fim, uma última restrição: interfaces não podem ter métodos estáticos,
    // não compila (métodos default, que existem desde o Java 8 não são
    // cobrados nesta prova).
}
