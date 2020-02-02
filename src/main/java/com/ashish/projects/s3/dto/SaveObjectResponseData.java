package com.ashish.projects.s3.dto;

import lombok.Data;

@Data
public class SaveObjectResponseData {

	private String url;
	private String bucket;
	private String keyName;
}