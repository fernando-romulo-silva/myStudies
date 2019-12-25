package br.com.fernando.chapter13_javaPersistence.part17_entityGrapth;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class EntityGraphExample {

    // Lazy loading is often an issue with JPA. You have to define at the entity if you want to use FetchType.LAZY
    // (default) or FetchType.EAGER to load the relation and this mode is always used.
    //
    // But this is not without drawbacks. If you have to use an element of the relation, you need to make sure, that the
    // relation gets initialized within the transaction that load the entity from the database.
    //
    // This can be done by using a specific query that reads the entity and the required relations from the database.
    //
    // Another option is to access the relation within your business code which will result in an additional query for
    // each relation. Both approaches are far from perfect.
    //
    // JPA 2.1 entity graphs are a better solution for it.
    //
    // The definition of an entity graph is independent of the query and defines which attributes to fetch from the database.
    //
    // An entity graph can be used as a fetch or a load graph.
    // If a fetch graph is used, only the attributes specified by the entity graph will be treated as FetchType.EAGER.
    // All other attributes will be lazy. If a load graph is used, all attributes that are not specified by the entity graph will keep their default fetch type.

    @Entity
    @Table(name = "ORDER_PURCHASE")
    // The definition of a named entity graph is done by the @NamedEntityGraph annotation at the entity.
    // It defines a unique name and a list of attributes (the attributeNodes) that shall be loaded.
    // The following example shows the definition of the entity graph graph.Order.items which will load the list of OrderItem of an Order.

    @NamedEntityGraphs({ //

	    @NamedEntityGraph(name = "graph.Order.items", attributeNodes = @NamedAttributeNode("items")),

	    // If we want to do the same for the OrderItem entity, we can do this with an entity sub graph.
	    // The definition of a named sub graph is similar to the definition of an named entity graph and can be referenced as an attributeNode.
	    @NamedEntityGraph(name = "graph.Order.itemsSub", //
		    attributeNodes = @NamedAttributeNode(value = "items", subgraph = "items"), //
		    subgraphs = @NamedSubgraph(name = "items", attributeNodes = @NamedAttributeNode("product")) //
	    ) })

    public static class Order {

	@Id
	@Column(name = "ID")
	Integer id;

	@Column(name = "ORDER_NUMBER")
	String orderNumber;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	Set<OrderItem> items = new HashSet<OrderItem>();

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("Order [id=").append(id).append(", orderNumber=").append(orderNumber).append("]");
	    return builder.toString();
	}

	@Override
	public int hashCode() {
	    return Objects.hashCode(id);
	}

	@Override
	public boolean equals(final Object o) {

	    if (this == o)
		return true;

	    if (!(o instanceof Order))
		return false;

	    return id != null && id.equals(((Order) o).id);
	}
    }

    @Entity
    @Table(name = "ORDER_PURCHASE_ITEM")
    public static class OrderItem {

	@Id
	@Column(name = "ID")
	Integer id;

	@Column(name = "QUANTITY")
	int quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", referencedColumnName = "ID")
	Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
	Product product;

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("OrderItem [id=").append(id).append(", quantity=").append(quantity).append("]");
	    return builder.toString();
	}

	@Override
	public int hashCode() {
	    return Objects.hashCode(id);
	}

	@Override
	public boolean equals(final Object o) {

	    if (this == o)
		return true;

	    if (!(o instanceof OrderItem))
		return false;

	    return id != null && id.equals(((OrderItem) o).id);
	}
    }

    @Entity
    @Table(name = "PRODUCT")
    public static class Product implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	Integer id;

	@Column(name = "NAME")
	String name;

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("Product [id=").append(id).append(", name=").append(name).append("]");
	    return builder.toString();
	}

	@Override
	public int hashCode() {
	    return Objects.hashCode(id);
	}

	@Override
	public boolean equals(final Object o) {

	    if (this == o)
		return true;

	    if (!(o instanceof Product))
		return false;

	    return id != null && id.equals(((Product) o).id);
	}
    }

    public static void execute(final EntityManagerFactory factory) {

	// -------------------------------------------------------------------------------
	// Normal way
	final EntityManager emNormal = factory.createEntityManager();
	final Order order01 = emNormal.find(Order.class, 1);
	/**
	 * <pre>
	 *  	    
	 *   select
	 *       entitygrap0_.ID as ID1_0_0_,
	 *       entitygrap0_.ORDER_NUMBER as ORDER_NU2_0_0_ 
	 *   from
	 *       ORDER_PURCHASE entitygrap0_ 
	 *   where
	 *       entitygrap0_.ID=?
	 * 
	 * </pre>
	 */
	System.out.println("Without EntityGraph:" + order01);

	emNormal.close();

	// -------------------------------------------------------------------------------
	// With Graph
	// Therefore we need to create a Map with query hints and set it as an additional parameter on a find or query method call.
	final EntityManager emEntityGraph = factory.createEntityManager();

	@SuppressWarnings("unchecked")
	final EntityGraph<Order> graph = (EntityGraph<Order>) emEntityGraph.getEntityGraph("graph.Order.items");
	final Map<String, Object> hints = new HashMap<>();
	hints.put("javax.persistence.fetchgraph", graph);

	final Order order02 = emEntityGraph.find(Order.class, 1, hints);
	/**
	 * <pre>
	 *  
	 *  select
	 *       entitygrap0_.ID as ID1_0_0_,
	 *       entitygrap0_.ORDER_NUMBER as ORDER_NU2_0_0_,
	 *       items1_.ORDER_ID as ORDER_ID3_1_1_,
	 *       items1_.ID as ID1_1_1_,
	 *       items1_.ID as ID1_1_2_,
	 *       items1_.ORDER_ID as ORDER_ID3_1_2_,
	 *       items1_.PRODUCT_ID as PRODUCT_4_1_2_,
	 *       items1_.QUANTITY as QUANTITY2_1_2_ 
	 *   from
	 *       ORDER_PURCHASE entitygrap0_ 
	 *   left outer join
	 *       ORDER_PURCHASE_ITEM items1_ 
	 *           on entitygrap0_.ID=items1_.ORDER_ID 
	 *   where
	 *       entitygrap0_.ID=?
	 * 
	 * </pre>
	 */
	System.out.println("   With EntityGraph:" + order02);

	emEntityGraph.close();

	// -------------------------------------------------------------------------------
	// With Graph
	// The following code snippets shows the definition of a sub graph to load the Product of each OrderItem.
	// The defined entity graph will fetch an Order with all OrderItems and their Products.
	final EntityManager emEntitySubGraph = factory.createEntityManager();

	@SuppressWarnings("unchecked")
	final EntityGraph<Order> subGraph = (EntityGraph<Order>) emEntitySubGraph.getEntityGraph("graph.Order.itemsSub");
	final Map<String, Object> subHints = new HashMap<>();
	subHints.put("javax.persistence.fetchgraph", subGraph);

	final Order order03 = emEntitySubGraph.find(Order.class, 1, subHints);
	/**
	 * <pre>
	 * 
	 *   select
	 *       entitygrap0_.ID as ID1_0_0_,
	 *       entitygrap0_.ORDER_NUMBER as ORDER_NU2_0_0_,
	 *       items1_.ORDER_ID as ORDER_ID3_1_1_,
	 *       items1_.ID as ID1_1_1_,
	 *       items1_.ID as ID1_1_2_,
	 *       items1_.ORDER_ID as ORDER_ID3_1_2_,
	 *       items1_.PRODUCT_ID as PRODUCT_4_1_2_,
	 *       items1_.QUANTITY as QUANTITY2_1_2_,
	 *       entitygrap2_.ID as ID1_2_3_,
	 *       entitygrap2_.NAME as NAME2_2_3_ 
	 *   from
	 *       ORDER_PURCHASE entitygrap0_ 
	 *   left outer join
	 *       ORDER_PURCHASE_ITEM items1_ 
	 *           on entitygrap0_.ID=items1_.ORDER_ID 
	 *   left outer join
	 *       PRODUCT entitygrap2_ 
	 *           on items1_.PRODUCT_ID=entitygrap2_.ID 
	 *   where     
	 *       entitygrap0_.ID=?
	 * 
	 * </pre>
	 */
	System.out.println("   With EntitySubGraph:" + order03);

	emEntitySubGraph.close();

	// -------------------------------------------------------------------------------
	// Dynamic entity graph
	// We use the createEntityGraph(Class rootType) method of the entity manager to create an entity graph for the Order entity.
	// In the next step, we create a list of all attributes of the Order entity that shall be fetched with this entity graph.
	// We only need to add the attribute items, because we will use this entity graph as a loadgraph and all other attributes are eager by default.
	// If we would use this entity graph as a fetchgraph, we would need to add all attributes to the list that should be fetched from the database.

	final EntityManager em = factory.createEntityManager();

	EntityGraph<OrderItem> dynamicGraph = em.createEntityGraph(OrderItem.class);
	dynamicGraph.addAttributeNodes("product");

	Map<String, Object> dynamicHints = new HashMap<String, Object>();
	dynamicHints.put("javax.persistence.loadgraph", dynamicGraph);

	final OrderItem order04 = em.find(OrderItem.class, 1, dynamicHints);

	/**
	 * <pre>
	 * 
	 *    select
	 *        entitygrap0_.ID as ID1_1_0_,
	 *        entitygrap0_.ORDER_ID as ORDER_ID3_1_0_,
	 *        entitygrap0_.PRODUCT_ID as PRODUCT_4_1_0_,
	 *        entitygrap0_.QUANTITY as QUANTITY2_1_0_,
	 *        entitygrap1_.ID as ID1_2_1_,
	 *        entitygrap1_.NAME as NAME2_2_1_ 
	 *    from
	 *        ORDER_PURCHASE_ITEM entitygrap0_ 
	 *    left outer join
	 *        PRODUCT entitygrap1_ 
	 *            on entitygrap0_.PRODUCT_ID=entitygrap1_.ID 
	 *    where
	 *        entitygrap0_.ID=?
	 * 
	 * </pre>
	 */

	System.out.println("   With DynamicEntityGraph:" + order04);

	// This can be done with a sub graph. A sub graph is basically an entity graph that is embedded into another entity graph or entity sub graph.

	// EntityGraph<Order> graph = em.createEntityGraph(Order.class);
	// Subgraph<OrderItem> itemGraph = graph.addSubgraph("items");
	// itemGraph.addAttributeNodes("product");

	em.close();
    }

    // =================================================================================================================================================================
    public static void main(final String[] args) throws Exception {

	final String dataBaseName = EMBEDDED_JEE_TEST_APP_NAME + "DB";
	final String dataBaseUrl = "jdbc:hsqldb:hsql://localhost:" + DATA_BASE_SERVER_PORT + "/" + dataBaseName;

	final Properties props = new Properties();
	props.put("javax.persistence.schema-generation.database.action", "create");
	props.put("javax.persistence.jdbc.url", dataBaseUrl);
	props.put("javax.persistence.jdbc.user", DATA_BASE_SERVER_LOGIN);
	props.put("javax.persistence.jdbc.password", DATA_BASE_SERVER_PASSWORD);
	props.put("javax.persistence.jdbc.driver", "org.hsqldb.jdbcDriver");
	// hibernate
	props.put("hibernate.show_sql", "true");
	props.put("hibernate.format_sql", "true");
	props.put("hibernate.use_sql_comments", "true");

	final List<String> classes = Arrays.asList(Order.class.getName(), OrderItem.class.getName(), Product.class.getName());

	try (final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms()) {

	    embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
	    embeddedRdbms.addSchema("APP", dataBaseName);
	    embeddedRdbms.start(DATA_BASE_SERVER_PORT);

	    final HibernatePersistenceUnitInfo persistenceUnitInfo = new HibernatePersistenceUnitInfo("appName", classes, props);
	    final PersistenceUnitInfoDescriptor persistenceUnit = new PersistenceUnitInfoDescriptor(persistenceUnitInfo);

	    final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(persistenceUnit, new HashMap<>()).build();
	    final EntityManager em = factory.createEntityManager();

	    // -------------------------------------------------------------------------------------------------------
	    em.getTransaction().begin();

	    final Product p1 = new Product();
	    p1.id = 1;
	    p1.name = "Product 1";

	    final Product p2 = new Product();
	    p2.id = 2;
	    p2.name = "Product 2";

	    final Order o1 = new Order();
	    o1.id = 1;
	    o1.orderNumber = "111111";

	    final OrderItem oi1 = new OrderItem();
	    oi1.id = 1;
	    oi1.quantity = 10;
	    oi1.product = p2;
	    oi1.order = o1;
	    o1.items.add(oi1);

	    final OrderItem oi2 = new OrderItem();
	    oi2.id = 2;
	    oi2.quantity = 3;
	    oi2.product = p1;
	    oi2.order = o1;
	    o1.items.add(oi2);

	    em.persist(p1);
	    em.persist(p2);

	    em.persist(o1);

	    em.persist(oi1);
	    em.persist(oi2);

	    em.flush();

	    em.getTransaction().commit();

	    em.clear();

	    em.close();

	    execute(factory);

	    factory.close();
	}
    }

}
