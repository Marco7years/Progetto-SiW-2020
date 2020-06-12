package com.example.demo.authentication;

import javax.sql.DataSource;
import static com.example.demo.model.Credentials.ADMIN_ROLE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource datasource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/", "/index", "/login", "/users/register").permitAll()
				.antMatchers(HttpMethod.POST, "/login", "/users/register").permitAll()
				.antMatchers(HttpMethod.GET, "admin/**").hasAnyAuthority(ADMIN_ROLE) 
				.antMatchers(HttpMethod.POST, "admin/**").hasAnyAuthority(ADMIN_ROLE) 
				.anyRequest().authenticated()
				.and().formLogin()
				.defaultSuccessUrl("/home")
				.and().logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/index")
				.invalidateHttpSession(true)
				.clearAuthentication(true).permitAll();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth	
				.jdbcAuthentication()
				.dataSource(this.datasource)
				.authoritiesByUsernameQuery("select user_name, role from credentials where user_name=?")
				.usersByUsernameQuery("select user_name, password, 1 as enabled from credentials where user_name=?");
				
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
