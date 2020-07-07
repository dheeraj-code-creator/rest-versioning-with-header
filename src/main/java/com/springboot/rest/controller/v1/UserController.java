package com.springboot.rest.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest.dto.UserDto;
import com.springboot.rest.service.UserService;

@RestController
@RequestMapping(value = "/userinfo", produces ="application/json")
public class UserController {
	
	public static final String X_ACCEPT_VERSION_V1 = "X-Accept-Version" + "=" + "v1";

	@Autowired
	private UserService userService;

	@GetMapping(value = "/alluser", headers = X_ACCEPT_VERSION_V1)
	public List<UserDto> getUserinfo() {
		List<UserDto> finalResults = userService.getAllUserInfo();
		return finalResults;
	}

}
