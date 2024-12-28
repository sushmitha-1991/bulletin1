package com.dsp.bulletin1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.dsp.bulletin1.entity.feignclient")

public class Bulletin1Application {
	public static void main(String[] args) {
		SpringApplication.run(Bulletin1Application.class, args);
	}
}
