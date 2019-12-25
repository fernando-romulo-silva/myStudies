package br.com.fernando.chapter13_javaPersistence.part01_entities;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class Entities07Relationship {

    // https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
    // ==================================================================================================================================================================
    // You define the relationships between different entities using the @OneToOne, @OneToMany, @ManyToOne, and @ManyToMany annotations on the corresponding
    // field of the referencing entity.
    //
    // A unidirectional relationship requires the owning side to specify the annotation.
    // A bidirectional relationship also requires the nonowning side to refer to its owning side by use of the mappedBy element of the OneToOne, OneToMany,
    // or ManyToMany annotation.
    //
    // Ex
    // As straightforward as it might be in a RDBMS, when it comes to JPA, the one-to-many database association can be represented either through a @ManyToOne
    // or a @OneToMany association since the OOP association can be either unidirectional or bidirectional:
    //
    // 0000000000000000000000000000000000000000000000
    // 000||========||0000000000||==============||000
    // 000||00POST00||0000000000||0POST_COMMENT0||000
    // 000||========||I0------I<||==============||000
    // 000||ID000000||0000000000||ID000000000000||000
    // 000||TITLE000||0000000000||REVIEW00000000||000
    // 000||========||0000000000||POST_ID0000000||000
    // 0000000000000000000000000||==============||000
    // 0000000000000000000000000000000000000000000000
    //
    // ============== Biderecional ================================================================
    //
    @Entity(name = "Post")
    @Table(name = "POST")
    public static class Post {

        @Id
        Long id;

        String title;

        // There are many ways to map the @OneToMany association. We can use a List or a Set. We can also define the @JoinColumn annotation too.
        //
        // You are better off replacing collections with a query, which is much more flexible in terms of fetching performance.
        @OneToMany( //
                mappedBy = "post", // PostComment.post
                //
                // Marking a reference field with CascadeType.REMOVE (or CascadeType.ALL, which includes REMOVE) indicates that remove operations should be cascaded automatically
                // to entity objects that are referenced by that field (multiple entity objects can be referenced by a collection field)
                cascade = CascadeType.ALL, //
                //
                // If orphanRemoval=true is specified the disconnected PostComment instance is automatically removed.
                // This is useful for cleaning up dependent objects (e.g. Address) that should not exist without a reference from an owner object (e.g. Employee).
                orphanRemoval = true //
        )
        final Set<PostComment> comments = new HashSet<>(); // We can use a List or a Set.

        Post() {
            super();
        }

        public Post(final String title) {
            this.title = title;
        }

        public void addComment(final PostComment comment) {
            comments.add(comment);
            comment.post = this;
        }

        public void removeComment(final PostComment comment) {
            comments.remove(comment);
            comment.post = null;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();

            builder.append("Post [id=").append(id) //
                    .append(", title=").append(title) //
                    .append(", comments=").append(comments) //
                    .append("]");

            return builder.toString();
        }
    }

    @Entity(name = "PostComment")
    @Table(name = "POST_COMMENT")
    public static class PostComment {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        Long id;

        @Column(name = "REVIEW")
        String review;

        // The @ManyToOne annotation allows you to map the Foreign Key column in the child entity mapping so that the child has an entity object reference to its parent entity.
        // This is the most natural way of mapping a database one-to-many database association, and, usually, the most efficient alternative too.
        //
        // Fetch
        // The FetchType.EAGER annotation may be specified on an entity to eagerly load the data from the database.
        // The FetchType.LAZY annotation may be specified as a hint that the data should be fetched lazily when it is first accessed.
        //
        // The @ManyToOne association uses FetchType.LAZY because, otherwise, we'd fall back to EAGER fetching which is bad for performance.
        @ManyToOne(fetch = FetchType.LAZY)
        // When you add the @JoinColumn annotation to your association mapping, you can define the name of the foreign key column that represents your association in the table model.
        @JoinColumn(name = "POST_ID")
        Post post;

        PostComment() {
            super();
        }

        public PostComment(final Post post, final String review) {
            super();
            this.review = review;
            this.post = post;
        }

        @Override
        public boolean equals(final Object o) {

            if (this == o)
                return true;

            if (!(o instanceof PostComment))
                return false;

            return id != null && id.equals(((PostComment) o).id);
        }

        @Override
        public int hashCode() {
            return 31;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();

            builder.append("PostComment [id=").append(id) //
                    .append(", review=").append(review) //
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

        final List<String> classes = Arrays.asList(Post.class.getName(), PostComment.class.getName());

        try (final MyEmbeddedRdbms embeddedRdbms = new MyEmbeddedRdbms()) {

            embeddedRdbms.addDataBase(dataBaseName, DATA_BASE_SERVER_LOGIN, DATA_BASE_SERVER_PASSWORD);
            embeddedRdbms.addSchema("APP", dataBaseName);
            embeddedRdbms.start(DATA_BASE_SERVER_PORT);

            final HibernatePersistenceUnitInfo info = new HibernatePersistenceUnitInfo("appName", classes, props);
            final Map<String, Object> configuration = new HashMap<>();
            final EntityManagerFactory factory = new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(info), configuration).build();
            final EntityManager em = factory.createEntityManager();

            em.getTransaction().begin();

            final Post post01 = new Post("First post");
            post01.id = 1L;

            post01.comments.add(new PostComment(post01, "My first review"));
            post01.comments.add(new PostComment(post01, "My second review"));
            post01.comments.add(new PostComment(post01, "My third review"));

            em.persist(post01);

            em.getTransaction().commit();

            // Entities07Relationship$Post
            final List<Post> posts = em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
            System.out.println(posts);

            em.close();

            factory.close();
        }
    }
}
