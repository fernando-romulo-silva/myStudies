package com.apress.springbootrecipes.library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
public class LibrarySecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	final var adminUser = User.withUsername("admin@books.io").password("{noop}secret").roles("USER", "ADMIN").build();

	final var normalUser = User.withDefaultPasswordEncoder()//
		.username("marten@books.io") //
		.password("user") //
		.authorities("USER").build(); //
	
	final var disabledUser = User.withDefaultPasswordEncoder() //
		.username("john@books.io") //
		.password("user") //
		.disabled(true) //
		.authorities("USER").build(); //

	auth.inMemoryAuthentication() //
		.withUser(adminUser) //
		.withUser(normalUser) //
		.withUser(disabledUser) //
	; //
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
	StrictHttpFirewall firewall = new StrictHttpFirewall();
	firewall.setAllowUrlEncodedSlash(true);
	return firewall;
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
	super.configure(web);
	web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }
}
