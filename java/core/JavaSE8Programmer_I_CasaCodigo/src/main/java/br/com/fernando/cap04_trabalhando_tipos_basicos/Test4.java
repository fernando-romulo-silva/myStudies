package br.com.fernando.cap04_trabalhando_tipos_basicos;

import java.util.ArrayList;
import java.util.List;

// Explique o ciclo de vida de um objeto (criação, "de referência" e garbage collection)
public class Test4 {

    static class Person {

        String name;
    }

    // Criação de objetos
    public static void test01() {
        Person p1 = new Person(); // criando um novo objeto do tipo Person

        // Apenas declarando a variável,
        // nenhum objeto foi criado aqui
        Person p2;

        // Agora um objeto foi criado e atribuído a variável
        p2 = new Person();

        p2.name = "Mário"; // acessando e usando o objeto

        // Objeto inacessivel atribuímos a p o valor null
        // o objeto não está mais acessível
        p2 = null; // A

        // criando um objeto sem variável
        new Person(); // B

        // se a prova perguntar quantos objetos foram criados no
        // código acima, temos a criação de duas pessoas e uma String, totalizando
        // três objetos.

        int value = 100;
        if (value > 50) {
            Person p = new Person();
            p.name = "Guilherme";
        } // Após esta linha, o objeto do tipo Person não está mais acessível
    }

    // =========================================================================================
    //
    // Garbage Collector
    //
    static class Car {

    }

    static class Cars {

        List<Car> all = new ArrayList<Car>();
    }

    static class Bla {

        int b;
    }

    // Ao chegar na linha A, temos 9 objetos elegíveis do tipo Bla para o Garbage Collector.
    public static void test02() {
        Bla b;

        for (int i = 0; i < 10; i++) {
            b = new Bla();
            b.b = 10;
        }

        System.out.println("end"); // A
        // até essa linha todos ainda podem ser alcançados

        //
        //
        //

        Cars cars = new Cars();

        for (int i = 0; i < 100; i++)
            cars.all.add(new Car());

        // Nesse código, por mais que tenhamos criados 100 carros e um objeto do
        // tipo Cars, nenhum deles pode ser garbage coletado pois todos podem ser
        // alcançados direta ou indiretamente através de nossa thread principal.

    }
}
