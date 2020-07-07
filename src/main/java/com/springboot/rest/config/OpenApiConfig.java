package com.springboot.rest.config;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("User Management Microservice")
				.description("A view of list of all User Management Microservice")
				.termsOfService("https://www.9exceptions.nl/nl-nl.html")
				.contact(new io.swagger.v3.oas.models.info.Contact()
				.email("dheeraj.kumar@gmail.com")
				.name("User - User Management Microservice"))
				.version("1.0"));
	}

	@Bean
	public GroupedOpenApi v1OpenApi() {
		String[] packagesToscan = { "com.springboot.rest.controller.v1" };
		return GroupedOpenApi.builder().setGroup("v1 version").packagesToScan(packagesToscan).build();
	}

	@Bean
	public GroupedOpenApi v2OpenApi() {
		String[] packagesToscan = { "com.springboot.rest.controller.v2" };
		return GroupedOpenApi.builder().setGroup("v2 version").packagesToScan(packagesToscan).build();
	}

}