package com.ashish.projects.s3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ashish.projects.s3.exception.S3Exception;
import com.ashish.projects.s3.service.CacheService;
import com.ashish.projects.s3.utils.RestResponse;
import com.ashish.projects.s3.utils.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/cache")
@Api(value="CacheManagerController", description="It provides API to clear cache")
public class CacheManagerController {

	@Autowired
	private CacheService cacheService;

	/**
	 * TODO 
	 * @return
	 * @throws AntException
	 */
	@ApiOperation(value = "API to reload all cached objects", response = ResponseEntity.class)
	@RequestMapping(value="/load", method=RequestMethod.GET)
	public ResponseEntity<RestResponse<Boolean>> loadAllProperties()  throws S3Exception {
		return RestUtils.successResponse(cacheService.loadAllProperties());
	}
}