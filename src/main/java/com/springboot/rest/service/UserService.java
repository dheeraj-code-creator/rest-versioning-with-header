package com.springboot.rest.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import com.springboot.rest.dto.AirCraftDto;
import com.springboot.rest.dto.ProductDto;
import com.springboot.rest.dto.ShippedInfoDto;
import com.springboot.rest.dto.UserDto;

@Service
public class UserService {

	public List<UserDto> getAllUserInfo() {
		List<UserDto> userList = new ArrayList<>();
		UserDto user1 = firstUserInfo();
		UserDto user2 = secondUserInfo();
		UserDto user3 = thirdUserInfo();

		userList.add(user1);
		userList.add(user2);
		userList.add(user3);

		// Task 1: convert product name to upper case for all user
		userList = displayProductNameUpperCase(userList);

		// Task 2: add +10 to product quantity for all user
		userList = displayProductIncreasedQuantity(userList);

		// Task 3: add +A to shipped id for all user
		userList = displayShippedIncreasedId(userList);

		// Task 4: add +10 to shipped info last array value for all user
		userList = displayShippedInfoLastArrayUpdatedValue(userList);

		// Task 5: add +100 to air number for all user
		userList = displayUpdatedAirNumber(userList);

		// Task 6: add +10 to Airinformation last array value for all user
		userList = displayAirInfoForLastArrayUpdatedValue(userList);
		
		// Task 7: extract airInformation last array values
		   //created logic in two ways (1. with predifined class, 2. with internal logic)
		userList = extractAirInformationLastValues(userList);

		return userList;
	}

	// with predifined  ArrayUtil class
	/*private List<UserDto> extractAirInformationLastValues(List<UserDto> userList) {
		userList.stream().map(userDto->{
			ShippedInfoDto[] shippedInfo = userDto.getProductDto().getShippedInfoDto();
			for(int i=0; i<shippedInfo.length; i++) {
				AirCraftDto[] airCraft = shippedInfo[i].getAirCraftDto();
				for(int j=0; j<airCraft.length; j++) {
					if(j==airCraft.length-1) {
						break;
					}
					airCraft = ArrayUtils.remove(airCraft, 0);
				}
				shippedInfo[i].setAirCraftDto(airCraft);
			}
			userDto.getProductDto().setShippedInfoDto(shippedInfo);
			return userDto;
		}).collect(Collectors.toList());
		return userList;
	}*/
	
	// with conversion of list and array logic 
	private List<UserDto> extractAirInformationLastValues(List<UserDto> userList) {
		userList.stream().map(userDto->{
			ShippedInfoDto[] shippedInfo = userDto.getProductDto().getShippedInfoDto();
			for(int i=0; i<shippedInfo.length; i++) {
				AirCraftDto[] airCraft = shippedInfo[i].getAirCraftDto();
				// below line remove unsopperted method error
				List<AirCraftDto> list = new ArrayList<AirCraftDto>(Arrays.asList(airCraft));
				int temp = list.size();
				for(int j=0; j<temp-1; j++) {
					list.remove(0);
					airCraft = new AirCraftDto[list.size()];
					airCraft = list.toArray(airCraft);
				}
				shippedInfo[i].setAirCraftDto(airCraft);
			}
			userDto.getProductDto().setShippedInfoDto(shippedInfo);
			return userDto;
		}).collect(Collectors.toList());
		return userList;
	}
	
	private List<UserDto> displayAirInfoForLastArrayUpdatedValue(List<UserDto> userList) {
		userList.stream().map(userDto -> {
			ProductDto pDto = userDto.getProductDto();
			ShippedInfoDto[] sDto = pDto.getShippedInfoDto();
			for (int i = 0; i < sDto.length; i++) {
				AirCraftDto[] aDto = sDto[i].getAirCraftDto();
				AirCraftDto dto = aDto[aDto.length - 1];
				if (i > 0) {
					int updatedValue = dto.getNumber();
					dto.setNumber(updatedValue - 10);
				}
				int updatedValue = dto.getNumber();
				dto.setNumber(updatedValue + 10);
				aDto[aDto.length - 1] = dto;
				sDto[i].setAirCraftDto(aDto);
				pDto.setShippedInfoDto(sDto);
				userDto.setProductDto(pDto);
			}
			return userDto;
		}).collect(Collectors.toList());
		return userList;
	}

