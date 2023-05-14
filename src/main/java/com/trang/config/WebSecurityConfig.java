package com.trang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;




@EnableWebSecurity
public class WebSecurityConfig {

//	@Bean
//	public UserDetailsService userService() {
//		return new UserDetailsServiceImpl();
//	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/product/**","/user/**","/add-product-cart","/view-cart").hasAnyRole("ADMIN", "USER")
		.antMatchers("/resources/**")
				.permitAll()
				.and()
				.formLogin()
				.loginPage("/login")
				.usernameParameter("username").passwordParameter("password")
				.loginProcessingUrl("/login").defaultSuccessUrl("/home").permitAll().failureUrl("/loginFailed")
				.and()
				.logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll()
				.and()
				.csrf().disable();

		
		return http.build();
	}
}
