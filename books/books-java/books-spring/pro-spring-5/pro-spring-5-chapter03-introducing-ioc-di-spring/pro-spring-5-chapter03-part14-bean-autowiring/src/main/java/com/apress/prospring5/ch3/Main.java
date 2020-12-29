package com.apress.prospring5.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apress.prospring5.ch3.Main.InjectedBean;

public class Main {

    // Spring supports five modes for autowiring .
    //
    // byName: When using byName autowiring, Spring attempts to wire each property to a bean of the same name.
    // So, if the target bean has a property named foo and a foo bean is defined in ApplicationContext, the
    // foo bean is assigned to the foo property of the target.
    //
    // byType: When using byType autowiring, Spring attempts to wire each of the properties on the target
    // bean by automatically using a bean of the same type in ApplicationContext.
    //
    // constructor: This functions just like byType wiring, except that it uses constructors rather than setters to perform the injection.
    // Spring attempts to match the greatest numbers of arguments it can in the constructor.
    // So, if your bean has two constructors, one that accepts a String and one that accepts String and an Integer, and you have both
    // a String and an Integer bean in your ApplicationContext, Spring uses the two-argument constructor.
    //
    // default: Spring will choose between the constructor and byType modes automatically.
    // If your bean has a default (no-arguments) constructor, Spring uses byType; otherwise, it uses constructor.
    //
    // no: This is the default.
    //
    //
    // When To use Autowiring?
    //
    // In most cases, the answer to the question of whether you should use autowiring is definitely no!
    // Autowiring can save you time in small applications, but in many cases, it leads to bad practices and is inflexible in large applications.
    // Using byName seems like a good idea, but it may lead you to give your classes artificial property names so that you can take advantage
    // of the autowiring functionality.

    // Example

    public class InjectedBean {

    }

    // Field-based dependency injection
    @Component
    public class FieldBasedInjection {

	@Autowired // Don't do that!!!!
	private InjectedBean injectedBean;

    }

    // Setter-based dependency injection
    @Component
    public class SetterBasedInjection {

	private InjectedBean injectedBean;

	@Autowired
	public void setInjectedBean(InjectedBean injectedBean) {
	    this.injectedBean = injectedBean;
	}

    }

    // Constructor-based dependency injection
    @Component
    public class ConstructorBasedInjection {

	private final InjectedBean injectedBean; // thread safe!

	@Autowired // Optional since Spring 4
	public ConstructorBasedInjection(InjectedBean injectedBean) {
	    this.injectedBean = injectedBean;
	}

    }

    // Generally constructor injection should be favored (no need to annotate the constructor with @Autowired since Spring 4)
    // if few fields, otherwise setters should be the way.
    // Both ways don't have all drawbacks mentioned above.
}
