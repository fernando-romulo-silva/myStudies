package br.com.fernando.cap09_Trabalhando_com_heranca;

// Diferencie entre o tipo de uma referência e o tipo de um objeto
public class Test03 {

    // Se Car extends Vehicle, dizemos que Car é um Vehicle. Ou se ArrayList implements List
    // dizemos que ArrayList é um List. O relacionamento de é um é um dos recursos mais poderosos
    // da orientação a objetos. E é chamado formalmente de polimorfismo.

    class Vehicle1 {
    }

    class Car1 extends Vehicle1 {
    }

    class Motorcycle extends Vehicle1 {
    }

    class Bus1 extends Vehicle1 {
    }

    class HybridCar1 extends Car1 {
    }

    // Se temos ummétodo que recebe Vehicle, podemos passar qualquer um
    // daqueles objetos como parâmetro:

    void method(Vehicle1 v) {
    }

    void method2() {
        // Repare que, quando usamos polimorfismo, estamos mudando o tipo da
        // referência, mas nunca o tipo do objeto. Em Java, objetos nunca mudam seu
        // tipo, que é aquele onde demos new. O que fazemos é chamar (referenciar) o
        // objeto de várias formas diferentes. Chamar de várias formas.... é o polimorfismo.

        method(new Car1());
        method(new Motorcycle());
        method(new Bus1());
        method(new Vehicle1());
        method(new HybridCar1());
    }

    // Dessa forma, conseguimos obter um forte reaproveitamento de código.

    static interface A {
    }

    static interface B {
    }

    static class C implements A {
    }

    static class D extends C implements B {
    }

    public static void test02() {
        // D d2 = new C(); // compile error, C não é D
        D d3 = new D();

        C c3 = d3; // compila

        // D d4 = c3; // não compila, por mais que o ser humano saiba que sim, em execução, nem todo C é um D.
    }

    // E como funciona o acesso às variáveis membro e aos métodos?
    // Se temos uma referência para a classe mãe, não importa o que o valor seja em tempo
    // de execução, o compilador não conhece o tempo de execução, então ele só compila chamadas
    // aos métodos definidos na classe mãe

    static abstract class Vehicle2 {

        int speed;

        public void turnon() {
        }
    }

    static class Car2 extends Vehicle2 {

        int gear;

        public void turnoff() {
        }
    }

    static class Motorbike2 extends Vehicle2 {

        public void turnoff() {
        }
    }

    public static void test03() {
        Car2 c = new Car2();
        c.turnoff(); // ok
        Vehicle2 v2 = c;
        // v2.turnoff(); // vehicle.turnoff??? compile error

        // O mesmo valerá para variáveis membro:
        c.gear = 1; // ok
        c.speed = 2; // ok

        v2.speed = 5; // ok
        // v2.gear = 7; // vehicle.gear????? compile error
    }

    public static class Account {

        private void open() {
            System.out.println("open base account");
        }

        void close() {
            System.out.println("closing base account");
        }
    }

    public static class SavingsAccount extends Account {

        public void open() {
            System.out.println("open base account");
        }

        void close() {
            System.out.println("closing savings account");
        }
    }

    public static void test04() {
        //

        // O binding é feito para o método específico de cada uma delas, uma vez
        // que são métodos totalmente diferentes. Se o código está no pacote demodelo,
        // a chamada aométodo close de Account compila e imprimiria closing base account,
        // já a chamada aométodo close da referência SavingsAccounts não compila
        //
        // Lembre-se que os métodos privados terão um efeito equivalente: eles só são vistos 
        // internamente à classe onde foram definidos.
    }

    //
}
