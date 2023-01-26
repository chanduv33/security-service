
package com.storesmanagementsystem.springsecuritymicro.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.storesmanagementsystem.springsecuritymicro.config.RestAuthenticationEntryPoint;
import com.storesmanagementsystem.springsecuritymicro.filters.CustomUserPasswordAuthFilter;
import com.storesmanagementsystem.springsecuritymicro.handlers.MyLogoutSuccessHandler;
import com.storesmanagementsystem.springsecuritymicro.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private UserService userService;
	private Environment environment;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private MyLogoutSuccessHandler myLogoutSuccessHandler;

	@Autowired
	public AuthenticationFailureHandler myAuthFailureHandler;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Bean
	public UsernamePasswordAuthenticationFilter getUsernamePasswordAuthenticationFilter() throws Exception {
		CustomUserPasswordAuthFilter filter = new CustomUserPasswordAuthFilter(userService, environment);
		filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		filter.setAuthenticationFailureHandler(myAuthFailureHandler);
		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		http.csrf().disable();

//				.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/basicauth").permitAll().and().httpBasic().and()

		http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
				.addFilterBefore(getUsernamePasswordAuthenticationFilter(), CustomUserPasswordAuthFilter.class).logout()
				.logoutSuccessHandler(myLogoutSuccessHandler);
		http.headers().frameOptions().disable();
	}

	@Autowired
	public WebSecurity(UserService userService, Environment environment, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userService = userService;
		this.environment = environment;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

}
