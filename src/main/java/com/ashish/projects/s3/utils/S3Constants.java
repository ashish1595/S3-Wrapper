package com.ashish.projects.s3.utils;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class S3Constants implements Serializable {

	private static final long serialVersionUID = 5840082759430562017L;

	@JsonProperty("url")
	public String url;
}