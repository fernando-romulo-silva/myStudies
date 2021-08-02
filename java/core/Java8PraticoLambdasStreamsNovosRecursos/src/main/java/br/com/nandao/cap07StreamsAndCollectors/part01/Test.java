package br.com.nandao.cap07StreamsAndCollectors.part01;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.nandao.Usuario;

public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 190);

    static final List<Usuario> usuarios1 = Arrays.asList(user1, user2, user3);

    public static void test1() {

        // Para filtrar os 10 usuários com mais pontos e torná-los moderadores, podemos agora
        // fazer o seguinte código:
        usuarios1.sort(Comparator.comparing(Usuario::getPontos).reversed());

        usuarios1 //
            .subList(0, 10) // 10 primeiros
            .forEach(Usuario::tornaModerador); // tornando moderador

        // Antes do Java 8 seria necessário fazer algo como:
        Collections.reverse(usuarios1);

        final List<Usuario> top10 = usuarios1.subList(0, 10);

        for (final Usuario usuario : top10) {
            usuario.tornaModerador();
        }
    }

    public static void main(String[] args) {
        test1();
    }

}
