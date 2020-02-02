package com.ashish.projects.s3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.WebApplicationInitializer;

@PropertySource(value = "file:/opt/s3/properties/s3.properties", ignoreResourceNotFound = true)
@SpringBootApplication
@EnableRetry
public class S3Application extends SpringBootServletInitializer implements WebApplicationInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<S3Application> applicationClass = S3Application.class;

	public static void main(String[] args) {
		SpringApplication.run(S3Application.class, args);
	}
}