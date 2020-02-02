package com.ashish.projects.s3.service;

import com.ashish.projects.s3.dto.UserRequestData;
import com.ashish.projects.s3.entity.Bucket;
import com.ashish.projects.s3.entity.User;
import com.ashish.projects.s3.exception.S3Exception;

public interface AuthService {

	User createUser(UserRequestData userRequestData) throws S3Exception;
}