package com.msa.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MsaArchitectureEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsaArchitectureEurekaApplication.class, args);
	}

}
