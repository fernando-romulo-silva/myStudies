package br.com.fernando.cap04_trabalhando_tipos_basicos;

// Chame métodos em objetos
public class Test5 {

    static class Person {

        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void test01() {
        Person p = new Person();
        // chamando método na variável de ref.
        p.setName("Mario");
        // Atribuindo o retorno do método a variável.
        String name = p.getName();
        // erro, método é void
        // String a = p.setName("X");
    }

    // =======================================================================
    // Argumentos variáveis: varargs
    //

    static class Calculator {

        public int sum(int... nums) {
            int total = 0;
            for (int a : nums) {
                total += a;
            }
            return total;
        }
    }

    static void method(int... x) {
    }

    static void method(int x) {
    }

    // static void method(int[] x) { // compile error S}

    public static void test02() {
        Calculator c = new Calculator();
        System.out.println(c.sum()); // 0
        System.out.println(c.sum(1)); // 1
        System.out.println(c.sum(1, 2)); // 3
        System.out.println(c.sum(1, 2, 3, 4, 5, 6, 7, 8, 9)); // 45

        // Em todos os casos, um array será criado, nunca null será passado. Um
        // parâmetro varargs deve ser sempre o último da assinatura dométodo para
        // evitar ambiguidade. Isso implica que apenas um dos parâmetros de um método
        // seja varargs. E repare que os argumentos variáveis têm que ser do
        // mesmo tipo.
        //
        //
        method(5);

        // Isso vai invocar o segundo método.

        // Podemos também passar um array de ints para um método que recebe um varargs:

        method(new int[] { 1, 2, 3, 4 });
        
        // void method(int[] x) { } compile error
    }
}
