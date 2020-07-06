package com.springboot.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto{
	
	@JsonProperty(value = "userId")
	private String userId=null;
	
	@JsonProperty(value = "userName")
	private String userName=null;
	
	@JsonProperty(value = "userProductsInfo")
	private ProductDto productDto;
	
	public UserDto() {
	
	}

	public UserDto(String userId, String userName, ProductDto productDto) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.productDto = productDto;
	}
	
	public ProductDto getProductDto() {
		return productDto;
	}

	public void setProductDto(ProductDto productDto) {
		this.productDto = productDto;
	}
	 
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
