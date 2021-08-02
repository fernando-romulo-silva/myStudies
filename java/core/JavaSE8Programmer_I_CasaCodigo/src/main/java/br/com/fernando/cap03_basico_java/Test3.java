package br.com.fernando.cap03_basico_java;

// Crie aplicações Java executáveis com um método main, rode um programa Java na linha de comando
public class Test3 {
    // ==========================================================================
    // Método main
    // • Ser público ( public );
    // • Ser estático ( static );
    // • Não ter retorno ( void );
    // • Ter o nome main;
    // • Receber como parâmetro um String[] ou String... ).
    //
    // Metodos main validos
    //
    // parameter: array
    // public static void main (String[] args) {}
    //
    // parameter: varargs
    // public static void main (String... args) {}
    //
    // static public/public static are ok
    // static public void main(String[] args) {}
    //
    // parameter name does not matter
    // public static void main (String... listOfArgumentsOurUserSentUs){}
    //
    // parameter: array variation
    // public static void main(String args[]) {}
    //
    // ==========================================================================
    // Executando uma classe pela linha de comando
    //
    // Compilamos e executamos no console com os seguintes comandos:
    // $ javac HelloWorld.java
    // $
    // $ java HelloWorld
    // Hello World!
    // ==========================================================================
    //
    // Compilação e execução
    //
    // Para criar um programa java, é preciso escrever um código-fonte e, através
    // de um compilador, gerar o executável (bytecode). O compilador do JDK (Java Development Kit) é o javac.
    //
    // A execução do bytecode é feita pela JVM (Java Virtual Machine). O comando java
    // invoca a máquina virtual para executar um programa java. Ao baixarmos o Java,
    // podemos escolher baixar o JDK, que já vem com o JRE, ou somente o JRE (Java Runtime Environment),
    // que inclui a Virtual Machine.
    //
    // $ javac certification/Exam.java
    //
    // Nesse exemplo, o arquivo Exam.class é colocado no diretório
    // certification , junto com seu fonte, o Exam.java .
    //
    // ==========================================================================
    //
    // Propriedades na linha de comando
    // 
    // A prova ainda cobra conhecimentos sobre como executar um programa
    // java passando parâmetros ou propriedades para a JVM e essas propriedades
    // são identificadas pelo -D antes delas. Este -D não faz parte da chave.
    //
    // java -key1=abc -Dkey2=def Foo xpto bar
    //
    // key1=abc e key2=def são parâmetros/propriedades e xpto e bar são argumentos recebidos pelo método main .
    //
    // ==========================================================================
    //
    // Configurando o classpath
    //
    // 1) Configurando a variável de ambiente CLASSPATH no sistema operacional.
    // Basta seguir as opções do SO em questão e definir a variável. Isso é con-
    // siderado uma má prática no dia a dia porque é um classpath global, que vai
    // valer para qualquer programa java executado na máquina. 
    //
    // 2) Com as opções -cp ou -classpath dos comandos javac ou java .
    //
    // $ javac -cp /path/to/library.jar Test.java
    // $ java -cp /path/to/library.jar Test
    //
    // Para passar mais de uma coisa no classpath, usamos o separador de parâmetros 
    // no SO (no Windows é ponto e vírgula, no Linux/Mac/Solaris/Unixsão dois pontos):
    //
    // $ javac -cp /path/to/library.jar;/another/path/certification/Test.java
    //
    // $ java -cp /path/to/library.jar;/another/path/certification.Test

    // ==========================================================================
    public static void main(String[] args) {
	// reading the first (0) position
	String arg = args[0];
	System.out.println("Hello " + arg + "!");
    }
}
