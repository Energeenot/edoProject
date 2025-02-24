package com.example.edo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.edo.feign")
public class EdoProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdoProjectApplication.class, args);
	}
}
