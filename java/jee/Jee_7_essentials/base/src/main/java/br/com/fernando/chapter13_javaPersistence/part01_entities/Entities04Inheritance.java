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

public class Entities04Inheritance {

    // Table per Class
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
    // The table per class strategy is similar to the mapped superclass strategy. 
    // The main difference is that the superclass is now also an entity. 
    // Each of the concrete classes gets still mapped to its own database table. 
    // This mapping allows you to use polymorphic queries and to define relationships to the superclass.
    //
    // But the table structure adds a lot of complexity to polymorphic queries, and you should, therefore, avoid them.
    //
    // You annotate the class with @Entity and add your mapping annotations to the attributes.
    @Entity
    @Table(name = "PUBLICATION")
    // The only difference is the additional @Inheritance annotation which you have to add to the class 
    // to define the inheritance strategy. 
    // In this case, it’s the InheritanceType.TABLE_PER_CLASS.
    @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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

        @ManyToMany
        @JoinTable( //
                name = "PUBLICATION_AUTHOR", //
                joinColumns = { @JoinColumn(name = "PUBLICATION_ID", referencedColumnName = "ID") }, //
                inverseJoinColumns = { @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID") } //
        )
        final Set<Author> authors = new HashSet<>();

        @Column(name = "PUBLISHING_DATE")
        @Temporal(TemporalType.DATE)
        Date publishingDate;

    }

    @Entity
    @Table(name = "BOOK")
    public static class Book extends Publication {

        @Column(name = "PAGES")
        int pages;

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

            final List<Publication> publications = em.createQuery("SELECT p FROM Entities04Inheritance$Publication p", Publication.class).getResultList();
            System.out.println(publications);

            final List<BlogPost> blogPost = em.createQuery("SELECT b FROM Entities04Inheritance$BlogPost b", BlogPost.class).getResultList();
            System.out.println(blogPost);

            // The superclass is now also an entity and you can, therefore, use it to define a relationship between the Author and the Publication entity.
            final List<Author> authors = em.createQuery("SELECT a FROM Entities04Inheritance$Author a", Author.class).getResultList();
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
