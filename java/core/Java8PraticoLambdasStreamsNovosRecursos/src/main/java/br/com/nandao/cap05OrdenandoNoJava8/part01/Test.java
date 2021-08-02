package br.com.nandao.cap05OrdenandoNoJava8.part01;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.nandao.Usuario;

// Comparators como lambda
public class Test {

    public static void test1() {
        // Comparator normal
        final Comparator<Usuario> comparator1 = new Comparator<Usuario>() {
            @Override
            public int compare(final Usuario u1, final Usuario u2) {
                return u1.getNome().compareTo(u2.getNome());
            }
        };

        final Usuario user1 = new Usuario("Paulo Silveira", 150);
        final Usuario user2 = new Usuario("Rodrigo Turini", 120);
        final Usuario user3 = new Usuario("Guilherme Silveira", 190);

        final List<Usuario> usuarios1 = Arrays.asList(user1, user2, user3);

        Collections.sort(usuarios1, comparator1);

        //
        //
        final List<Usuario> usuarios2 = Arrays.asList(user1, user2, user3);

        // Podemos tirar proveito do lambda e reescrever a instanciação daquela classe anônima de maneira mais simples:
        final Comparator<Usuario> comparator2 = (u1, u2) -> u1.getNome().compareTo(u2.getNome());

        Collections.sort(usuarios2, comparator2);
        //
        //

        final List<Usuario> usuarios3 = Arrays.asList(user1, user2, user3);
        // Ou ainda, colocando tudo em uma mesma linha, sem a declaração da variável local:
        Collections.sort(usuarios3, (u1, u2) -> u1.getNome().compareTo(u2.getNome()));

        //
        // Os cuidados com comparators
        // Atenção! Para deixar o código maissucinto, não nos precavemos aqui
        // de possíveis usuários com atributo nome igual a null. Mesmo sendo
        // uma invariante do seu sistema, é importante sempre checar esses casos
        // particulares e definir se um usuário com nome nulo iria para o começo ou fim nesse critério de comparação.
        // Há também a boa prática de utilizar comparators que já existem String.CASE_INSENSITIVE_ORDER. Seu código ficaria return:
        // 
        // String.CASE_INSENSITIVE_ORDER.compare(u1.getNome(), u2.getNome())
        //
        // ou ainda algum dos java.text.Collactors, junto com as verificações de valores nulo.
    }

    public static void main(final String[] args) {
        test1();
    }

}
