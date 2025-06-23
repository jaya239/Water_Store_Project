package com.example.Water_Store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WaterStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterStoreApplication.class, args);
	}

}
