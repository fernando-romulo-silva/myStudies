package br.com.nandao.cap06MethodReferences.part07;

import java.util.function.BiFunction;
import java.util.function.IntBinaryOperator;
import java.util.function.ToIntBiFunction;

// Outros tipos de referências
public class Test {

    // Novamente o cuidado com o boxing
    public static void test1() {
        // A atenção ao auto boxing desnecessário é constante. Assim como vimos no caso
        // da Function, a BiFunction possuisuasinterfaces análogas para tipos primitivos.
        
        // Eh possivel acessar metodos estaticos como por exemplo: Math::max
        final BiFunction<Integer, Integer, Integer> max = Math::max;
        
        final ToIntBiFunction<Integer, Integer> max2 = Math::max;

        // Esta e' a mais interessante, mas vai depender
        // do método que receberá nosso lambda como argumento.
        final IntBinaryOperator max3 = Math::max;
    }
    
    public static void main(String[] args) {

    }

}
