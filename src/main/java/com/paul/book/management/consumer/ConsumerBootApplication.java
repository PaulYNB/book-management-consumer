package com.paul.book.management.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@EnableDubbo
@SpringBootApplication
public class ConsumerBootApplication
{
	public static void main(String[] args) {
		ConfigurableApplicationContext springCtx = 
				SpringApplication.run(ConsumerBootApplication.class);   

	    String[] beanDefinitionNames = springCtx.getBeanDefinitionNames();   
	    for (String beanDefinitionName : beanDefinitionNames) {
		    System.out.println("beanDefinitionName: " + beanDefinitionName);
	    }		
	}

}
