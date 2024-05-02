package com.rockbite.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseApplication.class, args);
	}

}

//todo notification/observer pattern, finish implementation, fix observer adding logic
//todo fix argument for update methods, print info
//todo ensure every controller method works properly
