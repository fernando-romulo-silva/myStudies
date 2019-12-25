package br.com.fernando.cap10_Lidando_com_excecoes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

// Invoque um método que joga uma exceção
public class Test04 {

    // Eventualmente, um método qualquer não tem condição de tratar um determinado
    // erro de execução. Nesse caso, esse método pode deixar passar o erro
    // para o próximo método na pilha de execução.
    // Para deixar passar qualquer erro de execução que não seja uma checked
    // exception, é muito simples: basta não fazer nada

    static void method01() {
        System.out.println("primeiro antes");
        method02();
        System.out.println("primeiro depois");
    }

    static void method02() {
        String s = null;
        System.out.println("segundo antes");
        s.length();
        System.out.println("segundo depois");
    }

    // Agora, para deixar passar uma checked exception, o método é obrigado
    // a deixar explícito (avisado) que pretende deixar passar.

    static void method03() {
        try {
            System.out.println("primeiro antes");
            method04();
            System.out.println("primeiro depois");
        } catch (IOException e) {
            // tratamento.
            System.out.println("primeiro catch");
        }
        System.out.println("primeiro fim");
    }

    static void method04() throws IOException {
        System.out.println("segundo antes");
        System.in.read(); // pode lançar IOException
        System.out.println("segundo depois");
    }

    // Essa invocação pode gerar um IOException, de modo que o segundo método tem duas
    // alternativas: ou pega e trata o possível erro ou o deixa passar. Para deixar
    // passar, o comando throws deve ser utilizado na sua assinatura do segundo
    // método. Isso indicará que um IOException pode ser lançado.
    //
    // Gerando um erro de execução
    //
    // Qualquermétodo, ao identificar uma situação errada, pode criar um erro
    // de execução e lançar para quem o chamou. Vale lembrar que os erros de execução
    // são representados por objetos criados a partir de alguma classe da hierarquia
    // da classe Throwable, logo, basta o método instanciar um objeto de
    // qualquer uma dessas classes e depois lançá-lo.
    // Se o erro não for uma checked exception, basta criar o objeto e utilizar o
    // comando throw para lançá-lo na pilha de execução (não confunda com o throws):

    static void method05() {
        try {
            method06();
        } catch (RuntimeException e) {
            // tratamento.
        }
    }

    static void method06() {
        throw new RuntimeException();
    }

    // Se o erro for uma checked exception, é necessário também declarar na
    // assinatura do método o comando throws
    //
    // Tome cuidado, somente instanciar a Exception não implica em jogála,
    // portanto o código a seguir mostra uma situação onde não será impresso error:

    static void method07() {
        try {
            method08();
        } catch (Exception e) {
            System.err.println("error");
        }
    }

    static void method08() throws Exception {
        new Exception(); // throw?????
    }

    // Podemos ainda criar nossas próprias exceções, bastando criar uma classe
    // que entre na hierarquia de Throwable.
    class FundoInsuficienteException extends Exception {
    }

    // Em qualquer lugar do código, é opcional o uso do try e catch de uma
    // unchecked exception para compilar o código. Em uma checked exception, é
    // obrigatório o uso do try/catch ou throws.
    //
    // exemplo a seguir mostra uma unchecked exception sendo ignorada e
    // o erro vazando, portanto finishing main não será impresso, mas a aplicação
    // morrerá com uma mensagem de erro da Exception que ocorreu.

    public static void method09() {
        method10();
        System.out.println("finishing main");
    }

    private static void method10() {
        int[] i = new int[10];
        System.out.println(i[15]);
        System.out.println("after i[15]");
    }

    //
    // Ao pegarmos a exception, será impresso também “finishing main” uma
    // vez que após o catch, o fluxo volta ao normal. O exemplo a seguir imprime
    // Exception caught e finishing main.
    public static void method091() {
        try {
            method10();
        } catch (RuntimeException ex) {
            System.out.println("Exception caught");
        }
        System.out.println("finishing main");
    }

    // Podemos ter também múltiplas expressões do tipo catch. Nesse caso,
    // será invocada somente a cláusula adequada, e não as outras. No código a
    // seguir, se o 'method04' jogar uma ArrayIndexOutOfBoundsException,
    // será impresso runtime:

    static void method1() {
        try {
            method04();
        } catch (IOException ex) {
            System.out.println("io");
        } catch (RuntimeException ex) {
            System.out.println("runtime");
        } catch (Exception ex) {
            System.out.println("any other exception");
        }
    }

    // Cuidado também com exceptions nos inicializadores. O código a seguir
    // não compila uma vez que durante a inicialização de um FileAccess pode
    // ser jogado um FileNotFoundException,mas o construtor dessa classe (o padrão)
    // não fala nada sobre isso:

    static class FileAccess01 {

        // compile error
        // private InputStream is = new FileInputStream("input.txt");
    }

    // Nesses casos, precisamos dizer no construtor que a Exception pode ser
    // jogada ao instanciar um objeto do tipo AcessoAoArquivo:

    static class FileAccess {

        private InputStream is = new FileInputStream("input.txt");

        FileAccess() throws IOException {
        }
    }
}
