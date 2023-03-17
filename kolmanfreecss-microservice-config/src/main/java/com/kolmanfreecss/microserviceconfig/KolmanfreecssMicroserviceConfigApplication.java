package com.kolmanfreecss.microserviceconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class KolmanfreecssMicroserviceConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(KolmanfreecssMicroserviceConfigApplication.class, args);
	}

}
