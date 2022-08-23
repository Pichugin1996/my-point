package com.weblinestudio.mypoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-dev.properties")
public class MyPointApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyPointApplication.class, args);
	}

}
