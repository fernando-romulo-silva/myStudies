package br.com.nandao.cap08MaisOperacoesStreams.part03;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.nandao.Usuario;

// Qual é a vantagem dos métodos serem lazy?
public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);
    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);
    static final Usuario user3 = new Usuario("Guilherme Silveira", 90);

    static final List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

    public static void main(String[] args) {

        // Imagine que queremos encontrar um usuário com mais de 100 pontos. Basta um e
        // serve qualquer um, desde que cumpra o predicado de ter mais de 100 pontos.

        final Usuario maisDe100 = usuarios.stream() //
            .filter(u -> u.getPontos() > 100) //
            .collect(Collectors.toList()) //
            .get(0);

        // É muito trabalho para algo simples: aqui filtramos todos os usuários e criamos
        // uma nova coleção com todos eles apenas para pegar o primeiro elemento. Além
        // disso, no caso de não haver nenhum usuário com mais de 100 pontos, receberemos
        // uma exception. O Stream possui o método findAny que devolve qualquer um dos elementos:

        final Optional<Usuario> usuarioOptional = usuarios.stream() //
            .filter(u -> u.getPontos() > 100) //
            .findAny(); //
        
        // Há duas vantagens aqui em trabalhar com Stream. A primeira é que o
        // findAny devolve um Optional<Usuario> e com isso somos obrigados a fazer
        // um get para receber o Usuario, ou usar os métodos de teste (como orElse e isPresent)
        // para saberse realmente há um usuário filtrado aí. A segunda vantagem
        // é que, como todo o trabalho foi lazy, o stream não foi inteiramente filtrado.
        //
        // O findAny é uma operação terminal e forçou a execução do pipeline
        // de operações. Ao mesmo tempo, esse método é escrito de maneira inteligente,
        // analisando as operações invocadas anteriormente e percebendo que não precisa filtrar
        // todos os elementos da lista para pegar apenas um deles que cumpra o predicado
        // dado. Ele começa a executar o filtro e, assim que encontrar um usuário com mais de
        // 100 pontos, retorna-o e termina a filtragem ali mesmo. Outro método que pode ser
        // útil é o findFirst, similar ao findAny, mas utilizando os elementos na ordem percorrida pelo stream.
        //
        // Essa técnica de otimização nem sempre é possível de ser aplicada, dependendo
        // do pipeline de operações. Portanto, você não deve ter isso como garantia para escrever
        // código possivelmente ineficiente.
    }

}
