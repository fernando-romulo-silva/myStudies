package br.com.fernando.cap13_Crie_manipule_dados_de_calendarios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

// Escreva uma expressão Lambda simples que consuma uma expressão Lambda Predicate
public class Test02 {

    @FunctionalInterface
    static interface Matcher<T> {

        boolean test(T t);
    }

    static class AgeOfMajority implements Matcher<Person> {

        @Override
        public boolean test(Person p) {
            return p.getAge() >= 18;
        }
    }

    static class PersonFilter {

        public List<Person> filter(List<Person> input, Matcher<Person> matcher) {

            List<Person> output = new ArrayList<>();

            for (Person person : input) {
                if (matcher.test(person)) {
                    output.add(person);
                }
            }

            return output;
        }
    }

    static class PersonFilterNew {

        public List<Person> filter(List<Person> input, Predicate<Person> matcher) {
            List<Person> output = new ArrayList<>();
            for (Person person : input) {
                if (matcher.test(person)) {
                    output.add(person);
                }
            }
            return output;
        }
    }

    public static void test01() {

        final List<Person> persons = Arrays.asList(new Person("Paul", 43), new Person("Mary", 34), new Person("John", 17));

        PersonFilter pf = new PersonFilter();
        List<Person> adults1 = pf.filter(persons, new AgeOfMajority());

        // O problema dessa abordagem é que, sempre que quisermos um critério
        // diferente, precisamos criar uma nova classe que implemente Matcher,
        // mesmo se for para usar apenas uma vez. Podemos reduzir um pouco esse impacto
        // usando classes anônimas,mas a legibilidade do código fica prejudicada:

        List<Person> adults2 = pf.filter(persons, new Matcher<Person>() {

            @Override
            public boolean test(Person p) {
                return p.getAge() >= 18;
            }
        });

        // É para resolver este tipo de problema que existem os lambdas. Um lambda
        // é um trecho de código que pode ser passado como parâmetro para um método
        // ou ser armazenado em uma variável para ser invocado posteriormente
        //
        // Para usar um lambda em Java, precisamos de uma interface funcional.
        // Interfaces funcionais são interfaces normais, mas com apenas um método.
        // Nossa interface Matcher pode ser considerada funcional.
        // É possível checar se uma interface é funcional usando a annotation FunctionalInterface

        Predicate<Person> matcher = new Predicate<Person>() {

            @Override
            public boolean test(Person p) {
                return p.getAge() >= 18;
            }
        };

        PersonFilterNew pfn = new PersonFilterNew();

        List<Person> adults = pfn.filter(persons, matcher);

        // Ok, vamos converter este código para um lambda.
        Predicate<Person> matcherNew = (Person p) -> {
            return p.getAge() >= 18;
        };

        Predicate<Person> matcherNewest = p -> p.getAge() >= 18;
        List<Person> adults3 = pfn.filter(persons, matcherNewest);

        // Não somos obrigados a armazenar o lambda em uma variável, podemos
        // passá-lo diretamente como parâmetro do método:
        List<Person> adults4 = pfn.filter(persons, p -> p.getAge() >= 18);

        // Antes de passar para o próximo exemplo, vamos entendera regras para se escrever um lambda.
        // • Lambdas podem ter vários argumentos, como um método. Bastasepará-los por ,.
        //
        // • O tipo dos parâmetros pode ser inferido e, assim, omitido da declaração.
        //
        // • Se não houver nenhum parâmetro, é necessário incluir parênteses vazios, como em:
        // // Runnable r = () -> System.out.println("a runnable object!");
        // Se houver apenas um parâmetro, podemos omitir os parênteses, como em:
        // /// Predicate<Person> matcher = p -> p.getAge() >= 18;
        //
        // • O corpo do lambda pode conter várias instruções, assim como um método.
        //
        // • Se houver apenas uma instrução, podemos omitir as chaves, como em :
        // // Predicate<Person> matcher = p -> p.getAge() >= 18;
        //
        // • Se houver mais de uma instrução, é necessário delimitar o corpo dolambda com chaves, como em:
        Runnable r = () -> {
            int a = 10;
            int b = 20;
            System.out.println(a + b);
        };
    }

    // Acessando variáveis do objeto com lambdas
    //
    // Lambdas podem interagir com as variáveis de instância dos objetos onde
    // foram declarados. Temos apenas que tomar cuidado com variáveis marcadas como final:
    public class LambdaScopeTest {

        public int instanceVar = 1;

        public final int instanceVarFinal = 2;

        private void test() {
            instanceVar++; // ok
            new Thread(() -> {
                System.out.println(instanceVar); // ok
                instanceVar++; // ok
                System.out.println(instanceVarFinal); // ok
                // instanceVarFinal++; // compile error
            }).start();
        }
    }

    // Lambdas só podem interagir com variáveis locais caso estas
    // estejam marcadas como final (uma referência imutável) ou que sejam
    // efetivamente final (não são final, mas não são alteradas). Não é possível
    // alterar o valor de nenhuma variável local dentro de um lambda:
    public static void test02() {

        int unchangedLocalVar = 3; // effectively final

        final int localVarFinal = 4; // final

        int simpleLocalVar = 0;

        simpleLocalVar = 9; // updated the value

        new Thread(() -> {
            System.out.println(unchangedLocalVar); // can read
            System.out.println(localVarFinal); // can read
            // System.out.println(simpleLocalVar); // compile error

        }).start();

    }

    // Conflitos de nomes com lambdas
    // As variáveis do lambda são do mesmo escopo que o método onde ele foi
    // declarado, portanto, não podemos declarar nenhuma variável, como parâmetro
    // ou dentro do corpo, cujo nome conflite com alguma variável local do método:
    public static void test04(String param) {
        String methodVar = "method"; // not final
        
        // Predicate<String> a = param -> param.length() > 0; // compile error
        
        // Predicate<String> b = methodVar -> methodVar.length() > 0; // compile error
        
        Predicate<String> c = newVar -> newVar.length() > 0; // ok
    }
}
