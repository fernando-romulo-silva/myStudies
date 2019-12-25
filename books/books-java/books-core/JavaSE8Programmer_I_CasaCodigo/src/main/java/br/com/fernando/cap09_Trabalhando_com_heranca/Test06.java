package br.com.fernando.cap09_Trabalhando_com_heranca;

import java.io.Serializable;

// Use classes abstratas e interfacesS
public class Test06 {

    // Classes e métodos podem ser abstratos. Mesmo que soe estranho, uma classe
    // abstrata pode não ter nenhum método abstrato:

    static abstract class NoMethods {
    }

    // Se uma classe tem ummétodo que é abstrato, ela deve ser declarada como
    // abstrata, ou não compilará, como no exemplo a seguir:
    static class BasicClass { // compile error
        // public abstract void execute(); // compile error
    }

    // Uma classe abstrata não pode ser instanciada diretamente:
    static abstract class X {
    }

    public static void main(String[] args) {
        // X x = new X(); // compile error
    }

    // Um método abstrato é um método sem corpo, somente com a definição.
    // Uma classe que temumoumaismétodos abstratos precisa ser declarada como abstrata.
    abstract class Vehicle {

        public abstract void turnon();
    }

    // Uma classe concreta que herda de uma abstrata precisa implementar os
    // métodos para compilar:
    class Motorcycle extends Vehicle { // ok

        public void turnon() {
        }
    }

    // O exemplo a seguir mostra a herança que não compila uma vez que o
    // método herdado não foi implementado:
    // class SemRodas extends Vehicle {} // compile error

    // Caso a subclasse seja abstrata, os métodos abstratos da superclasse não
    // precisam ser implementados:
    abstract class Truck extends Vehicle { // ok
    }

    // Quando herdamos de uma classe abstrata que possui um método abstrato,
    // temos que escolher: ou implementamos o método, ou somos abstratos
    // também e passamos adiante a responsabilidade.

    //
    // O código de uma classe abstrata pode invocar métodos dela mesma, que
    // ainda são abstratos, uma vez que ele só será executado quando o objeto for
    // criado:

    abstract class Z {

        void x() {
            System.out.println(y());
        }

        abstract String y();
    }

    // Interfaces
    // Uma interface declara métodos que deverão ser implementados pelas
    // classes concretas que queiram ser consideradas como tal. Por padrão, são
    // todos métodos públicos e abstratos.

    static interface Vehicle02 {

        /* public abstract */ void turnon();

        public abstract int getSpeed();
    }

    // Quando você implementa a interface em uma classe concreta, é preciso
    // implementar todos os métodos:

    static class Car implements Vehicle02 {

        public void turnon() {
        }

        public int getSpeed() {
            return 0;
        }
    }

    // Uma classe pode implementar diversas interfaces:
    abstract class MyType implements Serializable, Runnable {
    }
    
    // Lembre-se que uma interface pode herdar de outra, inclusive de diversas interfaces:
    interface A extends Runnable {}
    interface B extends Serializable {}
    interface C extends Runnable, Serializable {}
    
    // Você pode declarar variáveis em uma interface, todas elas serão public
    // final static, isto é, constantes.
    
    interface W {
        /* public static final */ int i = 5;
        public /* static final */ int j = 5;
        public static /* final */ int k = 5;
        public static final int l = 5;
    }
}
