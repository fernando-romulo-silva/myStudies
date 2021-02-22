package br.com.fernando.myExamCloud.managePersistenceJpaAndBeanValidation;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

public class Question03 {

    // You are assigned to create entities for QUESTION and IMAGE table.
    //
    // Table QUESTION contains a foreign key to table Q_IMAGE.
    // The business requirement states that each question must have exactly one image.
    //
    // --------------------------------------------------------------------------------------------------------------------
    // Choice A
    @Entity
    public class QImageA {
	private QuestionA question;

	@OneToOne
	public QuestionA getQuestion() {
	    return question;
	}

	public void setQuestion(QuestionA question) {
	    this.question = question;
	}
	// ...
    }

    @Entity
    public class QuestionA {
	// ...
    }

    // --------------------------------------------------------------------------------------------------------------------
    // Choice B
    @Entity
    public class QuestionB { // Table Question
	private QImageB questionImage;

	@OneToOne
	public QImageB getQuestionImagee() {
	    return questionImage;
	}

	public void setLicense(QImageB questionImage) {
	    this.questionImage = questionImage;
	}
	// ...
    }

    @Entity
    public class QImageB { // table Q_IMAGE
	// ...
    }

    // --------------------------------------------------------------------------------------------------------------------
    // Choice C
    @Entity
    public class QuestionC {
	private QImageC questionImage;

	public QImageC getQuestionImagee() {
	    return questionImage;
	}

	public void setLicense(QImageC questionImage) {
	    this.questionImage = questionImage;
	}
	// ...
    }

    @Entity
    public class QImageC {
	// ...
    }
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
    // Choice B is correct.
    //
    // This is an example of unidirectional OneToOne Relationships.
    // The annotation @OneToOne must be defined on owner side.
    // Here, entity Question is the owner of the relationship.
    // Hence Choice B is correct.

}
