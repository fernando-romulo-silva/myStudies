package br.com.nandao.cap08MaisOperacoesStreams.part01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.nandao.Usuario;

public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 90);

    static final List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

    // Ordenando um Stream
    public static void test1() {

        // Para ordenar uma lista
        usuarios.sort(Comparator.comparing(Usuario::getNome));

        // e um stream?
        usuarios.stream() // 
                .filter(u -> u.getPontos() > 100) // 
                .sorted(Comparator.comparing(Usuario::getNome));

        // No stream, o método de ordernação é o sorted. A diferença entre ordenar
        // uma lista com sort e um stream com sorted você já deve imaginar: um método 
        // invocado em Stream não altera quem o gerou. No caso, ele não altera a List<Usuario> usuarios. 
        // Se quisermos o resultado em uma List, precisamos usar um coletor, como visto:

        final List<Usuario> filtradosOrdenados = usuarios.stream() //
                .filter(u -> u.getPontos() > 100) //
                .sorted(Comparator.comparing(Usuario::getNome)) //
                .collect(Collectors.toList());

        // Este codigo no java 7 ficaria assim:
        final List<Usuario> usuariosFiltrados = new ArrayList<>();
        for (final Usuario usuario : usuarios) {
            if (usuario.getPontos() > 100) {
                usuariosFiltrados.add(usuario);
            }
        }

        Collections.sort(usuariosFiltrados, new Comparator<Usuario>() {
            @Override
            public int compare(final Usuario u1, final Usuario u2) {
                return u1.getNome().compareTo(u2.getNome());
            }
        });

    }

    public static void main(final String[] args) {

    }

}