	private List<UserDto> displayUpdatedAirNumber(List<UserDto> userList) {
		userList.stream().map(userDto -> {
			ProductDto pDto = userDto.getProductDto();
			ShippedInfoDto[] sDto = pDto.getShippedInfoDto();
			for (int i = 0; i < sDto.length; i++) {
				AirCraftDto[] aDto = sDto[i].getAirCraftDto();
				for (int j = 0; j < aDto.length; j++) {
					aDto = sDto[i].getAirCraftDto();
					AirCraftDto aObj = aDto[j];
					if (i > 0) {
						int updatedAirNumber = aObj.getNumber() - 100;
						aObj.setNumber(updatedAirNumber);
					}
					int updatedAirNumber = aObj.getNumber() + 100;
					aObj.setNumber(updatedAirNumber);
					aDto[j] = aObj;
				}
				sDto[i].setAirCraftDto(aDto);
			}
			pDto.setShippedInfoDto(sDto);
			userDto.setProductDto(pDto);
			return userDto;
		}).collect(Collectors.toList());
		return userList;
	}

	private List<UserDto> displayShippedInfoLastArrayUpdatedValue(List<UserDto> userList) {
		userList.stream().map(userDto -> {
			ProductDto pdtO = userDto.getProductDto();
			ShippedInfoDto[] sDto = pdtO.getShippedInfoDto();
			ShippedInfoDto sObj = sDto[sDto.length - 1];
			int sValues = sObj.getShippedValue();
			sObj.setShippedValue(sValues + 10);
			sDto[sDto.length - 1] = sObj;
			pdtO.setShippedInfoDto(sDto);
			userDto.setProductDto(pdtO);
			return userDto;
		}).collect(Collectors.toList());
		return userList;
	}

	private List<UserDto> displayShippedIncreasedId(List<UserDto> userList) {
		userList.stream().map(userDto -> {
			ProductDto pDto = userDto.getProductDto();
			ShippedInfoDto[] sDto = pDto.getShippedInfoDto();
			for (int i = 0; i < sDto.length; i++) {
				ShippedInfoDto sObj = sDto[i];
				String updatedId = sObj.getShippedId() + "A";
				sObj.setShippedId(updatedId);
				sDto[i] = sObj;
			}
			pDto.setShippedInfoDto(sDto);
			userDto.setProductDto(pDto);
			return userDto;
		}).collect(Collectors.toList());
		return userList;
	}

	private List<UserDto> displayProductIncreasedQuantity(List<UserDto> userList) {
		userList.stream().map(userDto -> {
			ProductDto pDto = userDto.getProductDto();
			int updatedQuantity = pDto.getQuantity();
			pDto.setQuantity(updatedQuantity + 10);
			userDto.setProductDto(pDto);
			return userDto;
		}).collect(Collectors.toList());
		return userList;
	}

	// product name to upper case with Java 8 stream
	public List<UserDto> displayProductNameUpperCase(List<UserDto> userList) {
		userList = userList.stream().map(userDto -> {
			UserDto userResults = performUserOperation(userDto);
			return userResults;
		}).collect(Collectors.toList());
		return userList;
	}

	private UserDto performUserOperation(UserDto userDto) {
		ProductDto pdto = userDto.getProductDto();
		String pName = pdto.getProductName();
		pdto.setProductName(pName.toUpperCase());
		userDto.setProductDto(pdto);
		return userDto;
	}

	// product name to upper case with for loop
	/*
	 * for (int i = 0; i < userList.size(); i++) { ProductDto pdto =
	 * userList.get(i).getProductDto(); String pName =
	 * userList.get(i).getProductDto().getProductName();
	 * pdto.setProductName(pName.toUpperCase());
	 * userList.get(i).setProductDto(pdto); }
	 */

	// product name to upper case with iterator
	/*
	 * int i = 0; Iterator<UserDto> itr = userList.iterator(); while (itr.hasNext())
	 * { if (i == userList.size()) { break; } ProductDto pdto =
	 * userList.get(i).getProductDto(); String pName =
	 * userList.get(i).getProductDto().getProductName();
	 * pdto.setProductName(pName.toUpperCase());
	 * userList.get(i).setProductDto(pdto); i++; }
	 */

