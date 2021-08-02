// podem exister comentarios antes do pacotes
// no package => package "default"
package br.com.fernando.cap03_basico_java;

// Defina a estrutura de uma classe Java
public class Test2 {

    // Pacotes : Pacotes servem para separar e organizar as diversas classes que temos em nossos sistemas
    //
    // Classe: Uma classe é a forma no Java onde definimos os atributos e comportamentos de um objeto.
    // A declaração de uma classe pode ser bem simples, apenas a palavra class seguida do nome e de {}
    //
    // Variaveis: sempre o tipo seguido do nome da variável.
    //
    // Métodos: O metodo tem tipo do retorno, seguido do nome do método e seguido de parênteses, sendo
    // que pode ou não haver parâmetros de entrada desse método
    //
    // Construtores: Um construtor não tem retorno e possui o mesmo nome da classe.
    class Person {

	int j = -100;

	String firstName;

	String lastName;

	// Podemos ter membros de tipos diferentes com o mesmo nome. Fique
	// atento, o código a seguir compila normalmente:
	String b;

	String b() {
	    return null;
	}

	Person(int i) { // constructor
	    // Note que um construtor pode ter um return vazio:

	    if (i > 1)
		return;

	    j = i;
	}

	void Person() { // method
	}

	Person(String firstname, String lastname) {
	    this.firstName = firstname;
	    this.lastName = lastname;
	}

	public String getFullName() {
	    return this.firstName + this.lastName;
	}

    }
    // ==========================================================================
    // Interfaces

    interface Authenticable {

	final int PASSWORD_LENGTH = 8;

	void authenticate(String login, String password);
    }

    // ==========================================================================

    // Multíplas estruturas em um arquivo
    // Em java, é possível definir mais de uma classe/interface em um mesmo
    // arquivo java, embora devamos seguir algumas regras:
    //
    // • Podem ser definidos em qualquer ordem;
    //
    // • Se existir alguma classe/interface pública, o nome do arquivo deve ser
    // o mesmo dessa classe/interface;
    //
    // • Só pode existir uma classe/interface pública por arquivo;
    //
    // • Se não houver nenhuma classe/interface pública, o arquivo pode ter
    // qualquer nome.

    // ==========================================================================

    public static void main(String[] args) {

    }
}
