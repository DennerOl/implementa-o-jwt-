package com.devsuperior.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
/* aqui permito qualquer requisição que vier para back-end	
 * pois irei implementar o controle de acesso por rota
 */		http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
		return http.build();
	}

}

