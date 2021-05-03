package org.agoncal.fascicle.quarkus.book;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Random;

@Schema(description = "Book representation")
@Entity
public class Book extends PanacheEntity {

    @NotNull
    @Schema(required = true)
    public String title;
    
    @Column(name = "isbn_13")
    public String isbn13;

    @Column(name = "isbn_10")
    public String isbn10;
    
    public String author;
    
    @Column(name = "year_of_publication")
    public Integer yearOfPublication;
    
    @Column(name = "nb_of_pages")
    public Integer nbOfPages;

    @Min(1)
    @Max(10)
    public Integer rank;
    
    public BigDecimal price;
    
    @Column(name = "small_image_url")
    public URL smallImageUrl;
    
    @Column(name = "medium_image_url")
    public URL mediumImageUrl;
    
    @Column(length = 10000)
    @Size(min = 1, max = 10000)
    public String description;

    public static Book findRandom() {
	long countBooks = Book.count();
	int randomBook = new Random().nextInt((int) countBooks);
	return Book.findAll().page(randomBook, 1).firstResult();
    }

    // ======================================
    // = Methods hash, equals, toString =
    // ======================================

    @Override
    public String toString() {
	return "Book{" + "id=" + id + ", title='" + title + '\'' + ", isbn13='" + isbn13 + '\'' + ", isbn10='" + isbn10 + '\'' + ", author='" + author + '\'' + ", yearOfPublication="
			+ yearOfPublication + ", nbOfPages=" + nbOfPages + ", rank=" + rank + ", price=" + price + ", smallImageUrl=" + smallImageUrl + ", mediumImageUrl=" + mediumImageUrl
			+ ", description='" + description + '\'' + '}';
    }
}
