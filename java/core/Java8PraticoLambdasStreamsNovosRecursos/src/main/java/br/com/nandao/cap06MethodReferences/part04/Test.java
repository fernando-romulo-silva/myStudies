package br.com.nandao.cap06MethodReferences.part04;

import java.util.function.Consumer;

import br.com.nandao.Usuario;

// Referenciando métodos de instância
public class Test {

    public static void main(String[] args) {
        
        // Dada uma referência a um objeto, podemos criar um method reference que invoque
        // um de seus métodos:

        final Usuario rodrigo = new Usuario("Rodrigo Turini", 50);
        final Runnable bloco = rodrigo::tornaModerador;
        bloco.run();

        // A invocação bloco.run() vai fazer com que rodrigo.tornaModerador()
        // seja invocado. Para ficar mais nítido, os dois blocos a seguir são equivalentes:
        final Runnable bloco1 = rodrigo::tornaModerador;
        final Runnable bloco2 = () -> rodrigo.tornaModerador();

        // Repare que isso é bastante diferente de quando fazemos
        // Usuario::tornaModerador, pois estamos referenciando o método do meu
        // usuário existente, e não de qualquer objeto do tipo Usuario.

        // No caso do Usuario::tornaModerador, o compilador pode inferir o lambda
        // para Consumer<Usuario> pois, apesar de tornaModerador não receber um
        // argumento, é necessário saber de qual usuário estamos falando. Veja:

        final Consumer<Usuario> consumer = Usuario::tornaModerador;
        consumer.accept(rodrigo);

        // O efeito é o mesmo, porém aqui precisamos
        // passar o argumento. Novamente para reforçar, veja que os dois consumidores
        // a seguir são equivalentes, um usando method reference e o outro usando lambda:

        final Consumer<Usuario> consumer1 = Usuario::tornaModerador;
        final Consumer<Usuario> consumer2 = u -> u.tornaModerador();

        // O que não pode é misturar expectativas de número e tipos diferentes dos argumentos
        // e retorno! Por exemplo, o Java não vai aceitar fazer
        //
        // Runnable consumer = Usuario::tornaModerador,
        //
        // pois esse method reference só pode ser atribuído
        // a uma interface funcional que necessariamente receba um parâmetro em seu método
        // abstrado, que seria utilizado na invocação de tornaModerador.
    }

}
