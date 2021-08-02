package br.com.fernando.cap04_trabalhando_tipos_basicos;

// Manipule dados usando a classe StringBuilder e seus métodos
public class Test6 {

    public static void test01() {

        // Para suportar Strings mutáveis, o Java possui as classes StringBuffer e
        // StringBuilder. A operação mais básica é o append que permite concatenar
        // ao mesmo objeto:
        StringBuffer sb = new StringBuffer();
        sb.append("Caelum");
        sb.append(" - ");
        sb.append("Alura - Casa do Código");
        //
        System.out.println(sb); // Caelum - Alura - Casa do Código
        //
        // Podemos criar um objeto desse tipo de diversas maneiras diferentes:
        // vazio
        StringBuilder sb1 = new StringBuilder();
        // conteudo inicial
        StringBuilder sb2 = new StringBuilder("java");
        // tamanho inicial do array para colocar a string
        StringBuilder sb3 = new StringBuilder(50);
        // baseado em outro objeto do mesmo tipo
        StringBuilder sb4 = new StringBuilder(sb2);
        //
        // ao definir o tamanho do array, não estamos criando uma
        // String de tamanho definido, somente um array desse tamanho que será utilizado pelo StringBuilder, portanto:
        StringBuilder sb5 = new StringBuilder(50);
        System.out.println(sb5); // linha em branco
        System.out.println(sb5.length()); // 0
        //
        // Inclusive, em Java, quando fazemos concatenação de Strings usando o +,
        // por baixo dos panos, é usado um StringBuilder. Não existe a operação + na classe String.
    }

    // Principais métodos de StringBuffer e StringBuilder
    public static void test02() {

        StringBuffer sb = new StringBuffer();
        sb.append("Caelum").append(" - ").append("Ensino e Inovação");
        System.out.println(sb); // Caelum - Ensino e Inovação

        // Há ainda os métodos insert para inserir coisas no meio. Há versões
        // que recebem primitivos, Strings, arrays de char etc. Mas todos têm o primeiro
        // argumento recebendo o índice onde queremos inserir:
        StringBuffer sb2 = new StringBuffer();
        sb2.append("Caelum - Inovação");
        sb2.insert(9, "Ensino e ");

        System.out.println(sb2); // Caelum - Ensino e Inovação

        //
        // Para converter um StringBuffer/ Builder em String, basta chamar
        // o toString mesmo. O método reverse inverte seu conteúdo:

        System.out.println(new StringBuffer("guilherme").reverse());

        //
        // Fora esses, também há o trim, charAt, length(), equals,
        // indexOf, lastIndexOf, substring.
        // Cuidado, pois o método substring não altera o valor do seu
        // StringBuilder ou StringBuffer, mas retorna a String que você deseja.
        // Existe também o método subSequence que recebe o início e o fim e
        // funciona da mesma maneira que o substring com dois argumentos.
    }
}
