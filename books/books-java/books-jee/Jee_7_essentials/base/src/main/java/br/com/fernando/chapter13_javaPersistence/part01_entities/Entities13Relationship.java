package br.com.fernando.chapter13_javaPersistence.part01_entities;

import static br.com.fernando.Util.DATA_BASE_SERVER_LOGIN;
import static br.com.fernando.Util.DATA_BASE_SERVER_PASSWORD;
import static br.com.fernando.Util.DATA_BASE_SERVER_PORT;
import static br.com.fernando.Util.EMBEDDED_JEE_TEST_APP_NAME;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyTemporal;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.myembedded.rdbms.MyEmbeddedRdbms;

import br.com.fernando.chapter13_javaPersistence.part00_intro.HibernatePersistenceUnitInfo;

public class Entities13Relationship {

    @Entity(name = "Post")
    @Table(name = "POST")
    public static class Post {

	@Id
	@Column(name = "ID")
	Long id;

	@Column(name = "TITLE")
	String title;

	@MapKeyTemporal(TemporalType.DATE)
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	final Map<Date, PostComment> comments = new HashMap<>();

	Post() {
	    super();
	}

	public Post(final String title) {
	    this.title = title;
	}

	public void addComment(final PostComment comment) {
	    comments.put(comment.createdOn, comment);
	    comment.post = this;
	}

	public void removeComment(final PostComment comment) {
	    comments.remove(comment.createdOn);
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
	Long id;

	@Column(name = "REVIEW")
	String review;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POST_ID")
	Post post;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_ON")
	Date createdOn;

	PostComment() {
	    super();
	}

	public PostComment(final Long id, final Post post, final String review, final Date createOn) {
	    super();
	    this.id = id;
	    this.review = review;
	    this.post = post;
	    this.createdOn = createOn;
	}

	@Override
	public boolean equals(final Object o) {

	    if (this == o)
		return true;

	    if (!(o instanceof PostComment))
		return false;

	    return Objects.equals(id, ((PostComment) o).id);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id);
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

	    SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	    final Date date01 = fmt.parse("04/06/2017 07:00:00");
	    final Date date02 = fmt.parse("17/12/2007 12:30:20");
	    final Date date03 = fmt.parse("18/12/2007 24:15:40");

	    post01.addComment(new PostComment(1L, post01, "My first review", date01));
	    post01.addComment(new PostComment(2L, post01, "My second review", date02));
	    post01.addComment(new PostComment(3L, post01, "My third review", date03));

	    em.persist(post01);

	    em.getTransaction().commit();

	    // Entities13Relationship$Post
	    final List<Post> posts = em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
	    System.out.println(posts);

	    // Entities13Relationship$PostComment
	    final List<PostComment> postComments = em.createQuery("SELECT p FROM PostComment p", PostComment.class).getResultList();
	    System.out.println(postComments);

	    em.close();

	    factory.close();
	}
    }

}
