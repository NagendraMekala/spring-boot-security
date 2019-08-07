package com.mng.java.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
/*	   PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	   auth.inMemoryAuthentication().withUser("nag").password(encoder.encode("nag")).roles("user");
	   auth.inMemoryAuthentication().withUser("admin").password(encoder.encode("admin")).roles("user","admin");*/
	   
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication().withUser("nag").password(encoder.encode("nag")).roles("user")
		        .and().withUser("admin").password(encoder.encode("admin")).roles("user", "admin");

	}

@Override
protected void configure(HttpSecurity http) throws Exception {
/*      http.csrf().disable().authorizeRequests().antMatchers("/product/user/**").hasAnyRole("user", "admin").and().formLogin();
      http.csrf().disable().authorizeRequests().antMatchers("/product/admin/**").hasAnyRole("admin").and().formLogin();*/
      
	 	http.csrf().disable().authorizeRequests().antMatchers("/product/user/**").hasAnyRole("user", "admin").and()
				.authorizeRequests().antMatchers("/product/admin/**").hasAnyRole("admin").and().formLogin();
	}
}
