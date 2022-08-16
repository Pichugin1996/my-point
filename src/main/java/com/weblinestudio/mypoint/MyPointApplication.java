package com.weblinestudio.mypoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("/application-dev.properties")
public class MyPointApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyPointApplication.class, args);
	}

}
