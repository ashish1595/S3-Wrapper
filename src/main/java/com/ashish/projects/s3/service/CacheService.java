package com.ashish.projects.s3.service;

import java.util.List;

import com.ashish.projects.s3.entity.Bucket;
import com.ashish.projects.s3.entity.User;
import com.ashish.projects.s3.exception.S3Exception;

public interface CacheService {

	Boolean loadAllProperties() throws S3Exception;

	User getUserDetails(String organisation) throws S3Exception;
	
	List<Bucket> getBucketDetails(String organisation) throws S3Exception;
}