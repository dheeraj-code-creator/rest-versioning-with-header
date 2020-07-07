# rest-versioning-with-header

http://localhost:9091/user-management/swagger-ui/index.html?configUrl=%2Fuser-management%2Fv3%2Fapi-docs%2Fswagger-config 

=======================================================================================================================================

<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.0.BUILD-SNAPSHOT</version>
		<relativePath />
		<!-- lookup parent from repository -->
	</parent>
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<springfox-swagger-version>2.9.2</springfox-swagger-version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-ui</artifactId>
			<version>1.4.1</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
			<!-- <version>2.0.0.RELEASE</version> -->
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.modelmapper/modelmapper -->
		<dependency>
			<groupId>org.modelmapper</groupId>
			<artifactId>modelmapper</artifactId>
			<version>2.3.0</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<!-- https://mvnrepository.com/artifact/org.apache.commons/commons-lang3 -->
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-lang3</artifactId>
			<version>3.10</version>
		</dependency>


	</dependencies>
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
===============================================================================================================

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
=============================================================================================================
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


/**
 * This filter checks whether 'X-Accept-Version' header is present or not in 
 * API request and if not then it redirects it to latest version of an API by 
 * adding 'X-Accept-Version' header with latest version value.</br>
 * </br>
 * {@link RequestMappingHandlerMapping} provides the details of all the api's present in current service.</br>
 * When any api request comes to service without 'X-Accept-Version' header, 
 * {@link APIVersionFilter#doFilter(ServletRequest, ServletResponse, FilterChain)} method
 * matches it's signature with api's of current service and finds out it's all 'X-Accept-Version' header versions.</br>
 * </br>
 * After finding out all the version of requested api, {@link APIVersionFilter#doFilter(ServletRequest, ServletResponse, FilterChain)} method
 * adds 'X-Accept-Version' header in the requested api with latest version value.
 *
 */
@Component
public class APIVersionFilter implements Filter {

	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		httpRequest.setAttribute("X-Accept-Version",  httpRequest.getHeader("X-Accept-Version"));
		httpRequest.setAttribute("X-Api-Version", httpRequest.getHeader("X-Accept-Version"));
		if (null == httpRequest.getHeader("X-Accept-Version")) {
			Set<String> availableVersions = new TreeSet<>();
			requestMappingHandlerMapping.getHandlerMethods().forEach((requestMappingInfo, handlerMethod) -> {
				if (null != requestMappingInfo.getPatternsCondition().getMatchingCondition(httpRequest)
						&& null != requestMappingInfo.getMethodsCondition().getMatchingCondition(httpRequest)
						&& null != requestMappingInfo.getParamsCondition().getMatchingCondition(httpRequest)
						&& null != requestMappingInfo.getConsumesCondition().getMatchingCondition(httpRequest)
						&& null != requestMappingInfo.getProducesCondition().getMatchingCondition(httpRequest)) {
					requestMappingInfo.getHeadersCondition().getExpressions().forEach(header -> {
						if (header.getName().equals("X-Accept-Version")) {
							availableVersions.add(header.getValue());
						}
					});
				}
			});
			Optional<String> latestVersion = ((TreeSet<String>)availableVersions).descendingSet().stream().findFirst();
			if(latestVersion.isPresent()) {
				 MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(httpRequest);
				 mutableRequest.putHeader("X-Accept-Version",latestVersion.get());
				 mutableRequest.setAttribute("X-Api-Version", mutableRequest.getHeader("X-Accept-Version"));
				 request = mutableRequest;
			}	
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
		
}

==========================================================================================================================

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Wrapper class to wrap HttpServletRequest to expose method to add a new
 * header.
 *
 */
@Component
@Primary
public class MutableHttpServletRequest extends HttpServletRequestWrapper {

	private final Map<String, String> customHeaders;

	public MutableHttpServletRequest(HttpServletRequest request) {
		super(request);
		this.customHeaders = new HashMap<>();
	}

	/**
	 * Exposing method to add header.
	 * 
	 * @param name
	 * @param value
	 */
	public void putHeader(String name, String value) {
		this.customHeaders.put(name, value);
	}

	/**
	 * Method to get header.
	 * 
	 * @param name
	 * @return header
	 */
	@Override
	public String getHeader(String name) {
		String headerValue = customHeaders.get(name);

		if (null != headerValue) {
			return headerValue;
		}
		return ((HttpServletRequest) getRequest()).getHeader(name);
	}

	/**
	 * Method to get header names.
	 * @return header names
	 */
	@Override
	public Enumeration<String> getHeaderNames() {
		Set<String> headerSet = new HashSet<>(customHeaders.keySet());

		Enumeration<String> headerNames = ((HttpServletRequest) getRequest()).getHeaderNames();
		while (headerNames.hasMoreElements()) {
			headerSet.add(headerNames.nextElement());
		}
		return Collections.enumeration(headerSet);
	}
}
=================================================================================================================================
