package com.orderize.backoffice_api;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info = @Info(title = "Backoffice api", version = "1", description = "Api Backoffice Orderize"))
public class BackofficeApiApplication {

	public static void main(String[] args) {
		try {
			Dotenv dotenv = Dotenv.load();
			dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
			System.out.println("DEBUG: Variáveis de ambiente do .env carregadas via System.setProperty.");

		}catch (Exception e) {
			System.out.println("ERRO ao carregar as variáveis de ambiente do .env no main" + e.getMessage());
			e.printStackTrace();
		}

		SpringApplication.run(BackofficeApiApplication.class, args);

	}

}
