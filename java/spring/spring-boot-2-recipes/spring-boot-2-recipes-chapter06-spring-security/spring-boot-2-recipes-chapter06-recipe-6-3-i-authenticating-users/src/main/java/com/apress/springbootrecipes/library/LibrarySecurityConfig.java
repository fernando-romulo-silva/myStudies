package com.apress.springbootrecipes.library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LibrarySecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    public LibrarySecurityConfig() {
	super(true); // disable default configuration
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
	registry.addViewController("/login.html").setViewName("login");
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

	auth.inMemoryAuthentication() //
			.withUser(adminUser) //
			.withUser(normalUser) //
			.withUser(disabledUser) //
	; //
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.securityContext() //
			.and().exceptionHandling() //
			.and().servletApi() //
			.and().httpBasic() //
			//
			.and().logout() //
			/*-*/.logoutSuccessUrl("/") //
			//
			.and().headers() //
			.and().csrf() //
			//
			.and().anonymous() //
			/*-*/.principal("guest") //
			/*-*/.authorities("ROLE_GUEST") //
			//
			.and().rememberMe() //
			//
			.and().formLogin() //
			/*-*//*-*/.loginPage("/login.html") //
			/*-*//*-*/.defaultSuccessUrl("/") //
			/*-*//*-*/.failureUrl("/login.html?error=true") //
			/*-*//*-*/.permitAll() //
			//
			.and().authorizeRequests() //
			/*-*/.mvcMatchers("/").permitAll() //
			/*-*/.anyRequest().authenticated(); //
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

    @Controller
    static class FaviconController {

	@GetMapping("favicon.ico")
	@ResponseBody
	void returnNoFavicon() {
	}
    }
}
