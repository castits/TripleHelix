package com.triplehelix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf().disable()
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/auth/**", "/error").permitAll()
	            .anyRequest().authenticated()
	        )
	        .logout(logout -> logout
	            .logoutUrl("/api/auth/logout")
	            .invalidateHttpSession(true)
	            .deleteCookies("JSESSIONID")
	            .logoutSuccessHandler((request, response, authentication) -> {
	                response.setStatus(HttpServletResponse.SC_OK);
	                response.getWriter().write("Logout successful");
	            })
	        )
	        .httpBasic().disable()
	        .formLogin().disable();
	    return http.build();
	}

	
}
