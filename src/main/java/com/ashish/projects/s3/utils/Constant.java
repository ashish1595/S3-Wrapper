package com.ashish.projects.s3.utils;

public interface Constant {
	Integer SUCCESS_STATUS = 200;
	Integer FAILURE_STATUS = 101;
	String SUCCESS_STATUS_MESAGE = "ok";
	Integer ACTIVE_STATUS = 1;
	Integer INACTIVE_STATUS = 0;
	Integer IS_DELETED = 1;
	Integer IS_NOT_DELETED = 0;

	interface Headers {
		String ORGANISATION = "ORGANISATION";
		String ACCESS_KEY = "ACCESS_KEY";
		String BUCKET_NAME = "BUCKET_NAME";
	}
}
