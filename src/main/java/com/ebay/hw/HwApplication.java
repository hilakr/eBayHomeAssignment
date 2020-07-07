package com.ebay.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class HwApplication {

	public static void main(String[] args) {
		SpringApplication.run(HwApplication.class, args);
	}

}
