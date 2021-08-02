package br.com.fernando.cap12_Treanalhando_com_algumas_classes_da_java_API;

// Desenvolver código que usa classes wrappers como Boolean, Double e Integer
@SuppressWarnings("unused")
public class Test01 {

    // Classes Wrapper
    public static void test01() {

        // Wrappers são classes de objetos que representam tipos primitivos:
        //
        // • boolean : Boolean
        // • byte : Byte
        // • short : Short
        // • char : Character
        // • int : Integer
        // • long : Long
        // • float : Float
        // • double : Double
    }

    // Criando wrappers
    public static void test02() {
        // Todos os wrappers numéricos possuem dois construtores, um que recebe
        // o tipo primitivo, e um que recebe String

        Double d1 = new Double(22.5);
        Double d2 = new Double("22.5");
        Double d3 = new Double("abc"); // throws NumberFormatException

        // A classe Character possui apenas um construtor, que recebe um char
        // como argumento:
        Character c = new Character('d');
        //
        // A classe Boolean também possui dois construtores, um que recebe
        // boolean e outro que recebe String. Caso a String passada como argumento
        // tenha o valor "true", com maiúsculas ou minúsculas, o resultado
        // será true; qualquer outro valor resultará em false:
        Boolean b1 = new Boolean(true); // true
        Boolean b2 = new Boolean("true"); // true
        Boolean b3 = new Boolean("TrUe"); // true
        Boolean b4 = new Boolean("T"); // false
    }

    // Convertendo de wrappers para primitivos
    public static void test03() {
        // Existem vários métodos no formato xxxValue(), onde xxx é o tipo para
        // o qual gostaríamos de realizar a conversão:

        Long l = new Long("123");

        byte b = l.byteValue();
        double d = l.doubleValue();
        int i = l.intValue();
        short s = l.shortValue();

        // Todos os tipos numéricos podem ser convertidos entre si. Os tipos
        // Boolean e Character só possuem método para converter para o próprio
        // tipo primitivo:
        boolean b2 = new Boolean("true").booleanValue();
        char c = new Character('z').charValue();
    }

    // Convertendo de String para wrappers ou primitivos
    public static void test04() {
        // Existem métodos para realizar transformações entre strings, wrappers e primitivos.
        //
        // Cada wrapper possui um método no formato parseXXX onde XXX é o tipo do wrapper.
        // Este método também lança NumberFormatException caso não consiga fazer a conversão:
        double d = Double.parseDouble("23.4");
        long l = Long.parseLong("23");
        int i = Integer.parseInt("444");

        // Os wrappers de números inteiros possuem uma variação do parseXXX
        // que recebe como segundo argumento a base a ser usada na conversão:
        short i1 = Short.parseShort("11", 10); // 11 Decimal
        int i2 = Integer.parseInt("11", 16); // 17 HexaDecimal
        byte i3 = Byte.parseByte("11", 8); // 9 Octal
        int i4 = Integer.parseInt("11", 2); // 3 Binary
        int i5 = Integer.parseInt("A", 16); // 10 HexaDecimal
        int i6 = Integer.parseInt("FF", 16); // 255 HexaDecimal

        // Já para converter uma String diretamente para um wrapper podemos
        // ou usar o construtor como vimos anteriormente, ou usar ométodo valueOf
        Double d2 = Double.valueOf("23.4");
        Long l2 = Long.valueOf("23");
        Integer i11 = Integer.valueOf("444");
        Integer i12 = Integer.valueOf("5AF", 16);
    }

    // Convertendo de primitivos ou wrappers para String
    public static void test05() {
        // Assim como todo objeto Java, os wrappers também possuem um método toString:
        Integer i2 = Integer.valueOf(256);
        String number = i2.toString();

        // Além do toString padrão, há uma versão estática sobrecarregada, que
        // recebe o tipo primitivo como argumento. Ademais, os tipos Long e Integer
        // possuem uma versão que, além do primitivo, também recebem a base:

        String d = Double.toString(23.5);
        String s = Short.toString((short) 23);
        String i = Integer.toString(23);
        String l = Long.toString(20, 16);

        // Além destes, as classes Long e Integer ainda possuem outrosmétodos
        // para fazer a conversão direta para a base escolhida:
        String binaryString = Integer.toBinaryString(8); // 1000, binary
        String hexString = Long.toHexString(11); // B, Hexadecimal
        String octalString = Integer.toOctalString(22); // 26 Octal
    }

    // Autoboxing
    public static void test06() {
        // Até o Java 1.4 não era possível executar operações em cima de wrappers.
        // Por exemplo, para somar 1 em um Integer era necessário o seguinte código:
        Integer intWrapper = Integer.valueOf(1);
        int intPrimitive = intWrapper.intValue();
        intPrimitive++;
        intWrapper = Integer.valueOf(intPrimitive);

        // A partir do Java 5, foi incluído um recurso chamado autoboxing. O próprio
        // compilador é responsável por transformar os wrappers em primitivos
        // (unboxing) e primitivos em wrappers (boxing). A mesma operação de somar 1 em um Integer agora é:

        Integer intWrapper2 = Integer.valueOf(1);
        intWrapper2++; // will unbox, increment, then box again.
    }

    // Comparando wrappers
    public static void test07() {
        Integer i1 = 1234;
        Integer i2 = 1234;
        System.out.println(i1 == i2); // false
        System.out.println(i1.equals(i2)); // true

        // Apesar de parecer que estamos trabalhando com primitivos, estamos
        // usando objetos aqui, logo, quando esses objetos são comparados usando ==
        // o resultado é false, já que são duas instâncias diferentes de Integer.
        // Agora veja o seguinte código:

        Integer i3 = 123;
        Integer i4 = 123;
        System.out.println(i3 == i4); // true
        System.out.println(i3.equals(i4)); // true

        // Repare que o resultado do == foi true, mas o que aconteceu?
        // É que o Java, para economizar memória, mantém um cache de alguns objetos, e toda
        // vez que é feito um boxing ele os reutiliza. Os seguintes objetos são mantidos no cache:
        // • Todos Boolean e Byte;
        // • Short e Integer de -128 até 127;
        // • Caracter ASCII, como letras, números etc.
        
        // Sempre que você encontrar comparações usando == envolvendo wrappers,
        // prestemuita atenção aos valores: se forem baixos, é possível que o resultado
        // seja true mesmo sendo objetos diferentes!
    }
    
    // NullPointerException em operações envolvendo wrappers
    public static void test08() {
        // Fique atento, pois, como wrappers são objetos, eles podem assumir
        // o valor de null. Qualquer operação executada envolvendo um objeto
        // null resultará em um NullPointerException:
        
        Integer a = null;
        int b = 44;
        System.out.println(a + b); //throws NPE
    } 
}
