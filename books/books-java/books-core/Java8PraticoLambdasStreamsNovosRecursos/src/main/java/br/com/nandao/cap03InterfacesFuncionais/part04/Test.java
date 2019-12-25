package br.com.nandao.cap03InterfacesFuncionais.part04;

// Indo mais a fundo: primeiros detalhes
//
// Interfaces funcionais são o coração do recurso de Lambda. O Lambda por si só não
// existe, e sim expressões lambda, quando atribuídas/inferidas a uma interface funcional.
public class Test {

    // É sempre necessário haver a atribuição (ou alguma forma de inferir) daquele
    // lambda para uma interface funcional.
    public static void test1() {

        // Se atribuirmos o lambda para um tipo que seja uma interface funcional compatível,
        // isso é, com os argumentos e retornos que o método necessita, aí sim temos sucesso na compilação:
        final Runnable o = () -> {
            System.out.println("O que sou eu? Que lambda?");
        };

        System.out.println(o);
        System.out.println(o.getClass());
    }

    // Captura de variáveis locais
    public static void test2() {
        // Assim como numa classe anônima local, você também pode acessar as variáveis
        // finais do método a qual você pertence:

        final int numero = 5;

        new Thread(() -> {
            System.out.println(numero);
        }).start();

        // Porém, ela deve ser efetivamente final. Isso é, apesar de não precisar declarar as
        // variáveis locais como final, você não pode alterá-lasse estiver utilizando-as dentro
        // do lambda.

    }

}
