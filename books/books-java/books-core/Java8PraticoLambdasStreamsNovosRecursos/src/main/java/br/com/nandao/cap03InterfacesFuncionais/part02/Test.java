package br.com.nandao.cap03InterfacesFuncionais.part02;

// Sua própria interface funcional
// Você não precisa fazer nada de especial para que uma interface seja considerada
// funcional. O compilador já identifica esse tipo de interface pela sua estrutura

interface Validador<T> {
    boolean valida(T t);
}

public class Test {

    public static void test1() {
        // Costumamos utilizá-la criando uma classe anônima, desta forma:
        final Validador<String> validadorCEP1 = new Validador<String>() {
            @Override
            public boolean valida(String valor) {
                return valor.matches("[0-9]{5}-[0-9]{3}");
            }
        };

        // Costumamos utilizá-la criando uma classe anônima, desta forma:
        final Validador<String> validadorCEP2 = valor -> {
            return valor.matches("[0-9]{5}-[0-9]{3}");
        };

        // Nosso lambda está um pouco grande. Vimos mais de uma vez que, quando há
        // uma única instrução, podemos resumi-la. E isso acontece mesmo se ela for uma
        // instrução de return! Podemos remover o próprio return, assim como o seu
        // ponto e vírgula e as chaves delimitadoras

        final Validador<String> validadorCEP = valor -> valor.matches("[0-9]{5}-[0-9]{3}");
    }

    public static void main(String[] args) {

    }

}
