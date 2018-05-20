package com.amountaggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * This is the main Spring Boot application class.
 */

@SpringBootApplication(scanBasePackages= {"com.amountaggregator"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
