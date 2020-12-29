package br.com.fernando.enthuware.managePersistenceJpaAndBeanValidation;

import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import javax.persistence.Converts;
import javax.persistence.Entity;
import javax.persistence.Id;

public class Question02 {

    class LocalDate {

    }

    // Given the code fragment:

    // line1
    public class DataConverter implements AttributeConverter<LocalDate, Date> {

	// implementations
	@Override
	public Date convertToDatabaseColumn(LocalDate attribute) {
	    return null;
	}

	@Override
	public LocalDate convertToEntityAttribute(Date dbData) {
	    return null;
	}
    }

    @Entity
    // line 2
    public class Customer {
	// line3
	private LocalDate birthday;
	// remaining implementation
    }

    // How can you apply DateConverter to the birthday field?
    // You had to select 1 option(s)
    //
    // A
    // by adding @Convert(to=Date.class) at line 3
    //
    // B
    // by invoking the setConverter(DateConverter.class) method on the EntityManager object
    //
    // C
    // by adding @Converter(autoApply=true) at line 1
    //
    // D
    // by adding @Convert((DateConverter.class)) at line 2
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
    // The correct answer is C
    // If the autoApply element is specified as true, the persistence provider must automatically apply the converter to all mapped attributes of
    // the specified target type for all entities in the persistence unit except for attributes for which conversion is overridden by means of the
    // Convert annotation (or XML equivalent).
    //
    // The Convert annotation may be applied to an entity class that extends a mapped superclass to specify or override a conversion mapping
    // for an inherited basic or embedded attribute.

    @Entity
    @Convert(converter = DataConverter.class, attributeName = "birthday")
    // or
    @Converts({ //
	    @Convert(attributeName = "startDate", converter = DataConverter.class), //
	    @Convert(attributeName = "endDate", converter = DataConverter.class) //
    })
    public class Employee {

	@Id
	private long id;

	@Convert(converter = DataConverter.class)
	private LocalDate birthday;
	// ...
    }

}
