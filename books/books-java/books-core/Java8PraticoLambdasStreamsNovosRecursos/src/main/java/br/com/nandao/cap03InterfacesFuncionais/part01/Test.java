package br.com.nandao.cap03InterfacesFuncionais.part01;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

// Interfaces Funcionais
// O fato de essa interface ter apenas um método não foi uma coincidência, mas sim um requisito
// para que o compilador consiga traduzi-la para uma expressão lambda. Podemos
// dizer então que toda interface do Java que possui apenas um método abstrato pode
// ser instanciada como um código lambda!
// Isso vale até mesmo para as interfaces antigas, pré-Java 8

public class Test {

    public static void test1() {

        // Poderíamos ir além e fazer tudo em um único statement, com talvez um pouco menos de legibilidade:

        new Thread(() -> { //
            for (int i = 0; i <= 1000; i++) { //
                System.out.println(i); //
            } //
        }).start();
    }

    // Um outro uso bem natural de classe anônima é quando precisamos adicionar uma ação no click
    // de um objeto do tipo java.awt.Button
    public static void test2() {

        final JButton button = new JButton();

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println("evento do click acionado");
            }
        });

        // Assim como toda interface funcional, também podemos representá-la como uma expressão lambda:
        button.addActionListener((event) -> {
            System.out.println("evento do click acionado");
        });

        // Como já vimos, essa expressão pode ficar ainda mais simples, sem parênteses no único argumento e podemos também remover o {} e ; :

        button.addActionListener(event -> System.out.println("evento do click acionado"));

    }

    public static void main(String[] args) {

    }

}
