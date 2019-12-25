package br.com.fernando.cap04_trabalhando_tipos_basicos;

// Leia ou escreva para campos de objetos
public class Test3 {

    class Car {

        String model;

        int year;

        public Car() {
            model = "???"; // acessando variável de
            // instancia sem o this
            this.year = 2014; // acessando com o this
        }

        public String getData() {
            return model + " - " + year;
        }

        public void setModel(String m) {
            this.model = m;
        }
    }

    // Para acessar um atributo, usamos o operador . (ponto),
    public void method01() {
        Car a = new Car();
        a.model = "Ferrari"; // A. acessando diretamente o atributo
        a.setModel("Ferrari"); // B. acessando o atributo por um método
        // acessando o método e passando o retorno como argumento para
        // o método println
        System.out.println(a.getData());

        // Quando estamos dentro da classe, não precisamos de nenhum operador
        // para acessar os atributos de instância da classe. Opcionalmente, podemos
        // usar a palavra-chave this, que serve como uma variável de referência para
        // o próprio objeto onde o código está sendo executado
    }

}
