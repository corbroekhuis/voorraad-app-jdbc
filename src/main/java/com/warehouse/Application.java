package com.warehouse;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static ApplicationContext applicationContext;

	public static void main(String[] args){
		//System.setProperty("javax.net.debug", "ssl:handshake");

		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

		for( String beanDefinitionName: applicationContext.getBeanDefinitionNames()){
			// System.out.println( beanDefinitionName);
			if( beanDefinitionName.endsWith("DataSource")){
				Object object = applicationContext.getBean( beanDefinitionName);
				System.out.println("Stop hier");
			}
		}
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
