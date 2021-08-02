package br.com.fernando.cap06_Declare_instancie_inicialize_e_use_array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Declare e use uma ArrayList
public class Test03 {

    public static void test01() {

	// Para declarar um arraylist
	final ArrayList<String> names1 = new ArrayList<String>();

	// Por exemplo, para adicionar itens, fazemos:
	ArrayList<String> names2 = new ArrayList<String>();
	names2.add("certification");
	names2.add("java");

	// Para remover e verificar a existência do mesmo na lista:

	System.out.println(names2.contains("java")); // true
	System.out.println(names2.contains("c#")); // false

	// true, encontrado e removido
	boolean removed = names2.remove("java");

	System.out.println(names2.contains("java")); // false
	System.out.println(names2.contains("c#")); // false

	System.out.println(names2.size()); // 1

	names2.add("java"); // add java again

	// E convertê-la para um array:
	Object[] objectArray = names2.toArray();

	// caso desejarmos um array de String, devermos inidcar isso ao metodo toArray:
	String[] names3 = names2.toArray(new String[0]);
	String[] names4 = names2.toArray(new String[names2.size()]);

	// Ambas passam um array de String: o primeiro menor e o segundo com o
	// tamanho suficiente para os elementos. Se ele possui o tamanho suficiente, ele
	// mesmo será usado, enquanto que, se o tamanho não é suficiente, o toArray
	// cria um novo array do mesmo tipo.

	//
	// Além disso, podemos adicionar uma coleção inteira em outra:

	ArrayList<String> countries = new ArrayList<String>();
	countries.add("korea");
	countries.add("brazil");

	ArrayList<String> everything = new ArrayList<String>();
	everything.addAll(names2);
	everything.addAll(countries);

	System.out.println(everything.size());

	// O método get devolve o elemento na posição desejada, lembrando que começamos sempre com 0:
	System.out.println(names2.get(0)); // certification

	// Já o método add foi sobrecarregado para receber a posição de inclusão:
	names2.add(2, "groovy");

	// E o método set , que serve para alterar o elemento em determinada posição:
	names2.set(0, "certification");

	// Os métodos indexOf e lastIndexOf retornam a primeira ou a última
	// posição que possui o elemento desejado. Caso esse elemento não esteja na
	// lista, ele retorna -1:

	ArrayList<String> names5 = new ArrayList<String>();
	names5.add("guilherme");
	names5.add("mario");
	names5.add("paulo");
	names5.add("mauricio");
	names5.add("adriano");
	names5.add("alberto");
	names5.add("mario");
	System.out.println(names5.indexOf("guilherme")); // 0
	System.out.println(names5.indexOf("mario")); // 1
	System.out.println(names5.indexOf("john")); // -1
	System.out.println(names5.lastIndexOf("mario")); // 6
	System.out.println(names5.lastIndexOf("john")); // -1

	// Iterator e o enhanced for
	// A interface Iterator define uma maneira de percorrer coleções. Isso é
	// necessário porque, em coleções diferentes de List , não possuímos métodos
	// para pegar o enésimo elemento. Como, então, percorrer todos os elementos de uma coleção?
	//
	// • hasNext : retorna um booleano indicando se ainda há elementos a serem percorridos por esse iterador;
	// • next : pula para o próximo elemento, devolvendo-o;
	// • remove : remove o elemento atual da coleção.

	// O código que costuma aparecer para percorrer uma coleção é o seguinte:

	Collection<String> strings = new ArrayList<String>();

	Iterator<String> iterator = strings.iterator();

	while (iterator.hasNext()) {
	    String current = iterator.next();
	    System.out.println(current);
	}

	// também pode ser usado nesse caso:
	for (String current : strings) {
	    System.out.println(current);
	}

	//
	// O método equals em coleções

	// A maioria absoluta das coleções usa o método equals na hora de buscar
	// por elementos, como nos métodos contains e remove . Se você deseja ser
	// capaz de remover ou buscar elementos, terá que provavelmente sobrescrever o
	// método equals para refletir o conceito de igualdade em que está interessado,
	// e não somente a igualdade de referência (implementação padrão do método).
    }

}
