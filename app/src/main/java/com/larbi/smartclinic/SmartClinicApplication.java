package com.larbi.smartclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
//Explicitly indicate location of JPA repositories
@EnableJpaRepositories(basePackages = "com.larbi.smartclinic.repository.mysql")
//Explicitly indicate location of Mongo repositories
@EnableMongoRepositories(basePackages = "com.larbi.smartclinic.repository.mongo")
public class SmartClinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartClinicApplication.class, args);
	}

}
