package br.com.nandao.cap07StreamsAndCollectors.part06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.nandao.Usuario;

// Liste apenas os pontos de todos os usuários com o map
public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 90);

    static final List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

    public static void main(String[] args) {

        final List<Integer> pontos1 = new ArrayList<>();

        // Com o seguinte código, não muito diferente do que faríamos com as versões pré-Java 8,
        // conseguimos extrair uma lista com a pontuação de todos os usuários:

        usuarios.forEach(u -> pontos1.add(u.getPontos()));
        
        System.out.println();
        System.out.println(pontos1);

        // Mas repare que é preciso criar uma variável intermediária com a lista, e adicionar
        // os pontos manualmente. Ainda não estamos tirando muito proveito da nova API
        // do Java 8! Além disso, o nosso lambda está causando efeitos colaterais: alterando
        // o estado de variáveis e objetos fora de seu escopo (variavel pontos1).
        //
        // Há uma forma bem mais interessante e conhecida de quem já tem algum conhecimento
        // de programação funcional: o map.
        // Utilizando o método map da API de Stream, conseguimos obter o mesmo resultado
        // aplicando uma transformação em minha lista sem a necessidade de variáveis
        // intermediárias! Repare:

        final List<Integer> pontos2 = usuarios.stream()//
            .map(Usuario::getPontos)//
            .collect(Collectors.toList());
        
        System.out.println();
        System.out.println(pontos2);
        //
        // Repare que nem entramos em detalhes para dizer o que map recebe como argumento.
        // Ele trabalha com Function, que é uma interface funcional. No nosso caso,
        // dado um Usuario ele precisa devolver um Integer, definido pelo seu único 
        // método abstrato, o apply. É uma Function<Usuario, Integer>. Perceba que
        // não foi necessário saber exatamente os argumentos, retorno e nome do método para
        // lermos o código que usa o map, já que o lambda ajuda na legibilidade.
    } 

}
