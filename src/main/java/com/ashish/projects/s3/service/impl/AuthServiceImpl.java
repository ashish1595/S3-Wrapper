/**
 * @Ashish Gupta
 */
package com.ashish.projects.s3.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import com.ashish.projects.s3.dto.UserRequestData;
import com.ashish.projects.s3.entity.User;
import com.ashish.projects.s3.exception.S3Exception;
import com.ashish.projects.s3.repository.UserRepository;
import com.ashish.projects.s3.service.AuthService;

@EnableCaching
@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User createUser(UserRequestData userRequestData) throws S3Exception {
		User userBean = setUserBeanData(userRequestData);
		return userRepository.save(userBean);
	}

	private User setUserBeanData(UserRequestData userRequestData) {
		User userBean = new User();
		userBean.setAccessKey(UUID.randomUUID().toString());
		userBean.setBucketInitials(null == userRequestData.getBucketInitials()?
				userRequestData.getOrganisation() : userRequestData.getBucketInitials());
		userBean.setDefaultBucket(userRequestData.getDefaultBucket());
		userBean.setIsDeleted(0);
		userBean.setOrganisation(userRequestData.getOrganisation());
		return userBean;
	}
}