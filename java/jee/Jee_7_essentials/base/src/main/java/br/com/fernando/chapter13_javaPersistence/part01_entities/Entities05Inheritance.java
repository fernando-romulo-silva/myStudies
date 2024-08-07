package br.com.fernando.chapter13_javaPersistence.part01_entities;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class Entities05Inheritance {

    // Single Table
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
    // 0000000000000||===============||000000000000000000
    // 0000000000000||PUBLICATION0000||000000000000000000
    // 0000000000000||===============||000000000000000000
    // 0000000000000||ID0000000000000||000000000000000000
    // 0000000000000||PUBLISHING_DATE||000000000000000000
    // 0000000000000||TITLE0000000000||000000000000000000
    // 0000000000000||VERSION00000000||000000000000000000
    // 0000000000000||TYPE00000000000||000000000000000000
    // 0000000000000||PAGE00000000000||000000000000000000
    // 0000000000000||URL000000000000||000000000000000000
    // 0000000000000||===============||000000000000000000
    // 00000000000000000000000000000000000000000000000000
    // 00000000000000000000000000000000000000000000000000
    //
    //
    // The single table strategy maps all entities of the inheritance structure to the same database table. 
    // This approach makes polymorphic queries very efficient and provides the best performance.
    // 
    // But it also has some drawbacks. The attributes of all entities are mapped to the same database table. 
    // Each record uses only a subset of the available columns and sets the rest of them to null. 
    // You can, therefore, not use not null constraints on any column that isn’t mapped to all entities.     
    //
    @Entity
    @Table(name = "PUBLICATION_TYPE")
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    // You can either define the column name with a @DiscriminatorColumn annotation on the superclass or Hibernate will use DTYPE as its default name.
    @DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "TYPE")
    public static abstract class Publication {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "ID", updatable = false, nullable = false)
        protected Long id;

        @Column(name = "TITLE")
        protected String title;

        @Version
        @Column(name = "VERSION")
        protected int version;

        @Column(name = "PUBLISHING_DATE")
        @Temporal(TemporalType.DATE)
        protected Date publishingDate;

        @ManyToMany
        @JoinTable( //
                name = "PUBLICATION_AUTHOR", //
                joinColumns = { @JoinColumn(name = "PUBLICATION_ID", referencedColumnName = "ID") }, //
                inverseJoinColumns = { @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID") } //
        )
        protected final Set<Author> authors = new HashSet<>();

    }

    @Entity
    // The definition of the subclasses is again similar to the previous examples. 
    // But this time, you should also provide a @DiscriminatorValue annotation.
    @DiscriminatorValue("BOOK")
    public static class Book extends Publication {

        @Column(name = "PAGES")
        private int pages;

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
    @DiscriminatorValue("BLOG_POST")
    public static class BlogPost extends Publication {

        @Column(name = "URL")
        private String url;

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

        @ManyToMany(mappedBy = "authors")
        final List<Publication> publications = new ArrayList<>();

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

            final PersistenceUnitInfoDescriptor persistenceUnit = new PersistenceUnitInfoDescriptor(new HibernatePersistenceUnitInfo("appName", classes, props));
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
            a01.publications.add(b1);
            b1.authors.add(a02);
            a02.publications.add(b1);

            final Book b2 = new Book();
            b2.title = "The book 02";
            b2.publishingDate = new Date();
            b2.pages = 250;
            b2.version = 1;

            final BlogPost bl1 = new BlogPost();
            bl1.title = "Post 01";
            bl1.publishingDate = new Date();
            bl1.version = 2;
            bl1.authors.add(a01);
            a01.publications.add(bl1);

            em.persist(a01);
            em.persist(a02);
            em.persist(b1);
            em.persist(b2);
            em.persist(bl1);

            em.getTransaction().commit();

            // --------------------------------------------------------------------------------------------------------

            final List<Publication> publications = em.createQuery("SELECT p FROM Entities05Inheritance$Publication p", Publication.class).getResultList();
            System.out.println(publications);

            final List<BlogPost> blogPost = em.createQuery("SELECT b FROM Entities05Inheritance$BlogPost b", BlogPost.class).getResultList();
            System.out.println(blogPost);

            final List<Author> authors = em.createQuery("SELECT a FROM Entities05Inheritance$Author a", Author.class).getResultList();
            for (final Author author : authors) {
                for (final Publication p : author.publications) {

                    if (p instanceof Book) {
                        System.out.println("book: " + p);
                    } else {
                        System.out.println("blog post: " + p);
                    }
                }
            }

            em.close();

            factory.close();
        }
    }

}
