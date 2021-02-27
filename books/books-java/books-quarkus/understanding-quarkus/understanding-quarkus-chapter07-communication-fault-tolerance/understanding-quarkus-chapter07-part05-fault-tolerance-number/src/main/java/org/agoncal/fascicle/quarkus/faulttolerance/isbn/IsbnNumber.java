package org.agoncal.fascicle.quarkus.faulttolerance.isbn;

import java.time.Instant;

public class IsbnNumber {

    public String gs1;

    public String isbn13;

    public Instant generatedAt;

    @Override
    public String toString() {
	return "IsbnNumber{" + //
		"gs1='" + gs1 + '\'' + //
		", isbn13='" + isbn13 + '\'' + //
		", generatedAt=" + generatedAt + //
		'}';
    }
}
