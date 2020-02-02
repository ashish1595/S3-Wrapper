package com.ashish.projects.s3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ashish.projects.s3.dto.UserRequestData;
import com.ashish.projects.s3.entity.User;
import com.ashish.projects.s3.exception.S3Exception;
import com.ashish.projects.s3.service.AuthService;
import com.ashish.projects.s3.utils.RestResponse;
import com.ashish.projects.s3.utils.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth")
@Api(value="AuthController", description="It provides API to register a new organisation")
public class AuthController {

	@Autowired
	private AuthService authService;

	@ApiOperation(value = "API to register a new organisation", response = ResponseEntity.class)
	@RequestMapping(value="/createUser", method=RequestMethod.POST)
	public ResponseEntity<RestResponse<User>> createUser(@RequestBody UserRequestData userRequestData)  throws S3Exception {
		return RestUtils.successResponse(authService.createUser(userRequestData));
	}
}