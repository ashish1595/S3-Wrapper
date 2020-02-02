package com.ashish.projects.s3.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ashish.projects.s3.entity.Bucket;
import com.ashish.projects.s3.exception.S3Exception;
import com.ashish.projects.s3.service.GenericService;
import com.ashish.projects.s3.utils.Constant;
import com.ashish.projects.s3.utils.RestResponse;
import com.ashish.projects.s3.utils.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/request")
@Api(value="RequestController", description="It provides generic data insert, get, create buckets, create links, list buckets on S3 storage for the registered organisation")
public class RequestController {

	@Autowired
	private GenericService genericService;
	
	@ApiOperation(value = "API to create a bucket in S3 storage", response = ResponseEntity.class)
	@RequestMapping(value="/createBucket", method=RequestMethod.GET)
	public ResponseEntity<RestResponse<String>> createBucket(
			@RequestHeader(value = Constant.Headers.BUCKET_NAME) String bucketName,
			@RequestHeader(value = Constant.Headers.ORGANISATION) String organisation
			)  throws S3Exception {
		return RestUtils.successResponse(genericService.createBucket(bucketName, organisation));
	}

	@ApiOperation(value = "API to view all buckets of an organisation present in S3 storage", response = ResponseEntity.class)
	@RequestMapping(value="/buckets", method=RequestMethod.GET)
	public ResponseEntity<RestResponse<List<Bucket>>> getBuckets(
			@RequestHeader(value = Constant.Headers.ORGANISATION) String organisation
			) throws S3Exception {
		return RestUtils.successResponse(genericService.getBuckets(organisation));
	}

	@ApiOperation(value = "API to post data into S3 storage", response = ResponseEntity.class)
	@RequestMapping(value="/data/object", method=RequestMethod.POST, consumes = "multipart/form-data")
	public ResponseEntity<RestResponse<String>> saveDataObject(
			@RequestPart(value = "filePart") MultipartFile filePart,
			@RequestParam(value = "fileName", defaultValue="test.jpg") String fileName,
			@RequestParam(value = "isPrivate", defaultValue="false") Boolean isPrivate,
			@RequestParam(value = "userMetadata", required = false) Map<String, String> userMetadata,
			@RequestHeader(value = Constant.Headers.BUCKET_NAME) String bucketName
			)  throws S3Exception {
		return RestUtils.successResponse(genericService.saveDataObject(filePart, bucketName, fileName, userMetadata, isPrivate));
	}

	@ApiOperation(value = "API to fetch data from S3 storage in bytes format", response = ResponseEntity.class)
	@RequestMapping(value="/data/object", method=RequestMethod.GET)
	public ResponseEntity<RestResponse<byte[]>> getDataObject(
			@RequestParam(value = "fileName") String fileName,
			@RequestHeader(value = Constant.Headers.BUCKET_NAME) String bucketName
			)  throws S3Exception {
		return RestUtils.successResponse(genericService.getDataObject(bucketName, fileName));
	}
	
	@ApiOperation(value = "API to fetch data from S3 storage as a file", response = ResponseEntity.class)
	@RequestMapping(value="/image", method=RequestMethod.GET)
	public void getImage(
			@RequestParam(value = "fileName") String fileName,
			@RequestHeader(value = Constant.Headers.BUCKET_NAME) String bucketName,
			HttpServletResponse response)  throws IOException, S3Exception {
		InputStream in = genericService.getImageObject(bucketName, fileName);
	    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	    IOUtils.copy(in, response.getOutputStream());
	}


	@ApiOperation(value = "API to fetch link for a file from S3 storage", response = ResponseEntity.class)
	@RequestMapping(value="/link", method=RequestMethod.GET)
	public ResponseEntity<RestResponse<String>> getLink(
			@RequestParam(value = "fileName") String fileName,
			@RequestHeader(value = Constant.Headers.BUCKET_NAME) String bucketName
			) throws S3Exception {
		return RestUtils.successResponse(genericService.getLink(bucketName, fileName));
	}
	
	@ApiOperation(value = "API to delete data from S3 storage", response = ResponseEntity.class)
	@RequestMapping(value="/delete/object", method=RequestMethod.GET)
	public ResponseEntity<RestResponse<String>> deleteDataObject(
			@RequestParam(value = "fileName") String fileName,
			@RequestHeader(value = Constant.Headers.BUCKET_NAME) String bucketName
			)  throws S3Exception {
		return RestUtils.successResponse(genericService.deleteDataObject(bucketName, fileName));
	}

}