package com.springboot.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShippedInfoDto {

	private String shippedId;
	private int shippedValue;

	@JsonProperty(value = "airInformation")
	private AirCraftDto[] airCraftDto;
	
	public ShippedInfoDto() {

	}

	public ShippedInfoDto(String shippedId, int shippedValue, AirCraftDto[] airCraftDto) {
		super();
		this.shippedId = shippedId;
		this.shippedValue = shippedValue;
		this.airCraftDto = airCraftDto;
	}

	public AirCraftDto[] getAirCraftDto() {
		return airCraftDto;
	}

	public void setAirCraftDto(AirCraftDto[] airCraftDto) {
		this.airCraftDto = airCraftDto;
	}

	public String getShippedId() {
		return shippedId;
	}

	public void setShippedId(String shippedId) {
		this.shippedId = shippedId;
	}

	public int getShippedValue() {
		return shippedValue;
	}

	public void setShippedValue(int shippedValue) {
		this.shippedValue = shippedValue;
	}

}
