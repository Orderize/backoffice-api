package com.orderize.backoffice_api;

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
		SpringApplication.run(BackofficeApiApplication.class, args);

	}

}
