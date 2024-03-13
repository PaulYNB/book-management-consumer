package com.paul.book.management.consumer;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//To extends SpringBootServletInitializer, and add ProviderBootApplication into SpringApplication.sources
public class ConsumerSpringBootServletInitializer extends SpringBootServletInitializer {
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    	System.out.println("ConsumerSpringBootServletInitializer.configure()!!!");
        return builder.sources(ConsumerBootApplication.class);
    }	
  
}
