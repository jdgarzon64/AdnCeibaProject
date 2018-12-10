package com.ceiba.AdnProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class AdnProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdnProjectApplication.class, args);
	}
}
