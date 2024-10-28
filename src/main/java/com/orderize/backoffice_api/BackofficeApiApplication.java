package com.orderize.backoffice_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Backoffice api", version = "1", description = "Api braba"))
public class BackofficeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackofficeApiApplication.class, args);

	}

}
