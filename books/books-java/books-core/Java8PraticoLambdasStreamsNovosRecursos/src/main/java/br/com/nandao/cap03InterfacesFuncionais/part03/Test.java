package br.com.nandao.cap03InterfacesFuncionais.part03;

// A anotação @FunctionalInterface
public class Test {

    // Podemos marcar uma interface como funcional explicitamente, para que o fato de
    // ela ser uma interface funcional não seja pela simples coincidência de ter um único método
    @FunctionalInterface
    interface Validador<T> {

        boolean valida(T t);

        // boolean outroMetodo(T t);
        // Ao compilar esse código, recebemos o seguinte erro:
        // java: Unexpected @FunctionalInterface annotation
        // Validador is not a functional interface
        //
        // Essa anotação serve apenas para que ninguém torne aquela interface em nãofuncional
        // acidentalmente. Ela é opcional justamente para que as interfaces das antigas
        // bibliotecas possam também ser tratadas como lambdas, independente da anotação, 
        // bastando a existência de um único método abstrato.
    }

    public static void test1() {

    }

    public static void main(final String[] args) {

    }

}
