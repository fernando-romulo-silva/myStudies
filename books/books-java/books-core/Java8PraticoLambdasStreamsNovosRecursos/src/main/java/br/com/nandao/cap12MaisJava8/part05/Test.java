package br.com.nandao.cap12MaisJava8.part05;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

import br.com.nandao.Usuario;

// Reflection: parameter names
public class Test {

    public static void main(String[] args) throws Exception {

	// A API de reflection também recebeu melhorias. Uma delas foi a tão esperada habilidade
	// de recuperar o nome dos parâmetros dos métodos e construtores.
	// Isso agora pode ser feito de forma muito simples: basta invocarmos o método
	// getParameters de um método ou construtor. Podemos fazer isso com o seguinte
	// construtor da classe Usuario:

	final Constructor<Usuario> constructor = Usuario.class.getConstructor(String.class, int.class);

	final Parameter[] parameters = constructor.getParameters();

	Arrays.asList(parameters).forEach(param -> System.out.println(param.isNamePresent() + ": " + param.getName()));
	
	// Isso aconteceu pois precisamos passar o argumento -parameters para o compilador!
	// Apenas dessa forma ele vai manter os nomes dos parâmetros no byte code
	// para recuperarmos via reflection. Agora sim, ao recompilar a classe teremos o seguinte
	// resultado:
	// true: nome
	// true: pontos
	//
	// Antes do Java 8 isso também era possível ao compilar nosso código com a opção -g (debug), 
	// mas o problema é que neste caso precisamos manipular o byte code das classes e isso pode ser bastante 
	// complicado. Existem alternativas para simplificar esse trabalho, como é o caso da biblioteca Paranamer. 
	// Repare em um exemplo de seu uso:
	
	final Paranamer paranamer = new CachingParanamer();
	
	final String[] parameterNames = paranamer.lookupParameterNames(constructor);
    }
}
