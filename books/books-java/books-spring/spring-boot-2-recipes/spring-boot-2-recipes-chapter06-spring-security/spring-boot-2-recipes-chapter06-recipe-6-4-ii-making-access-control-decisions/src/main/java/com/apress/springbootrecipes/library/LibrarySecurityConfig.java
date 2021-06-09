package com.apress.springbootrecipes.library;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
public class LibrarySecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests() //
			.antMatchers(HttpMethod.GET, "/books*") //
			/*--*/.hasAnyRole("USER", "GUEST") //
			.antMatchers(HttpMethod.POST, "/books*") //
			/*--*/.hasRole("USER") //
			.antMatchers(HttpMethod.DELETE, "/books*")//
			/*--*/.access("hasRole('ROLE_ADMIN') or @accessChecker.hasLocalAccess(authentication)");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	final var adminUser = User//
			.withUsername("admin@books.io") //
			.password("{noop}secret") //
			.roles("USER", "ADMIN") //
			.build();

	final var normalUser = User //
			.withUsername("marten@books.io") //
			.password("{noop}user") //
			.roles("USER") //
			.build();

	final var disabledUser = User //
			.withUsername("john@books.io") //
			.password("{noop}user") //
			.disabled(true) //
			.roles("USER") //
			.build();

	auth.inMemoryAuthentication().withUser(adminUser).withUser(normalUser).withUser(disabledUser);
    }
}
