package com.weblinestudio.mypoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@PropertySource("classpath:application-dev.properties")
public class MyPointApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyPointApplication.class, args);
	}

}
