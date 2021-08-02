package br.com.fernando.cap09_Trabalhando_com_heranca;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Desenvolva código que mostra o uso de polimorfismo
public class Test02 {

    static class Vehicle {

        public void turnon() {
            System.out.println("Vehicle running!");
        }
    }

    static class Car extends Vehicle {

        public void turnon() {
            System.out.println("Car running!");
        }
    }

    public static void test01() {
        // O método chamado aqui será o da classe Car , independente de a referência ser do tipo Vehicle
        // (o que importa é o objeto).
        // Qual método será executado (binding) é descoberto em tempo de execução
        // (em compile-time, a assinatura é decidida em tempo de compilação!),
        // isso é a chamada virtual de método (virtual method invocation).
        Car c = new Car();
        c.turnon(); // Car running!
        Vehicle v = new Car();
        v.turnon(); // Car running!
    }

    //
    // Para reescrever um método, é necessário:
    //
    // • exatamente o mesmo nome;
    // • os parâmetros têm que ser iguais em tipo e ordem (nomes das variáveis podem mudar);
    // • retorno do método deve ser igual ou mais específico que o da mãe;
    // • visibilidade deve ser igual ou maior que o da mãe;
    // • exceptions lançadas devem ser iguais ou menos que na mãe;
    // • método na mãe não pode ser final .
    //
    // A regra sobre visibilidade é: um método reescrito só pode ter visibilidade maior
    // ou igual à do método que está sendo reescrito. (Essa não é uma regra mágica!
    // Faz todo o sentido; pense um pouco sobre o que poderia acontecer se essa regra não existisse).

    // Muito cuidado com interfaces, pois a definição de um método é, por padrão, public e o exercício
    // pode apresentar uma pegadinha de compilação:

    interface A {

        void a();
    }

    class B implements A {

        // void a() { } // compile error

        public void a() {
        } // okay
    }

    // Estranhamente, um método sobrescrito pode ser abstrato, dizendo para
    // o compilador que quem herdar dessa classe terá que sobrescrever o método original:

    class C {

        void a() {
        }
    }

    abstract class D extends C {

        abstract void a(); // ok
    }

    class E extends D {

        @Override
        void a() {
            // TODO Auto-generated method stub

        }
        // compile error
    }

    class F extends D {

        void a() {
            // ok
        }
    }

    // O retorno de um método pode ser covariante, o compilador permite que a classe
    // filha tenha um retorno igual ou mais específico polimorficamente (um subtipo).
    // Cuidado! O retorno covariante não vale para tipos primitivos.

    class A2 {

        List<String> metodo() {
            return null;
        }
    }

    class B2 extends A2 {

        ArrayList<String> metodo() { //
            return null;
        }
    }

    // Um método reescrito só pode lançar asmesmas exceções checked ou menos que o
    // métodos que está sendo reescrito (quanto às unchecked, não há regras e sempre
    // podemos lançar quantas quisermos).

    class A3 {

        public void method() throws SQLException, IOException {
        }
    }

    class B3 extends A3 {

        public void method() throws IOException { // ok
        }
    }

    // Esse código compila, pois o método na classe A lança menos exceções
    // que na classe mãe, respeitando a regra. Já o código a seguir não compila:
    class A4 {

        public void method() throws SQLException {
        }
    }

    class B4 extends A4 {

        // public void method() throws IOException { } // compile error
    }

    // Apesar de ambos os métodos lançarem apenas uma exceção, não é isso
    // que importa, pois elas são diferentes. Outro caso que não compila uma vez
    // que Exception é super tipo de IOException:

    class A5 {

        public void method() throws IOException {
        }
    }

    class B5 extends A5 {

        // public void method() throws Exception {} // compile error
    }

    // Repare que, quando dizemos menos exceções que na super classe, isso indica
    // não apenas quantidade, mas também devemos considerar o polimorfismo.

    // Exceptions do tipo RuntimeException podem ser adicionadas sem esse tipo
    // de restrição, uma vez que qualquer método pode jogar uma RuntimeException.
    // O exemplo a seguir compila justamente devido a essa regra:

    class A6 {

        public void method() {
        }
    }

    class B6 extends A6 {

        public void method() throws RuntimeException { // ok
        }
    }

    class C6 extends A6 {

        public void method() throws ArrayIndexOutOfBoundsException {
            // ok
        }
    }
    //
    // Veja as classes

    class Vehicle2 {

        void turnon() {
            System.out.println("Vehicle running");
        }
    }

    class Car2 extends Vehicle2 {

        void turnon() {
            System.out.println("Car running");
        }

        void turnoff() {
        }
    }

    class Motorcicle2 extends Vehicle2 {

        void turnon() {
            System.out.println("Motorcicle running");
        }
    }

    void method(Vehicle v) {
        v.turnon(); // ok
        // v.turnoff(); // compile error
        // Metodo turnoff esta no tipo Car2
    }
    // A regra geral é que somente podemos acessar os métodos de acordo com o tipo
    // da referência, pois a verificação da existência do método é feita em compilação.
    // Mas qual o método que será invocado, isso será conferido dinamicamente, em execução.

    // this, super e sobrescrita de métodos
    // Na ocasião em que um método foi sobrescrito, podemos utilizar as palavras-chave "super"
    // e "this" para deixar explícito qual método desejamos invocar:

    class A7 {

        public void method() {
            System.out.println("a");
        }

        public void method2() {
            System.out.println("parent method2");
        }
    }

    class B7 extends A7 {

        public void method() {
            System.out.println("b");
            super.method(); // a
        }

        public void method2() {
            System.out.println("c");
            method(); // b, a
            super.method(); // a
        }
    }

}
