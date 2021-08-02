package br.com.fernando.chapter13_javaPersistence.part09_queryEntities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

public class Queries00Entities {

    // https://thoughts-on-java.org/jpql/

    @Entity
    @Table(name = "PUBLICATION")
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

	@ManyToOne
	@JoinColumn(name = "PUBLISHER_ID", referencedColumnName = "ID")
	Publisher publisher;

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
    @SqlResultSetMapping( //
	    name = "AuthorValueMapping", //
	    classes = @ConstructorResult( //
		    targetClass = AuthorValue.class, //
		    columns = { //
			    @ColumnResult(name = "ID", type = Long.class), //
			    @ColumnResult(name = "FIRST_NAME"), //
			    @ColumnResult(name = "LAST_NAME"), //
			    @ColumnResult(name = "NUM_BOOKS", type = Long.class) //
		    } //
	    )//
    ) //
    @NamedNativeQueries({ //
	    @NamedNativeQuery(name = "selectAuthorNames", query = "SELECT a.FIRST_NAME, a.LAST_NAME FROM Author a"), //
	    @NamedNativeQuery(name = "selectAuthorEntities", query = "SELECT a.ID, a.FIRST_NAME, a.LAST_NAME FROM Author a", resultClass = Author.class), //
	    @NamedNativeQuery(name = "selectAuthorValue", //
		    query = //
		    "SELECT a.ID, a.FIRST_NAME, a.LAST_NAME, count(b.ID) as NUM_BOOKS " + //
			    "  FROM AUTHOR a " + //
			    "  JOIN PUBLICATION_AUTHOR ba on ba.AUTHOR_ID = a.ID" + //
			    "  JOIN BOOK b ON b.ID = ba.PUBLICATION_ID " + //
			    " GROUP BY a.ID, a.FIRST_NAME, a.LAST_NAME ", //
		    resultSetMapping = "AuthorValueMapping") //
    }) //
    
    @NamedQueries({ //
	@NamedQuery(name = "selectAuthors", query = "SELECT a FROM Queries00Entities$Author a")
    })

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

// The class of the static metamodel looks similar to the entity.
// Based on the JPA specification, there is a corresponding metamodel class for every managed class in the persistence unit.
// You can find it in the same package and it has the same name as the corresponding managed class with an added ‘_’ at the end.
    @StaticMetamodel(Author.class)
    public static class Author_ {

	public static volatile SingularAttribute<Author, Long> id;

	public static volatile SingularAttribute<Author, String> firstName;

    }

    public static class AuthorValue {

	Long id;

	String firstName;

	String lastName;

	Long numBooks;

	public AuthorValue(final Long id, final String firstName) {
	    super();
	    this.id = id;
	    this.firstName = firstName;
	}

	public AuthorValue(Long id, String firstName, String lastName, Long numBooks) {
	    super();
	    this.id = id;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.numBooks = numBooks;
	}

	@Override
	public String toString() {
	    final StringBuilder builder = new StringBuilder();

	    builder.append("AuthorValue [id=").append(id) //
		    .append(", firstName=").append(firstName) //
		    .append("]");

	    return builder.toString();
	}
    }

    @Entity
    @Table(name = "PUBLISHER")
    public static class Publisher {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", updatable = false, nullable = false)
	Long id;

	@Version
	int version;

	@Column(name = "NAME")
	String name;

	@OneToMany(mappedBy = "publisher")
	Set<Book> books = new HashSet<Book>();

	@Override
	public boolean equals(final Object o) {

	    if (this == o)
		return true;

	    if (!(o instanceof Publisher))
		return false;

	    return id != null && id.equals(((Publisher) o).id);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id);
	}

	@Override
	public String toString() {
	    String result = getClass().getSimpleName() + " ";

	    if (name != null && !name.trim().isEmpty()) {
		result += "name: " + name;
	    }

	    return result;
	}

    }

    public static void prepareDataBase(final EntityManager em) {
	em.getTransaction().begin();

	final Author a01 = new Author();
	a01.firstName = "Author 01 First";
	a01.lastName = "Author 01 Last";

	final Author a02 = new Author();
	a02.firstName = "Author 02 First";
	a02.lastName = "Author 02 Last";

	final Publisher p1 = new Publisher();
	p1.name = "Siera";

	final Publisher p2 = new Publisher();
	p2.name = "Amazon";

	final Book b1 = new Book();
	b1.title = "The book 01";
	b1.publishingDate = new Date();
	b1.pages = 56;
	b1.version = 2;
	b1.authors.add(a01);
	b1.authors.add(a02);
	b1.publisher = p1;
	p1.books.add(b1);

	final Book b2 = new Book();
	b2.title = "The book 02";
	b2.publishingDate = new Date();
	b2.pages = 250;
	b2.version = 1;
	b2.authors.add(a01);
	b2.publisher = p2;
	p2.books.add(b2);

	final BlogPost bl1 = new BlogPost();
	bl1.title = "Post 01";
	bl1.url = "www.blogpost.com";
	bl1.publishingDate = new Date();
	bl1.version = 2;
	bl1.authors.add(a01);

	em.persist(p1);
	em.persist(p2);
	em.persist(a01);
	em.persist(a02);
	em.persist(b1);
	em.persist(b2);
	em.persist(bl1);

	em.getTransaction().commit();

	final List<Book> books = em.createQuery("SELECT b FROM Queries00Entities$Book b", Book.class).getResultList();
	System.out.println(books);

	final List<BlogPost> blogPost = em.createQuery("SELECT b FROM Queries00Entities$BlogPost b", BlogPost.class).getResultList();
	System.out.println(blogPost);
    }
}
