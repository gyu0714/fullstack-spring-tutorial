package com.spring.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.spring.entity"})
@EnableJpaRepositories(basePackages = {"com.spring.repository"})
@ComponentScan(basePackages = {"com.spring.service", "com.spring.controller", "com.spring.dto"})
public class Step15UploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(Step15UploadApplication.class, args);
	}

}
