package com.springboot.rest.config;
import static com.google.common.collect.Lists.newArrayList;

import java.awt.print.Pageable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket v1Api() {
	    return new Docket(DocumentationType.SWAGGER_2)
	            .groupName("v1")
	            .select()
	            .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
	            .apis(RequestHandlerSelectors.basePackage("com.springboot.rest.controller.v1"))
	            .paths(PathSelectors.any())
	            .build()
	            .apiInfo(apiInfo("v1"))
	            .useDefaultResponseMessages(false)
	            .ignoredParameterTypes(Pageable.class)
	            .globalResponseMessage(
	                    RequestMethod.GET,
	                    newArrayList(new ResponseMessageBuilder().code(500).message("").build()));
	}

	@Bean
	public Docket v2Api() {
	    return new Docket(DocumentationType.SWAGGER_2)
	            .groupName("v2")
	            .select()
	            .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
	            .apis(RequestHandlerSelectors.basePackage("com.springboot.rest.controller.v2"))
	            .paths(PathSelectors.any())
	            .build()
	            .apiInfo(apiInfo("v2"))
	            .useDefaultResponseMessages(false)
	            .ignoredParameterTypes(Pageable.class)
	            .globalResponseMessage(
	                    RequestMethod.GET,
	                    newArrayList(new ResponseMessageBuilder().code(500).message("").build()));
	}

	private ApiInfo apiInfo(final String version) {		
	    return new ApiInfoBuilder()
	            .title("User-Management API")
	            .description("A view of list of all users")
	            .termsOfServiceUrl("https://www.user-management.nl/nl-nl.html")
	            .version(version)
	            .build();
	}

}