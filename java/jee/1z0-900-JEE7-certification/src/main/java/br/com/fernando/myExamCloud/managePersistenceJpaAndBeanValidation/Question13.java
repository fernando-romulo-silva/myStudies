package br.com.fernando.myExamCloud.managePersistenceJpaAndBeanValidation;

public class Question13 {

    // Given the JPQL code fragment:
    //
    // Select book.title, book.author, book.pages FROM Book book
    //
    // Which clauses can you add to this JPQL query to retrieve only those books with between 200 and 500 total pages?
    //
    // You had to select 2 options
    //
    // Choice A
    // WHERE MIN(book.pages) >= 200 AND MAX(book.pages) <= 500
    //
    // Choice B
    // WHERE book.pages <= 200 OR book.pages >= 500
    //
    // Choice C
    // WHERE book.pages BETWEEN 200 AND 500
    //
    // Choice D
    // WHERE book.pages <= 200 AND book.pages >=500
    //
    //
    //
    //
    //
    //
    //
    //
    //    
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // The A and C are corrects
    //
    // There are two ways we can achieve this goal.
    //
    // By MIN, MAX and By BETWEEN
    //
    // Choice A uses MIN, MAX.
    // Choice C uses BETWEEN. Just replace both the MIN and MAX arguments of the BETWEEN operator .
    // Choice B is incorrect, the query will not return those books with between 200 and 500 total pages.
    // Choice D is incorrect, the query will not correct results.
}
