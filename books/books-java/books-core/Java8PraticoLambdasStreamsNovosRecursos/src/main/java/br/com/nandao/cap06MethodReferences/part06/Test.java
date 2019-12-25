package br.com.nandao.cap06MethodReferences.part06;

import java.util.function.BiFunction;
import java.util.function.Function;

import br.com.nandao.Usuario;

// Referenciando construtores
public class Test {

    public static void main(String[] args) {

        // Assim como métodos estáticos, podemos usar method reference com construtores.
        // Por sinal, é comum ouvir esse tipo de referência ser chamada de constructorreference.
        // Neste caso, usamos o new para indicar que queremos referenciar um construtor,
        // desta forma:

        // final Usuario rodrigo = Usuario::new

        // Claro, afinal, assim como as expressões lambda, precisamos guardar o resultado
        // dessa referência em uma interface funcional!

        // Vamos utilizar a interface Supplier, também presente no pacote java.util.function
        //
        // final Supplier<Usuario> criadorDeUsuarios = Usuario::new;
        // final Usuario novo = criadorDeUsuarios.get();
        //
        // Utilizamos um Supplier sempre para criar um novo objeto a partir de seu
        // construtor default. Caso queiramos criar a partir de algum construtor com argumento
        // da classe, precisaremos utilizar alguma outra interface funcional.
        //
        // Precisamos de uma interface funcional que receba tanto o que será criado, que
        // neste caso é o tipo Usuario, como também qual argumento será passado para o
        // construtor! Podemos usar a interface que vimos no capítulo anterior, a Function!

        final Function<String, Usuario> criadorDeUsuarios = Usuario::new;
        final Usuario novo = criadorDeUsuarios.apply("Dom Pedro I");

        // Mas e se quisermos criar um usuário usando o construtor de dois parâmetros?

        final BiFunction<String, Integer, Usuario> criadorDeUsuarios2 = Usuario::new;
        final Usuario rodrigo = criadorDeUsuarios2.apply("Rodrigo Turini", 50);

    }
}
