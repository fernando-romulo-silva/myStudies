package org.agoncal.fascicle.quarkus.number;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import java.time.Instant;

@Schema(description = "Several formats of book numbers")
public class BookNumbers {

    @Schema(required = true)
    @JsonbProperty("isbn_10")
    private String isbn10;

    @Schema(required = true)
    @JsonbProperty("isbn_13")
    private String isbn13;

    private String asin;

    @JsonbProperty("ean_8")
    private String ean8;

    @JsonbProperty("ean_13")
    private String ean13;

    @JsonbTransient
    private Instant generationDate;

    public String getIsbn10() {
	return isbn10;
    }

    public void setIsbn10(String isbn10) {
	this.isbn10 = isbn10;
    }

    public String getIsbn13() {
	return isbn13;
    }

    public void setIsbn13(String isbn13) {
	this.isbn13 = isbn13;
    }

    public String getAsin() {
	return asin;
    }

    public void setAsin(String asin) {
	this.asin = asin;
    }

    public String getEan8() {
	return ean8;
    }

    public void setEan8(String ean8) {
	this.ean8 = ean8;
    }

    public String getEan13() {
	return ean13;
    }

    public void setEan13(String ean13) {
	this.ean13 = ean13;
    }

    public Instant getGenerationDate() {
	return generationDate;
    }

    public void setGenerationDate(Instant generationDate) {
	this.generationDate = generationDate;
    }

    // ======================================
    // = Methods hash, equals, toString =
    // ======================================

    @Override
    public String toString() {
	return "BookNumbers{" + //
			"isbn10='" + isbn10 + '\'' + //
			", isbn13='" + isbn13 + '\'' + //
			", asin='" + asin + '\'' + //
			", ean8='" + ean8 + '\'' + //
			", ean13='" + ean13 + '\'' + //
			", generationDate=" + generationDate + //
			'}';
    }
}
