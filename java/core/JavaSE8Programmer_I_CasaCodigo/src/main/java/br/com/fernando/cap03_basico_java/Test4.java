package br.com.fernando.cap03_basico_java;

import java.util.Date;

// É permitido também importar todas as classes de um pacote de uma vez,
// usando o * no lugar do nome da classe:
import java.util.*;

import static br.com.fernando.cap03_basico_java.model.Utils.*; // import static

// Importe outros pacotes Java e deixe-os acessíveis ao seu código
public class Test4 {

    Calendar birthday; // java.util.Calendar

    List<String> nicknames; // java.util.List

    // Se duas classes estão no mesmo pacote, elas se “enxergam” entre si, sem a
    // necessidade de colocar o nome do pacote.

    // Também é possível importar todas as classes de um determinado pacote,
    // basta usar um * após o nome do pacote. No exemplo a seguir importamos
    // todos os tipos do pacote order :
    //
    // import order.*;
    //
    // =========================================================================
    //
    // Importando classes com mesmo nome
    //
    // Quando precisamos usar duas classes com o mesmo nome mas de pacotes
    // diferentes, só podemos importar uma delas. A outra deve ser referenciada

    Date d1; // java.util

    // Full Qualified Name
    java.sql.Date d2; // java.sql

    // Caso tenhamos um import específico e um import genérico, o Java usa o específico:
    // import java.util.*;
    // import java.sql.Date;
    //
    // Por padrão, todas as classes do pacote java.lang são importadas.
    // Justamente por esse motivo é opcional escrevermos import
    // java.lang.String ou java.lang.String por extenso como em:

    String name;

    //
    // =========================================================================
    //
    // Subpacotes e estrutura de diretórios
    //
    // Pacotes são usados pela JVM como uma maneira de encontrar as classes
    // no sistema de arquivos, logo a estrutura de diretórios do projeto deve ser a
    // mesma da estrutura de pacotes.
    //
    // O arquivo Person.java deve estar localizado dentro do diretório
    // model , que deve estar dentro do diretorio project , isto é, em:
    //
    // cap03_basico_java/model/Person.java
    //
    // Dizemos que model é um subpacote de cap03_basico_java , já que está dentro
    // dele. Usamos o caractere . como separador de pacotes e subpacotes.
    //
    // ===========================================================================
    //
    // import static
    //

    public static void main(String[] args) {
	int x = AGE;
	method1();
	method1(x);
    }
}
