package br.com.fernando.cap09_Trabalhando_com_heranca;

// Determine quando é necessário fazer casting
public class Test04 {

    public static void test01() {
        // Às vezes, temos referências de um tipo mas sabemos que lá há um objeto de
        // outro tipo, um mais específico

        String[] args = new String[] { "Test" };

        Object[] objects = new Object[100];

        String s = "certification";
        objects[0] = s;

        // String recovered = objects[0]; //
        //
        // Temos um array de referências para Object. Nem todo Object é
        // uma String, então o compilador não vai deixar você fazer essa conversão.
        // Lembre-se que, em geral, o compilador não conhece os valores das variáveis,
        // apenas seus tipos.
        // Vamos precisar moldar a referência para que o código compile:
        String recovered = (String) objects[0];
        // Durante a execução, o casting vai ver se aquele objeto é mesmo compatível
        // com o tipo String (no nosso caso é). Se não fosse, ele lançaria uma ClassCastException (exceção unchecked).
    }

    static class Motor {
    }

    //
    static class Vehicle {
    }

    static class Motorcycle extends Vehicle {
    }

    static class Car extends Vehicle {
    }

    //
    static class RunnableCar extends Car implements Runnable {

        @Override
        public void run() {
        }
    }

    public static void test02() {
        final Vehicle v = new Car();

        final Motorcycle m1 = (Motorcycle) v; // ClassCastException, nao eh Motorcycle
        // Quando o código for executado, haverá um erro de execução: ClassCastException. Car não é uma Motorcycle.

        // Cuidado que, se o casting for totalmente impossível, o compilador já acusará erro:
        Car c = new Car();
        // Motorcycle m2 = (Motor) c; // Type mismatch
        //
        // É importante lembrar que quando não precisamos de casting, ele é opcional,
        // portanto todas as linhas a seguir funcionam com ou sem casting:

        String guilherme = "guilherme";
        String name = guilherme;
        String name2 = (String) guilherme;
        Object name3 = guilherme;
        Object name4 = (String) guilherme;
        Object name5 = (Object) guilherme;

        //
        // Mas e quando fazemos casting com interfaces envolvidas? Apesar de não
        // existir herança múltipla, podemos implementar múltiplas interfaces! Fazer
        // casting para interfaces sempre é possível e vai compilar (há apenas uma exceção a essa regra).

        //
        // Pegue uma interface qualquer, por exemplo Runnable. O código a seguir compila:
        Car c2 = new Car();
        Runnable r = (Runnable) c2; // lanca ClassCastingException.
        //
        // Um Car pode ser um Runnable? Sabemos que a classe Car propriamente
        // não implementa essa interface. Mas existe a possibilidade de existir
        // algum objeto em Java que seja, ao mesmo tempo, Car e Runnable?
        //
        // O compilador não sabe o valor da variável c naquele exemplo. Ele não
        // sabe que na verdade é uma instância de Car e não de RunnableCar. Ele
        // sabe apenas que é do tipo Car e, pela simples possibilidade de existir um
        // objeto que seja Car e Runnable, ele deixa o código compilar.
        //
        // Se a classe "Car" for final, entao nao tera filhos q podem estender Runnable, logo o codigo acima nao compilaria
    }

    // instanceof
    public static void test03() {
        // Ooperador instanceof ( variable instanceof ClassName) devolve 'true'
        // caso a referência variable aponte para um objeto do tipo ou subtipo de ClassName.

        Object c = new Car();
        boolean b1 = c instanceof Car; // true
        boolean b2 = c instanceof Motorcycle; // false
        //
        // O instanceof não compila se a referência em questão for obviamente incompatível, por exemplo:
        String s = "a";
        // boolean b = s instanceof java.util.List; // compile error

    }

}
