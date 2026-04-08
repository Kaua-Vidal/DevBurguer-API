package com.stackburguer.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.stackburguer.api.repositories.jpa")  //onde está o repo do Postgres
@EnableMongoRepositories(basePackages = "com.stackburguer.api.repositories.mongo")    //Onde está o repo do Mongo
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
