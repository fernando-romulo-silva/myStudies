package br.com.fernando.cap08_trabalhando_com_metodos_e_encapsulamento;

// Crie métodos sobrecarregados
public class Test03 {

    // Um método pode ter o mesmo nome que outro, desde que a sua invocação
    // não fique ambígua: os argumentos que são recebidos têm de ser obrigatoriamente
    // diferentes, seja em quantidade ou em tipos como mostra o código a seguir.
    public void method(int i) {
	System.out.println("int");
    }

    protected void method(double x) {
	System.out.println("double");
    }

    // Já o código a seguir não compila:
    public int method() {
	return 0;
    }
    // protected double method() { return 0; } // compile error

    // Métodos sobrecarregados podem ter ou não um retorno diferente e uma
    // visibilidade diferente. Mas eles não podem ter exatamente os mesmos tipos e
    // quantidade de parâmetros. Nesse caso, seria uma sobrescrita de método.

    void method(Object o) {
	System.out.println("object");
    }

    void method(String s) {
	System.out.println("string");
    }

    void method2(int i, double x) { // ok
    }

    void method2(double x, int i) { // ok
    }

    void method3(Object o, String s) {
	System.out.println("object");
    }

    void method3(String s, Object o) {
	System.out.println("string");
    }

    void method4(Object o, Object o2) {
	System.out.println("object");
    }

    void method4(String s, String s2) {
	System.out.println("string");
    }

    public static void test01() {
	new Test03().method(15); // int
	new Test03().method(15.0); // double

	// No caso de sobrecarga com tipos que possuem polimorfismo, como em
	// Object ou String , o compilador sempre invoca o método com o tipo mais
	// específico (o menos genérico):

	new Test03().method("random"); // string

	// Se quisermos forçar a invocação ao método mais genérico, devemos fazer o casting forçado:

	String x = "random";
	Object y = x;

	new Test03().method(y); // object

	new Test03().method2(2.0, 3); // double, int
	new Test03().method2(2, 3.0); // int, double

	// No caso a seguir, os números 2 e 3 podem ser considerados tanto int quanto double ,
	// portanto, o compilador fica perdido em qual dos dois métodos invocar, e decide não compilar:
	// new Test03().method2(2, 3); // compile error

	// Isso também ocorre com referências, que é diferente do caso com tipo
	// mais específico. Aqui não há tipo mais específico, pois onde um é mais específico,
	// o outro é mais genérico:
	// new Test03().method3("string", "string"); // compile error
	//
	// Note como ele é totalmente diferente do caso da regra do mais específico:
	new Test03().method4("string", "string"); // string
    }

}
