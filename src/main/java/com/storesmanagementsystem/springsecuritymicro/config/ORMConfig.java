package com.storesmanagementsystem.springsecuritymicro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class ORMConfig {
	
	//LocalEntityManagerFactoryBean helps us to integrate hibernate and spring
	
	@Bean
	public LocalEntityManagerFactoryBean getBean() {
		LocalEntityManagerFactoryBean bean = new LocalEntityManagerFactoryBean();
		bean.setPersistenceUnitName("stores-unit");
		return bean;
	}
	
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	 @Bean
	    @Primary
	    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
	        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
	        return builder.modulesToInstall(new JavaTimeModule());
	    }

	
}
