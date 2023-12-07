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
	//TODO: проверить работает ли скрипт отправки
	//TODO: сделать так чтобы на странице валидатора footer был снизу
	//TODO: разобраться с css validator
	//TODO: нужна ли мне бд? стоит попробовать использовать облако, потому что при загрузке данных в бд, записывается только путь к файлам, а если с другого пк попробуют запустить проект, то файлы не загрузятся
	//Todo: скорее всего вкладку добавить документ надо скрыть, пока не будет авторизирован пользователь
	//Todo: перенести из валидатора форму для отправки документов
	//todo: разобраться как скрыть вкладки вход и регистрация из хедера, и чтобы появлялась вкладка личный кабинет, когда пользователь авторизован

}
