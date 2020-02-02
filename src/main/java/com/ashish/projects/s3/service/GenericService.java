package com.ashish.projects.s3.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.multipart.MultipartFile;

import com.ashish.projects.s3.entity.Bucket;
import com.ashish.projects.s3.exception.S3Exception;

public interface GenericService {

	String createBucket(String bucketName, String organisation) throws S3Exception;

	List<Bucket> getBuckets(String organisation) throws S3Exception;

	byte[] getDataObject(String bucketName, String fileName) throws S3Exception;

	String getLink(String bucketName, String fileName);
 
	@Retryable(value = { Exception.class }, maxAttempts = 2, backoff = @Backoff(delay = 10))
	String saveDataObject(MultipartFile filePart, String bucketName, String fileName, Map<String, String> userMetadata, Boolean isPrivate) throws S3Exception;
	
	InputStream getImageObject(String bucketName, String fileName) throws S3Exception;

	String deleteDataObject(String bucketName, String fileName) throws S3Exception;
}