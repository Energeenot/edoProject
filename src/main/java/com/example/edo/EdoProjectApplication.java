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
	//TODO: сделать так чтобы на странице валидатора footer был снизу
	//TODO: разобраться с css validator
	//TODO: нужна ли мне бд? стоит попробовать использовать облако, потому что при загрузке данных в бд, записывается только путь к файлам, а если с другого пк попробуют запустить проект, то файлы не загрузятся
	//Todo: настроить вкладки так чтобы неавторизованный пользователь не мог заходить на страницы валидатора и нового документа
	//todo: разобраться как скрыть вкладки вход и регистрация из хедера, и чтобы появлялась вкладка личный кабинет, когда пользователь авторизован
// TODO: 07.12.2023 разобраться как mvcConfig-у передать стили и картинки, потому что иначе securityConfig работает не совсем корректно
}
