package br.com.nandao.cap07StreamsAndCollectors.part03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.nandao.Usuario;

// Como obter de volta uma Lista?
public class Test {

    // O forEach é um método void. Se o forEach é void, e o filter devolve
    // Stream<T>, quem devolve uma List caso eu precise?
    // Poderíamos fazer isso manualmente: criar uma lista que vai guardar os usuários
    // que têm mais de 100 pontos e, depois de filtrar quem tem mais de 100 pontos, no
    // forEach eu adiciono esses usuários a nova coleção:

    public static void main(String[] args) {
        final Usuario user1 = new Usuario("Paulo Silveira", 150);
        final Usuario user2 = new Usuario("Rodrigo Turini", 120);
        final Usuario user3 = new Usuario("Guilherme Silveira", 90);

        final List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

        usuarios.stream().filter(u -> u.getPontos() > 100);

        final List<Usuario> maisQue100 = new ArrayList<>();

        // Podemos simplificar, tirando proveito da sintaxe do method reference aqui:
        usuarios //
            .stream() // stream
            .filter(u -> u.getPontos() > 100) // filtro
            .forEach(maisQue100::add); // adicionar em outra lista
    }

}
