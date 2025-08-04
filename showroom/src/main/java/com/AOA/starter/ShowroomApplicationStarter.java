package com.AOA.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@EntityScan(basePackages = {"com.AOA"})
@EnableJpaRepositories(basePackages = {"com.AOA"})
@ComponentScan(basePackages = {"com.AOA"})
@EnableScheduling
@SpringBootApplication
public class ShowroomApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(ShowroomApplicationStarter.class, args);
	}

}
