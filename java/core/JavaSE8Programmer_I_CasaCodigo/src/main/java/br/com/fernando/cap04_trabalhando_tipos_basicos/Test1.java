package br.com.fernando.cap04_trabalhando_tipos_basicos;

// Declarar e inicializar variáveis
public class Test1 {

    int age;

    // A inicialização é obrigatória e pode ser implícita ou explícita.
    // É de fundamental importância saber que, para usar uma variável, é
    // necessário que ela tenha sido iniciada explicitamente ou implicitamente em
    // algum momento antes da sua utilização.
    public void method1() {
        int age;
        // System.out.println(age); // compilation error
    }

    public void method2(int a) {
        int age;
        age = 10; // explicit initialization
        System.out.println(age); // ok

        // Podemos declarar e iniciar a variável na mesma instrução:
        double pi = 3.14;

        // Se eu tenho um if , a inicialização deve ser feita em todos os caminhos
        // possíveis para que não haja um erro de compilação no momento da utilização
        // da variável:
        double x;
        if (a > 1) {
            x = 6;
        }
        // System.out.println(x); // compile error
    }

    // Quando a variável é membro de uma classe, ela é iniciada implicitamente
    // junto com o objeto com um valor default, esse processo pode ser chamado de
    // inicialização implícita ( implicit initialization ).

    class Exam {

        double timeLimit; // implicit initialization: 0.0
    }

    Exam exam = new Exam();

    public void method3() {
        System.out.println(exam.timeLimit);

        // Outro momento em que ocorre a inicialização implícita é na criação de arrays:
        int[] numbers = new int[10];
        System.out.println(numbers[0]); // 0
    }

    // ============================================================================================
    // Tipos primitivos
    //
    // Todos os tipos primitivos do Java já estão definidos e não é possível criar
    // novos tipos primitivos. São oito os tipos primitivos do Java: byte , short ,
    // char , int , long , float , double e boolean .
    //
    // O boolean é o único primitivo não numérico. Todos os demais armazenam números:
    // double e float são ponto flutuante, e os demais, todos inteiros (incluindo char )
    //
    // ============================================================================================
    // Literais
    public void method4() {
        // compila pois 737821237891232 é um double válido
        System.out.println(737821237891232d);
        // compila pois 737821237891232 é um long válido
        System.out.println(737821237891232l);
        // não compila pois 737821237891232 é um valor maior que o int aceita
        // System.out.println(737821237891232);
        //
        // Da mesma maneira, o compilador é um pouco esperto e percebe se você tenta quebrar o limite de um int muito facilmente:
        // compila pois 737821237891232l é um long válido
        long l = 737821237891232l;

        // não compila pois o compilador não é bobo assim
        // int i = l;

        // boolean
        System.out.println(true); // booleano verdadeiro
        System.out.println(false); // booleano falso

        // números simples são considerados inteiros
        System.out.println(1); // int

        // números com casa decimal são considerados double.
        // Também podemos colocar uma letra "D" ou "d" no final
        System.out.println(1.0); // double
        System.out.println(1.0D); // double

        // números inteiros com a letra "L" ou "l"
        // no final são considerados long.
        System.out.println(1L); // long

        // números com casa decimal com a letra "F" ou "f"
        // no final são considerados float.
        System.out.println(1.0F); // float
    }

    //
    // ============================================================================================
    // Bases diferentes
    //
    // No caso dos números inteiros, podemos declarar usando bases diferentes.
    // O Java suporta a base decimal e mais as bases octal, hexadecimal e binária.
    public void method5() {
        // Um número na base octal tem que começar com um zero à esquerda e
        // pode usar apenas os algarismos de 0 a 7:
        int i = 0761; // octal
        System.out.println(i); // 497

        // E na hexadecimal, começa com 0x ou 0X e usa os algarismos de 0 a 15.
        // Como não existe um algarismo “15”, usamos letras para representar algarismos
        // de “10” a “15”, no caso, “A” a “F”, maiúsculas ou minúsculas:
        int j = 0xAB3400; // hexadecimal
        System.out.println(j); // 11219968

        // Já na base binária ( binary ), começamos com 0b , e só podemos usar “0” e “1":
        int b = 0b100001011; // binary
        System.out.println(b); // 267
    }

    //
    // ============================================================================================
    // Notação científica
    public void method6() {
        // Ao declarar doubles ou floats, podemos usar a notação científica:

        double d = 3.1E2;
        System.out.println(d); // 310.0

        float e = 2e3f;
        System.out.println(e); // 2000.0

        float f = 1E4F;
        System.out.println(f); // 10000.0
    }

    //
    // ============================================================================================
    // Usando underlines em literais
    public void method7() {
        // Exemplo correto
        int a = 123_456_789;

        // Outros Exemplos
        //
        int v1 = 0_100_267_760; // ok
        // int v2=0_ x_4_13; // erro, _ antes e depois do x
        // int v3=0b_ x10_BA_75; // erro, _ depois do b
        // int v4=0b_ 10000_10_11; // erro, _ depois do b
        int v5 = 0xa10_AF_75; // ok, apesar de ser letra
        // representa dígito
        // int v6=_123_341; // erro, inicia com _
        // int v7=123_432_; // erro, termina com _
        int v8 = 0x1_0A0_11; // ok
        int v9 = 144__21_12; // ok
        //
        // A mesma regra se aplica a números de ponto flutuante:
        // double d1=345.45_ e3; // erro, _ antes do e
        // double d2=345.45e_3; // erro, _ depois do e
        double d3 = 345.4_5e3; // ok
        double d4 = 34_5.45e3_2; // ok
        double d5 = 3_4_5.4_5e3; // ok
        // double d6=345._ 45F; // erro, _ depois do .
        // double d7=345_.45; // erro, _ antes do .
        // double d8=345.45_ F; // erro, _ antes do indicador de float
        // double d9=345.45_ d; // erro, _ antes do indicador de double
    }

    //
    // ============================================================================================
    // Usando underlines em literais
    public void method8() {
        char c1 = 'A';

        char c2 = 65;
        System.out.println(c1); // A

        // Não é necessário decorar a tabela unicode, mas é preciso prestar atenção a pegadinhas como a seguinte:
        char sete = 7; // número, pois não está entre aspas simples
        System.out.println(sete); // Não imprime nada!!!!

        char c3 = '\u03A9'; // unicode
        System.out.println(c3); // imprime a letra grega ômega
    }

    //
    // ============================================================================================
    // Identificadores
    public void method9() {
        // Chamamos de identificadores as palavras definidas pelo programador
        // para nomear variáveis, métodos, construtores, classes, interfaces etc.
        // Já palavras reservadas ou palavras-chave são termos predefinidos da linguagem
        // que podemos usar para definir comandos

        // Os identificadores são case sensitive, ou seja, respeitam maiúsculas e minúsculas:
        int aName; // ok
        int aname; // ok, diferente do anterior
        int _num; // ok
        int $_ab_c; // ok
        int x_y; // ok
        // int false; // inválido, palavra reservada
        // int x-y; // inválido, traço
        // int 4num; // inválido, começa com número
        // int av#f; // inválido, #
        // int num.spc; // inválido, ponto no meio
    }

}
