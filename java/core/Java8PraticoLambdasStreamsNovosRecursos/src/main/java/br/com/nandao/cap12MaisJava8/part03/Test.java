package br.com.nandao.cap12MaisJava8.part03;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import br.com.nandao.Usuario;

// Limitações da inferência no lambda
public class Test {

    static final Usuario user1 = new Usuario("Paulo Silveira", 150);

    static final Usuario user2 = new Usuario("Rodrigo Turini", 120);

    static final Usuario user3 = new Usuario("Guilherme Silveira", 190);

    static final List<Usuario> usuarios1 = Arrays.asList(user1, user2, user3);

    public static void test1() {

	// Talvez você vá se deparar com esses problemas bem mais pra frente, mas é importante
	// ter conhecimento de que eles existem: algumas vezes o compilador não consegue
	// inferir os tipos com total facilidade, em especial quando encadeamos métodos
	// que utilizam lambda.

	usuarios1.sort( //
	               Comparator.comparingInt(Usuario::getPontos) //
	               .thenComparing(Usuario::getNome) //
	              );

	// Porém, usando os lambdas que parecem equivalentes, não compila:
	// usuarios1.sort(
	//                Comparator.comparingInt(u -> u.getPontos()) //
	//               .thenComparing(u -> u.getNome()) //
	//              );
	//
	// Se avisarmos que o u é um Usuario, o retorno do comparingInt fica mais
	// óbvio para o compilador, e com o código a seguir conseguimos uma compilação 
	// sem erros:
	// 
	usuarios1.sort( //
	                Comparator.comparingInt((Usuario u) -> u.getPontos()) //
	                .thenComparing(u -> u.getNome()) //
	              );
	// 
	// Outra forma seria quebrar a declaração do Comparator, perdendo a interface fluente:
	final Comparator<Usuario> comparator = Comparator.comparing(u -> u.getPontos());
	usuarios1.sort(comparator.thenComparing(u -> u.getNome()));
	
	// Repare que tudo funcionou perfeitamente quando utilizamos o method reference,
	// já que com eles o compilador pode facilmente dizer quais são os tipos envolvidos.
	// O mesmo acontece para casos com encadeamentos de interfaces fluentes mais
	// simples. Você pode fazer, como visto:
	//
	usuarios1.sort(Comparator.comparingInt(Usuario::getPontos).reversed());
	//
	// Mas se usar o lambda em vez do method reference, não compila:
	//
	// usuarios1.sort(Comparator.comparingInt(u -> u.getPontos()).reversed());
	//
	// Precisaria explicitar os tipos genéricos ou o tipo no lambda. Ou ainda declarar o
	// Comparator antes e só depois invocar o reversed. Como utilizamos com mais
	// frequência o method reference nesses encadeamentos, esses serão casos raros	
    }
    
    // Suporte a múltiplas anotações iguais
    public static void test2() {
	// Um detalhe que pode ser bastante util em nosso dia a dia é a capacidade de aplicar
	// a mesma anotação repetidas vezes em um mesmo tipo. Essa possibilidade, conhecida
	// como repeating annotations, foi adicionada na nova versão da linguagem!
	// Considere que temos a anotação @Role para determinar o tipo de acesso permitido
	// para uma classe
	//
	// Para adicionar duas 'roles' em uma mesma classe nas versões pré-Java 8 teríamos
	// que modificar sua estrutura pra receber um array de Strings, ou qualquer outro
	// tipo que represente uma regra de acesso de seu sistema.
	//
	// Para tornar isso possível agora podemos marcar a nossa anotação com
	// @Repeatable, passando como argumento uma outra anotação que servirá para
	// armazenar as anotações repetidas.
	//
	// Aplicamos as anotaçõesrepetidas em uma classe neste exemplo, porém o mesmo
	// pode ser feito em qualquer lugar passível de utilizar uma anotação.
	// Diversos métodos foram adicionados na API de reflection para recuperar essas
	// anotações. Um exemplo é o método getAnnotationsByType que recebe a classe
	// da anotação procurada como parâmetro:
	
	final RelatorioController controller = new RelatorioController();
	final Role[] annotationsByType = controller.getClass().getAnnotationsByType(Role.class);
	
	Arrays.asList(annotationsByType).forEach(a -> System.out.println(a.value()));
    }
    
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
    public @interface Roles {
	Role[] value();
    }    
    
    @Repeatable(Roles.class)
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
    public @interface Role {
   	String value();
    }
    
    @Role("presidente")
    @Role("diretor")
    public static class RelatorioController {
    }

    public static void main(String[] args) {

    }

}
