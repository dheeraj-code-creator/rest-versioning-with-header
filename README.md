# all-in-one-java8-features

http://localhost:9091/user-management/swagger-ui.html


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
