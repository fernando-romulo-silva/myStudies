package br.com.fernando.chapter16_jndi_resources.part01_jndi_basic;

import static br.com.fernando.Util.APP_FILE_TARGET;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.io.File;
import java.rmi.Remote;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.myembedded.pack.EmbeddedEar;
import org.myembedded.pack.EmbeddedEjb;
import org.myembedded.pack.JeeVersion;

public class Part01 {

    // It is an API( Application Program Interface) that works with servers and can fetch files from a database using naming conventions.
    // The naming convention can be a single phrase or a word.
    // JNDI provides users in Java the facility to search objects in Java using the Java coding language.
    //
    /**
     * <pre>
     * 
     *          Java Application 
     *                 |
     *                 V
     *              JNDI API
     *                 |
     *                 V
     *           Naming Manager
     *                 |              
     *                 V
     *             JNDI SPI
     *                 |
     *                 V
     *    LDAP  DNS  NIS NDS RMI CORBA HTTP ...
     * 
     * </pre>
     * 
     */
    // Pode-se usar JNDI para mapear nomes a objetos
    // * Objetos localizáveis por nome podem ser abstraídos do contexto ou até de outra linguagens em que são usados
    // * Aplicações diferentes podem compartilhar objetos
    //
    // ================================================================================================================================================
    // Also, JNDI plays a major role in three of the latest Java technologies. They are:
    //
    // JDBC (The Java Database Connectivity package
    // JMS (The Java Messaging Service)
    // EJB( Enterprise Java Beans)
    // Other objects
    public static void test01() throws NamingException {
	// There is a bind() and look up() in Java programming language and is used in naming an object and looking up an object from the directory.
	final Context context = new InitialContext();
	context.bind("text01", "My name is name");

	// Here the name can be assigned any name to the current object in the directory.
	// This is an example of the bind function where the object’s name is set.
	Object text01 = context.lookup("text01");

	System.out.println("Text01 is " + text01);

	// In this function the object hello looks for the name of the object in the directory.
	// There are also variations of serialized or non serialized data used as the kind of directory supports.
    }

    // ================================================================================================================================================
    // The javax. naming package contains the core interfaces, classes, and exceptions for performing naming operations with JNDI.
    //
    // * Context: interface onde se pode recuperar, ligar, desligar e renomear objetos, e criar e destruir contextos
    //
    // * InitialContext: ponto de partida (raiz) para todas as operações
    //
    // * Name: abstração de um nome. Contém geralmente um String de texto que corresponde ao nome do objeto ou contexto
    //
    // * NameClassPair: contém nome do objeto e de sua classe
    //
    // * Binding: contém nome do objeto ligado, nome da classe do objeto e o próprio objeto
    //
    // * Reference: abstração de uma referência para um objeto
    //
    // * NamingEnumeration: um tipo de java.util.Enumeration usado para colecionar componentes de um contexto
    //
    // * NamingException: principal exceção do JNDI
    public static void test02() throws Exception {

	Properties env = new Properties();

	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory"); // service for files
	env.put(Context.PROVIDER_URL, "file:/home/fernando/Temp"); // context: root folder of the service

	Context ctx = new InitialContext(env);
	File file = (File) ctx.lookup("Temp.txt"); // file in the root service
	System.out.println(file);

	// Método list() retorna uma lista (NamingEnumeration) de pares nome / nome_da_classe (objetos do tipo NameClassPair)
	NamingEnumeration<NameClassPair> list01 = ctx.list("awt"); // awt is a folder
	while (list01.hasMore()) {
	    NameClassPair nc = list01.next();
	    System.out.println(nc);
	}

	// Método listBindings() retorna uma lista de ligações nome / objeto (Binding)
	NamingEnumeration<Binding> lista = ctx.listBindings("awt");
	while (lista.hasMore()) {
	    Binding bd = (Binding) lista.next();
	    System.out.println(bd.getName() + ": " + bd.getObject());
	}
    }

    // ================================================================================================================================================
    // Two JNDI drivers are available to access objects distributed in the JDK
    // * SPI CORBA (COS Naming): allows localization of CORBA objects (serializados em um formato independende de linguagem) - used on RMI-IIOP (EJB)
    // com.sun.jndi.cosnaming.CNCtxFactory
    //
    // SPI RMI: allows localization of serialized Java objects (objects can be used by other Java applications)
    // com.sun.jndi.rmi.registry.RegistryContextFactory
    public static void test03() throws Exception {

	final EmbeddedEjb ejb = new EmbeddedEjb(EMBEDDED_JEE_TEST_APP_NAME);
	ejb.addClasses(AccountSessionBean.class, Fruit.class);

	final EmbeddedEar ear = new EmbeddedEar(EMBEDDED_JEE_TEST_APP_NAME, JeeVersion.JEE_7);
	ear.addModules(ejb);

	final File earFile = ear.exportToFile(APP_FILE_TARGET);
	System.out.println("Execute this file " + earFile + " on real Glassfish server");

	// -----------------------------------------------------------------------
	// RMI
	Properties envRmi = new Properties();
	envRmi.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory"); // service for files
	envRmi.put(Context.PROVIDER_URL, "rmi://localhost:8686"); // 1099 context: root folder of the service

	Fruit fruit01 = new Fruit("orange");

	// Map
	Context ctxRmi = new InitialContext(envRmi);
	ctxRmi.bind("favorite", fruit01); // object string

	// Locate
	Object rmiFruit = ctxRmi.lookup("favorite");
	Object javaRmiFruit = javax.rmi.PortableRemoteObject.narrow(rmiFruit, Fruit.class);
	Fruit fruit02 = (Fruit) javaRmiFruit;
	System.out.println(fruit02);

	// -----------------------------------------------------------------------
	// Corba, don't use it
	Properties envCorba = new Properties();
	envCorba.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.cosnaming.CNCtxFactory"); // service for files
	envCorba.put(Context.PROVIDER_URL, "iiop://localhost:1072"); // context: root folder of the service
	envCorba.put("org.omg.CORBA.ORBInitialPort", "3700");

	// Map
	Context ctxCorba = new InitialContext(envCorba);
	ctxCorba.bind("favorite", fruit01);

	// Locate
	Object corbaFruit = ctxCorba.lookup("favorite");
	Object javaFruit = javax.rmi.PortableRemoteObject.narrow(corbaFruit, Fruit.class);
	Fruit fruit03 = (Fruit) javaFruit;
	System.out.println(fruit03);

    }

    @Stateless
    public static class AccountSessionBean {

	private float amount = 0;

	@PostConstruct
	public void postConstruct() {
	    System.out.println("PostConstruct!");
	}

	@PreDestroy
	public void preDestroy() {
	    System.out.println("PreDestroy!");
	}

	public String withdraw(float amount) {
	    this.amount -= amount;
	    return "Withdrawn: " + amount;
	}

	public String deposit(float amount) {
	    this.amount += amount;
	    return "Deposited: " + amount;
	}

	public float getAmount() {
	    return this.amount;
	}
    }

    public static class Fruit implements Remote {

	public String type;

	public Fruit() {
	    super();
	}

	public Fruit(String type) {
	    super();
	    this.type = type;
	}

    }

    // ================================================================================================================================================
    public static void main(String[] args) throws Exception {
	test03();
    }

    //
    // java:global
    // É a maneira portatil de busca remota de um EJB usando JNDI lookup.
    // Ela segue o seguinte padrão:
    // java:global[/application name]/module name/enterprise bean name[/interface name]

    // java:module
    // É a maneira de busca do EJB dentro do mesmo módulo.
    // java:module/enterprise bean name/[interface name]
    // java:app

}
