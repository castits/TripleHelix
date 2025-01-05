package com.triplehelix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.triplehelix.services.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
	
	@Lazy
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf().disable()
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/**", "/login", "/logout", "/pub/auth/**").permitAll()
	            .anyRequest().authenticated()
	        )
	        .exceptionHandling()
	            .authenticationEntryPoint((request, response, authException) -> {
	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                response.getWriter().write("Access Denied: Authentication Required");
	            })
	        .and()
	        .formLogin(form -> form
        		.loginPage("/login")
        		.defaultSuccessUrl("/", true)
        		.permitAll()
        	)
	        .logout(logout -> logout
	            .logoutUrl("/pub/auth/logout")
	            .invalidateHttpSession(false)
	            .deleteCookies("JSESSIONID")
	            .logoutSuccessHandler((request, response, authentication) -> {
	                response.setStatus(HttpServletResponse.SC_OK);
	                response.getWriter().write("Logout successful");
	            })
	        )
	        .httpBasic();
	    return http.build();
	}

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
        return authenticationManagerBuilder.build();
    }

}
