package com.springboot.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDto {
	
	@JsonProperty(value = "productId")
    private int id;
	
	@JsonProperty(value = "productName")
    private String productName;
    
	@JsonProperty(value = "quantity")
    private int quantity;
	
	@JsonProperty(value = "shippedInformation")
	private ShippedInfoDto[] shippedInfoDto;
	
	public ProductDto() {
		
	}

	public ProductDto(int id, int quantity, String productName, ShippedInfoDto[] shippedInfoDto) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.productName = productName;
		this.shippedInfoDto = shippedInfoDto;
	}
	
	public ShippedInfoDto[] getShippedInfoDto() {
		return shippedInfoDto;
	}

	public void setShippedInfoDto(ShippedInfoDto[] shippedInfoDto) {
		this.shippedInfoDto = shippedInfoDto;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
     
}