	public UserDto thirdUserInfo() {
		ProductDto product3 = new ProductDto();
		ShippedInfoDto[] shipped3 = new ShippedInfoDto[3];
		AirCraftDto[] airCraft = new AirCraftDto[2];
		AirCraftDto airCraftDto1 = new AirCraftDto();
		AirCraftDto airCraftDto2 = new AirCraftDto();
		ShippedInfoDto shippedDto1 = new ShippedInfoDto();
		ShippedInfoDto shippedDto2 = new ShippedInfoDto();
		ShippedInfoDto shippedDto3 = new ShippedInfoDto();
		airCraftDto1.setNumber(301);
		airCraftDto1.setName("jet-airways3");
		airCraftDto2.setNumber(302);
		airCraftDto2.setName("Indian-airways3");
		airCraft[0] = airCraftDto1;
		airCraft[1] = airCraftDto2;
		shippedDto1.setShippedId("3303");
		shippedDto1.setShippedValue(3001);
		shippedDto1.setAirCraftDto(airCraft);
		shippedDto2.setShippedId("3304");
		shippedDto2.setShippedValue(3002);
		shippedDto2.setAirCraftDto(airCraft);
		shippedDto3.setShippedId("3305");
		shippedDto3.setShippedValue(3003);
		shippedDto3.setAirCraftDto(airCraft);
		shipped3[0] = shippedDto1;
		shipped3[1] = shippedDto2;
		shipped3[2] = shippedDto3;

		product3.setId(3);
		product3.setProductName("machine");
		product3.setQuantity(300);
		product3.setShippedInfoDto(shipped3);
		UserDto user3 = new UserDto();
		user3.setUserId("333");
		user3.setUserName("Third User");
		user3.setProductDto(product3);
		return user3;
	}

	public UserDto secondUserInfo() {
		ProductDto product2 = new ProductDto();
		ShippedInfoDto[] shipped2 = new ShippedInfoDto[3];
		AirCraftDto[] airCraft = new AirCraftDto[2];
		AirCraftDto airCraftDto1 = new AirCraftDto();
		AirCraftDto airCraftDto2 = new AirCraftDto();
		ShippedInfoDto shippedDto1 = new ShippedInfoDto();
		ShippedInfoDto shippedDto2 = new ShippedInfoDto();
		ShippedInfoDto shippedDto3 = new ShippedInfoDto();
		airCraftDto1.setNumber(201);
		airCraftDto1.setName("jet-airways2");
		airCraftDto2.setNumber(202);
		airCraftDto2.setName("Indian-airways2");
		airCraft[0] = airCraftDto1;
		airCraft[1] = airCraftDto2;
		shippedDto1.setShippedId("2222");
		shippedDto1.setShippedValue(2001);
		shippedDto1.setAirCraftDto(airCraft);
		shippedDto2.setShippedId("2233");
		shippedDto2.setShippedValue(2002);
		shippedDto2.setAirCraftDto(airCraft);
		shippedDto3.setShippedId("2244");
		shippedDto3.setShippedValue(2003);
		shippedDto3.setAirCraftDto(airCraft);
		shipped2[0] = shippedDto1;
		shipped2[1] = shippedDto2;
		shipped2[2] = shippedDto3;

		product2.setId(2);
		product2.setProductName("mobile");
		product2.setQuantity(200);
		product2.setShippedInfoDto(shipped2);
		UserDto user2 = new UserDto();
		user2.setUserId("222");
		user2.setUserName("Second User");
		user2.setProductDto(product2);
		return user2;
	}

	public UserDto firstUserInfo() {
		ProductDto productDtos = new ProductDto();
		ShippedInfoDto[] shipped = new ShippedInfoDto[3];
		AirCraftDto[] airCraft = new AirCraftDto[2];

		// with setter and getters
		AirCraftDto airCraftDto1 = new AirCraftDto();
		AirCraftDto airCraftDto2 = new AirCraftDto();
		
		ShippedInfoDto shippedDto1 = new ShippedInfoDto();
		ShippedInfoDto shippedDto2 = new ShippedInfoDto();
		ShippedInfoDto shippedDto3 = new ShippedInfoDto();
		airCraftDto1.setNumber(101);
		airCraftDto1.setName("jet-airways1");
		airCraftDto2.setNumber(102);
		airCraftDto2.setName("Indian-airways1");
		
		airCraft[0] = airCraftDto1;
		airCraft[1] = airCraftDto2;
		
		shippedDto1.setShippedId("1111");
		shippedDto1.setShippedValue(1001);
		shippedDto1.setAirCraftDto(airCraft);
		shippedDto2.setShippedId("1112");
		shippedDto2.setShippedValue(1002);
		shippedDto2.setAirCraftDto(airCraft);
		shippedDto3.setShippedId("1113");
		shippedDto3.setShippedValue(1003);
		shippedDto3.setAirCraftDto(airCraft);
		shipped[0] = shippedDto1;
		shipped[1] = shippedDto2;
		shipped[2] = shippedDto3;

		// with constructor
		// shipped1[0] = new ShippedInfoDto("1111", 10001);
		// shipped1[1] = new ShippedInfoDto("2222", 20002);

		productDtos.setId(1);
		productDtos.setProductName("laptop");
		productDtos.setQuantity(100);

		productDtos.setShippedInfoDto(shipped);
		UserDto user1 = new UserDto();
		user1.setUserId("111");
		user1.setUserName("First User");
		user1.setProductDto(productDtos);
		return user1;
	}
}
