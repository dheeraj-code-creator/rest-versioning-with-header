package com.springboot.rest.filter;
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
