package com.anudeep.complaint_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class ComplaintSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComplaintSystemApplication.class, args);
	}

}
