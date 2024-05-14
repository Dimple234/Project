package com.cloth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MyConfig {
	
	@Autowired
	CustomAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Bean
	UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//		http.authorizeRequests(req->{
//			try {
//				req.requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**").hasRole("USER")
//						.requestMatchers("/**").permitAll().and().formLogin().and().csrf().disable();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
		http.authorizeRequests(req -> {
		    try {
		        req
		            .requestMatchers("/admin/**").hasRole("ADMIN") // Require ADMIN role for paths starting with "/admin/"
		            .requestMatchers("/user/**").hasRole("USER")  // Require USER role for paths starting with "/user/"
		            .requestMatchers("/**").permitAll()           // Allow access to all other paths without authentication
		            .and()
		            .formLogin() 
		            .loginPage("/signin")
		            .loginProcessingUrl("/dologin").successHandler(authenticationSuccessHandler)
		            .and()
		            .csrf().disable();                            // Disable Cross-Site Request Forgery (CSRF) protection
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		});

	//	    }
	return http.build();
    }
}
