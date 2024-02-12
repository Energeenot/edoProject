package com.example.edo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@AutoConfiguration

public class EdoProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdoProjectApplication.class, args);
	}
	//TODO: разобраться с css validator
	//TODO: нужна ли мне бд? стоит попробовать использовать облако, потому что при загрузке данных в бд, записывается только путь к файлам, а если с другого пк попробуют запустить проект, то файлы не загрузятся
}
