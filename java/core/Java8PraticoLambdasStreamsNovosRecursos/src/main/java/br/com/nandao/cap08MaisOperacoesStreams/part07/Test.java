package br.com.nandao.cap08MaisOperacoesStreams.part07;

import java.util.List;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test {

    // Stream primitivos
    public static void test1() {

        // O IntStream, o LongStream e o DoubleStream possuem operações especiais
        // e que são importantes. Até mesmo o iterator deles devolvem Iterators
        // diferentes. No caso do IntStream, é o PrimitiveIterator.OfInt, que implementa
        // Iterator<Integer> mas que, além de um next que devolve um
        // Integer fazendo o boxing, também possui o nextInt

        // Assim como o comparingInt, que devolve um comparator sem fazer autoboxing
        // 
        final IntStream intStream1 = IntStream.of(1, 2, 3);
        // > 1, 2, 3
        //
        final IntStream intStream2 = IntStream.range(1, 3);
        // > 1, 2
        //
        final IntStream intStream3 = IntStream.rangeClosed(1, 3);
        // > 1, 2, 3
    }

    // Stream infinitos
    public static void test2() {

        // Um outro recurso poderoso do Java 8: através da interface de factory Supplier,
        // podemos definir um Stream infinito, bastando dizer qual é a regra para a criação
        // de objetos pertencentes a esse Stream.
        //
        // Por exemplo, se quisermos gerar uma lista “infinita” de números aleatórios, 
        // podemos fazer assim:
        final Random random = new Random(0);
        final Supplier<Integer> supplier = () -> random.nextInt();
        final Stream<Integer> stream1 = Stream.generate(supplier);
        //
        // O Stream gerado por generate é lazy. Certamente ele não vai gerar infinitos
        // números aleatórios. Eles só serão gerados à medida que forem necessários.
        //
        final IntStream stream2 = IntStream.generate(() -> random.nextInt());
    }

    // Operações de curto circuito 
    public static void test3() {

        // São operações que não precisam processar todos os elementos. 
        // Um exemplo seria pegar apenas os 100 primeiros elementos com limit:
        final Random random1 = new Random(0);
        final IntStream stream1 = IntStream.generate(() -> random1.nextInt());

        final List<Integer> list1 = stream1 //
                .limit(100) // limitando
                .boxed() // transforma em um Stream<Integer>
                .collect(Collectors.toList());

        // Não foi criado no Java um IntList que seria o análogo primitivo
        // a List<Integer>, e também não entraram no Java 8 os tais dos value objects ou
        // value types, que possibilitariam algo como List<int>.
        //
        // Vamos rever o mesmo código com a interface fluente:
        // 
        final Random random2 = new Random(0);
        //
        final List<Integer> list2 = IntStream.generate(() -> random2.nextInt()) //
                .limit(100) //
                .boxed() //
                .collect(Collectors.toList());
        //
        // Pode ser útil para um Supplier manter estado. Nesse caso, precisamos usar
        // uma classe ou classe anônima, pois dentro de um lambda não podemos declarar
        // atributos. Vamos gerar a sequência infinita de números de Fibonacci de maneira
        // lazy e imprimir seus 10 primeiros elementos:

        class Fibonacci implements IntSupplier {

            private int anterior = 0;

            private int proximo = 1;

            @Override
            public int getAsInt() {
                proximo = proximo + anterior;
                anterior = proximo - anterior;
                return anterior;
            }
        }
        //
        // 
        IntStream.generate(new Fibonacci()) //
                .limit(10) //
                .forEach(System.out::println);
        //
        // Veremos que manter o estado em uma interface funcional pode limitar os recursos
        // de paralelização que um Stream fornece.
        //
        // Além do limit, há outras operações que são de curto-circuito. O findFirst
        // é uma delas. Mas não queremos pegar o primeiro elemento Fibonacci. Quero pegar
        // o primeiro elemento maior que 100! Como fazer? Podemos filtrar antes de invocar
        // o findFirst:
        // 
        final int maiorQue100 = IntStream //
                .generate(new Fibonacci()) //
                .filter(f -> f > 100) //
                .findFirst() //
                .getAsInt();

        System.out.println(maiorQue100);
        //
        //
        // Os matchers também são de curto-circuito. Podemostentar descobrirse todos os
        // elementos de Fibonacci são pares com allMatch(f -> f % 2 ==0). Se houver
        // algum impar, ele retornará falso. Mas se houvesse apenas pares, ele rodaria indefinidamente!
        //
        // Lembre-se: trabalhar com Streams infinitos pode ser perigoso, mesmo
        // que você utilize operações de curto-circuito.
        //
        // Quando for necessário manter o estado de apenas uma variável, podemos usar
        // o iterate em vez do generate, que recebe um UnaryOperator. 
        // Para gerar os números naturais:

        IntStream.iterate(0, x -> x + 1) //
                .limit(10) //
                .forEach(System.out::println);


    }

    public static void main(final String[] args) {

    }
}
