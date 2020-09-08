package com.msa.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class MsaArchitectureConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsaArchitectureConfigApplication.class, args);
	}

}
