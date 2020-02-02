package com.ashish.projects.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class ApplicationConfiguration {

	@Value("${s3.endpointUrl}")
	private String endpointUrl;

	@Value("${s3.accessKey}")
	private String accessKey;

	@Value("${s3.secretKey}")
	private String secretKey;

	@Value("${s3.requestTimeout}")
	private String requestTimeout;

	@Value("${s3.connectionTimeout}")
	private String connectionTimeout;

	@Value("${s3.socketTimeout}")
	private String socketTimeout;

	@Value("${s3.maxErrorRetry}")
	private String maxErrorRetry;

	@Value("${s3.maxConnections}")
	private String maxConnections;

	@Bean
	public AmazonS3 s3client() {
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);
		clientConfig.setRequestTimeout(Integer.parseInt(requestTimeout));
		clientConfig.setConnectionTimeout(Integer.parseInt(connectionTimeout));
		clientConfig.setSocketTimeout(Integer.parseInt(socketTimeout));
		clientConfig.setRetryPolicy(PredefinedRetryPolicies.getDefaultRetryPolicy());
		clientConfig.setMaxErrorRetry(Integer.parseInt(maxErrorRetry));
		clientConfig.setMaxConnections(Integer.parseInt(maxConnections));
		BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials));
		builder.withClientConfiguration(clientConfig);
		builder.withEndpointConfiguration(new EndpointConfiguration(
				endpointUrl, Regions.DEFAULT_REGION.getName()));
		return builder.build();
	}
}