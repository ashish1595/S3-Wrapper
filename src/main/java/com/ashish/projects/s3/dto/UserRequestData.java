package com.ashish.projects.s3.dto;

import lombok.Data;

@Data
public class UserRequestData {

	private String organisation;

	private String bucketInitials;

	private String defaultBucket;
}