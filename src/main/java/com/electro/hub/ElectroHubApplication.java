package com.electro.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication

public class ElectroHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectroHubApplication.class, args);
		System.out.println("Hello Spring boot");
	}

}
