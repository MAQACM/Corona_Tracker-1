package com.example.CoronaTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoronaTracker1Application {

	public static void main(String[] args) {
		SpringApplication.run(CoronaTracker1Application.class, args);
	}

}
