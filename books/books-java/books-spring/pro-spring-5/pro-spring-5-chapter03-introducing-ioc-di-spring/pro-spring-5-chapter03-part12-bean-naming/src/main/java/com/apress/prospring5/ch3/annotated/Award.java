package com.apress.prospring5.ch3.annotated;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

// This annotation is used to declare aliases for annotation attributes, and most Spring annotations make use of it. 
/**
 * Created by iuliana.cosmina on 2/19/17.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Award {

	@AliasFor("prize")
	String[] value() default {};

	@AliasFor("value")
	String[] prize() default {};
}
