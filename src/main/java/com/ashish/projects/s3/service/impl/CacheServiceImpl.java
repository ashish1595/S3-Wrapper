/**
 * @Ashish Gupta
 */
package com.ashish.projects.s3.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import com.ashish.projects.s3.entity.Bucket;
import com.ashish.projects.s3.entity.User;
import com.ashish.projects.s3.exception.S3Exception;
import com.ashish.projects.s3.repository.BucketRepository;
import com.ashish.projects.s3.repository.UserRepository;
import com.ashish.projects.s3.service.CacheService;

@EnableCaching
@Service
public class CacheServiceImpl implements CacheService {

	private final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BucketRepository bucketRepository;

	@Override
	@CacheEvict(value = { "S3_USER_DETAILS","S3_BUCKET_DETAILS" }, allEntries=true)
	public Boolean loadAllProperties() throws S3Exception {
		logger.info("loaded All Properties...");
		return Boolean.TRUE;
	}

	@Override
	@Cacheable(value = "S3_USER_DETAILS")
	public User getUserDetails(String organisation) throws S3Exception {
		logger.info("Inside getUserDetails for org {}",organisation);
		List<User> userList = userRepository.findByOrganisationAndIsDeleted(organisation, 0);
		return userList.get(0);
	}

	@Override
	@Cacheable(value = "S3_BUCKET_DETAILS")
	public List<Bucket> getBucketDetails(String organisation) throws S3Exception {
		return bucketRepository.findByOrganisation(organisation);
	}
}