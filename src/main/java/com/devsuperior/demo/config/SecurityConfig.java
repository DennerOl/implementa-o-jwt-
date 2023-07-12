package com.devsuperior.demo.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	
/* gera um codigo hash ou seja criptografa a senha do
 * usuario que estava direto no  banco de dados 
 */
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Profile("test")
	@Order(1)
	public SecurityFilterChain h2SecurityFilterChain(HttpSecurity http) throws Exception {

		http.securityMatcher(PathRequest.toH2Console()).csrf(csrf -> csrf.disable())
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
		return http.build();
	}
	
	@Bean
	@Order(2)
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
/* aqui permito qualquer requisição que vier para back-end	
 * pois irei implementar o controle de acesso por rota
 */		http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
		return http.build();
	}

	
	
}

