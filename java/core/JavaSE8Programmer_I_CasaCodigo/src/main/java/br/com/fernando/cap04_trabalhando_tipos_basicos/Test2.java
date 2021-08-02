package br.com.fernando.cap04_trabalhando_tipos_basicos;

// Diferença entre variáveis de referências a objetos e tipos primitivos
public class Test2 {

    class Car {

        int age;
    }

    public void method01() {
        // As variáveis de tipos primitivos de fato armazenam os valores (e não ponteiros/ referências).
        int a = 10;
        int b = a; // copiando o valor de a para b
        b++; // somando 1 em b
        System.out.println(a); // continua com 10.
        //
        // Uma variável de referência é um ponteiro para o endereço de memória
        // onde o objeto se encontra. Ao atribuirmos uma variável de referência a outra,
        // estamos copiando a referência, ou seja, fazendo com que as duas variáveis
        // apontem para o mesmo objeto, e não criando um novo objeto:
        Car c = new Car();
        Car d = c; // agora b aponta para o mesmo objeto de a
        c.age = 5;
        System.out.println(d.age); // imprime 5

        // Duas referências são consideradas iguais somente se elas estão apontando
        // para o mesmo objeto. Mesmo que os objetos que elas apontem sejam iguais,
        // ainda são referências para objetos diferentes:

        Car a1 = new Car();
        a1.age = 5;
        Car b1 = new Car();
        b1.age = 5;
        Object c1 = a;
        System.out.println(a1 == b1); // false
        System.out.println(a1 == c1); // true
    }

}
