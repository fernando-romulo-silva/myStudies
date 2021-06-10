package com.apress.springbootrecipes.order;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class OrdersSecurityConfiguration {

    public OrdersSecurityConfiguration() {
	super();
    }

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
	return http.authorizeExchange() //
			/*-*/.pathMatchers("/").permitAll() //
			//
			/*-*/.pathMatchers("/orders*").hasRole("USER") //
			// or/and
			/*-*/.pathMatchers("/orders*").access(this::ordersAllowed) //
			//
			/*-*/.anyExchange().authenticated() //
			.and().httpBasic() //
			.and().formLogin() //
			.and().headers() //
			.and().logout() //
			.and().csrf() //
			.and().build(); //
    }

    public Mono<AuthorizationDecision> ordersAllowed(Mono<Authentication> authentication, AuthorizationContext context) {
	return authentication //
			.map(a -> a.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) //
			.map(AuthorizationDecision::new);
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
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

	return new MapReactiveUserDetailsService(adminUser, normalUser);
    }
}
