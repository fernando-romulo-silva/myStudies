package br.com.fernando.chapter13_javaPersistence.part01_entities;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class Entities03Inheritance {

    // https://thoughts-on-java.org/complete-guide-inheritance-strategies-jpa-hibernate/
    //
    // 4 Inheritance Strategies
    //
    // JPA and Hibernate support 4 inheritance strategies which map the domain
    // objects to different table structures.
    //
    // The @Inheritance and @Discriminator annotations are used to specify the
    // inheritance from an entity superclass.
    //
    // The @MappedSuperclass annotation is used to designate a nonentity superclass
    // and captures state and mapping information that is common to multiple entity classes.
    //
    // Choosing a Strategy
    //
    // Here are a few recommendations:
    //
    // * If you require the best performance and need to use polymorphic queries and relationships, you should choose the single table strategy. 
    // But be aware, that you can’t use not null constraints on subclass attributes which increase the risk of data inconsistencies.
    //
    // * If data consistency is more important than performance and you need polymorphic queries and relationships, the joined strategy is probably your best option.
    //
    // * If you don’t need polymorphic queries or relationships, the table per class strategy is most likely the best fit. 
    // It allows you to use constraints to ensure data consistency and provides an option of polymorphic queries. 
    // But keep in mind, that polymorphic queries are very complex for this table structure and that you should avoid them.
    //
    // Mapped Superclass
    //
    // Tables
    //
    // 00000000000000000000000000000000000000000000000000
    // 00000000000000000000000000000000000000000000000000
    // 000000000000000||==========||000000000000000000000
    // 000000000000000||AUTHOR0000||000000000000000000000
    // 000000000000000||==========||000000000000000000000
    // 000000000000000||ID00000000||000000000000000000000
    // 000000000000000||FIRST_NAME||000000000000000000000
    // 000000000000000||LAST_NAME0||000000000000000000000
    // 000000000000000||VERSION000||000000000000000000000
    // 000000000000000||==========||000000000000000000000
    // 0000000000000000000000|000000000000000000000000000
    // 0000000000000000000000|000000000000000000000000000
    // 000000000000||==================||0000000000000000
    // 000000000000||PUBLICATION_AUTHOR||0000000000000000
    // 000000000000||==================||0000000000000000
    // 000000000000||PUBLICATION_ID0000||0000000000000000
    // 000000000000||AUTHOR_ID000000000||0000000000000000
    // 000000000000||==================||0000000000000000
    // 0000000000000000000000|000000000000000000000000000
    // 0000000000000000000000|000000000000000000000000000
    // 00000000000---------------------------000000000000
    // 00000000000|0000000000000000000000000|000000000000
    // 00000000000|0000000000000000000000000|000000000000    
    // 000||===============||000000||===============||000
    // 000||BLOG_POST000000||000000||BOOK00000000000||000
    // 000||===============||000000||===============||000
    // 000||ID0000000000000||000000||ID0000000000000||000
    // 000||PUBLISHING_DATE||000000||PUBLISHING_DATE||000
    // 000||TITLE0000000000||000000||TITLE0000000000||000
    // 000||VERSION00000000||000000||VERSION00000000||000
    // 000||URL000000000000||000000||PAGE00000000000||000
    // 000||===============||000000||===============||000
    // 00000000000000000000000000000000000000000000000000
    // 00000000000000000000000000000000000000000000000000
    //
    // That allows you to share the attribute definition between multiple entities.
    //
    // But it also has a huge drawback. A mapped superclass is not an entity, and
    // there is no table for it.
    //
    // That means that you can't use polymorphic queries that select all Publication
    // entities and you also can't define a relationship between
    // an Author entity and all Publications.
    //
    // You either need to use uni-directional relationship from the Publication to
    // the Author entity, or you have to define a relationship
    // between an Author and each kind of Publication.
    //
    // In general, if you need these relationships, you should have a look at the
    // other inheritance strategies.
    // They are most likely a better fit for your use case.
    //
    // If you just want to share state and mapping information between your
    // entities, the mapped superclass strategy is a good fit and easy to implement.
    // You just have to set up your inheritance structure, annotate the mapping
    // information for all attributes and add the @MappedSuperclass annotation to your superclass.
    //
    // Without the @MappedSuperclass annotation, Hibernate will ignore the mapping
    // information of your superclass.
    //
    @MappedSuperclass
    public static abstract class Publication {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "ID", updatable = false, nullable = false)
        Long id;

        @Column(name = "TITLE")
        String title;

        @Version
        @Column(name = "VERSION")
        int version;

        @Column(name = "PUBLISHING_DATE")
        @Temporal(TemporalType.DATE)
        Date publishingDate;

        @Transient
        Set<Author> authors = new HashSet<>();

        protected abstract Set<Author> getAuthors();
    }

    @Entity
    @Table(name = "BOOK")
    public static class Book extends Publication {

        @Column(name = "PAGES")
        int pages;

        @Override
        @ManyToMany
        @JoinTable( //
                name = "PUBLICATION_AUTHOR", //
                joinColumns = { @JoinColumn(name = "PUBLICATION_ID", referencedColumnName = "ID") }, //
                inverseJoinColumns = { @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID") } //
        )
        public Set<Author> getAuthors() {
            return authors;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();

            builder.append("Book [id=").append(id) //
                    .append(", title=").append(title) //
                    .append(", authors=").append(authors) //
                    .append("]");

            return builder.toString();
        }
    }

    @Entity
    @Table(name = "BLOG_POST")
    public static class BlogPost extends Publication {

        @Column(name = "URL")
        String url;

        @Override
        @ManyToMany
        @JoinTable( //
                name = "PUBLICATION_AUTHOR", //
                joinColumns = { @JoinColumn(name = "PUBLICATION_ID", referencedColumnName = "ID") }, //
                inverseJoinColumns = { @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID") } //
        )
        public Set<Author> getAuthors() {
            return authors;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();

            builder.append("BlogPost [url=").append(url) //
                    .append(", id=").append(id) //
                    .append(", title=").append(title) //
                    .append(", authors=").append(authors) //
                    .append("]");

            return builder.toString();
        }
    }

    @Entity
    @Table(name = "AUTHOR")
    public static class Author {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "ID", updatable = false, nullable = false)
        Long id;

        @Column(name = "FIRST_NAME")
        String firstName;

        @Column(name = "LAST_NAME")
        String lastName;

        @Version
        @Column(name = "VERSION")
        int version;

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();

            builder.append("Author [id=").append(id) //
                    .append(", firstName=").append(firstName) //
                    .append("]");

            return builder.toString();
        }
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

        final List<String> classes = Arrays.asList(Publication.class.getName(), Book.class.getName(), BlogPost.class.getName(), Author.class.getName());

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

            final Author a01 = new Author();
            a01.firstName = "Author 01 First";
            a01.lastName = "Author 01 Last";

            final Author a02 = new Author();
            a02.firstName = "Author 02 First";
            a02.lastName = "Author 02 Last";

            final Book b1 = new Book();
            b1.title = "The book 01";
            b1.publishingDate = new Date();
            b1.pages = 56;
            b1.version = 2;
            b1.authors.add(a01);
            b1.authors.add(a02);

            final Book b2 = new Book();
            b2.title = "The book 02";
            b2.publishingDate = new Date();
            b2.pages = 250;
            b2.version = 1;
            b2.authors.add(a01);

            final BlogPost bl1 = new BlogPost();
            bl1.title = "Post 01";
            bl1.url = "www.blogpost.com";
            bl1.publishingDate = new Date();
            bl1.version = 2;
            bl1.authors.add(a01);

            em.persist(a01);
            em.persist(a02);
            em.persist(b1);
            em.persist(b2);
            em.persist(bl1);

            em.getTransaction().commit();

            // --------------------------------------------------------------------------------------------------------
            // As I explained at the beginning of this section, you can't use the inheritance structure for polymorphic 
            // queries or to define relationships.
            //
            // But you can, of course, query the entites as any other entity.

            final List<Book> books = em.createQuery("SELECT b FROM Entities03Inheritance$Book b", Book.class).getResultList();
            System.out.println(books);

            final List<BlogPost> blogPost = em.createQuery("SELECT b FROM Entities03Inheritance$BlogPost b", BlogPost.class).getResultList();
            System.out.println(blogPost);

            em.close();

            factory.close();
        }
    }
}
