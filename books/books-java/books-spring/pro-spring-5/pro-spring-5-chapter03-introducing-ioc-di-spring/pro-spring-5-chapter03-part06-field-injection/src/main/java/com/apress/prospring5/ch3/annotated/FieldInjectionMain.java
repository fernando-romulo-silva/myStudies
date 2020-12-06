package com.apress.prospring5.ch3.annotated;

import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Created by iuliana.cosmina on 2/15/17.
 */
public class FieldInjectionMain {

    // But there are drawbacks, and this is why using field injection is usually avoided.
    //
    // * Although it is easy to add dependencies this way, we must be careful not to violate the single responsibility principle.
    //
    // * Using field injections, it can become unclear what type of dependency is really needed and if the dependency is mandatory or not.
    //
    // * Field injection cannot be used for final fields. This type of fields can only be initialized using constructor injection.
    //
    // * Field injection introduces difficulties when writing tests as the dependencies have to be injected manually.

    public static void main(String... args) {

	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
	ctx.load("classpath:spring/app-context.xml");
	ctx.refresh();

	Singer singerBean = ctx.getBean(Singer.class);
	singerBean.sing();

	ctx.close();
    }
}
