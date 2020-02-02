package com.ashish.projects.s3.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.ashish.projects.s3.entity.Bucket;
import com.ashish.projects.s3.entity.Transaction;
import com.ashish.projects.s3.exception.ExceptionResponseCode;
import com.ashish.projects.s3.exception.S3Exception;
import com.ashish.projects.s3.repository.BucketRepository;
import com.ashish.projects.s3.repository.TransactionRepository;
import com.ashish.projects.s3.service.GenericService;
import com.ashish.projects.s3.utils.CommonUtility;

@Service
public class GenericServiceImpl implements GenericService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AmazonS3 s3client;

	@Autowired
	private BucketRepository bucketRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Value("${nginx.url}")
	private String nginxUrl;

	@Override
	public String saveDataObject(MultipartFile filePart, String bucketName, String fileName, Map<String, String> userMetadata,
			Boolean isPrivate) throws S3Exception {
		Transaction transaction = setTransactionDetails(bucketName, fileName, userMetadata);
		try {
			ObjectMetadata metaInfo = new ObjectMetadata();
			metaInfo.setUserMetadata(userMetadata);
			PutObjectRequest request = new PutObjectRequest(bucketName, fileName, filePart.getInputStream(), metaInfo);
			if (! isPrivate) request.setCannedAcl(CannedAccessControlList.PublicRead);
			s3client.putObject(request);
			transaction.setStatus(1);
			transactionRepository.save(transaction);
			return nginxUrl+s3client.getUrl(bucketName, fileName).getPath();
		} catch (Exception e) {
			logger.error("saveDataObject failed while uploading data due to {}",e);
			transaction.setStatus(0);
			transactionRepository.save(transaction);
			throw new S3Exception(ExceptionResponseCode.UPLOADING_FILE_FAILED);
		}
	}

	private Transaction setTransactionDetails(String bucketName, String fileName, Map<String, String> userMetadata) {
		Transaction transaction = new Transaction();
		transaction.setBucketName(bucketName);
		transaction.setFileName(fileName);
		return transaction;
	}

	@Override
	public String createBucket(String bucketName, String organisation) throws S3Exception {
		s3client.createBucket(bucketName);
		saveBucketDetails(bucketName, organisation);
		return "Bucket created successfully!";
	}

	@Override
	public List<Bucket> getBuckets(String organisation) {
		return bucketRepository.findByOrganisation(organisation);
	}

	@Override
	public byte[] getDataObject(String bucketName, String fileName) throws S3Exception {
		S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, fileName));
		if (CommonUtility.isNullObject(s3object))
			throw new S3Exception(ExceptionResponseCode.USER_DATA_NOT_FOUND_IN_REQUEST);
		byte[] objectData = null;
		try {
			objectData = IOUtils.toByteArray(s3object.getObjectContent());
		} catch (IOException e) {
			throw new S3Exception(ExceptionResponseCode.GENRAL_ERROR);
		}
		return objectData;
	}

	@Override
	public String getLink(String bucketName, String fileName) {
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, fileName);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 7);
		request.setExpiration(cal.getTime());
		return s3client.generatePresignedUrl(request).toString();
	}

	private void saveBucketDetails(String bucketName, String organisation) {
		Bucket bucket = new Bucket();
		bucket.setName(bucketName);
		bucket.setOrganisation(organisation);
		bucketRepository.save(bucket);
	}

	@Override
	public InputStream getImageObject(String bucketName, String fileName) throws S3Exception {
		S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, fileName));
		if (CommonUtility.isNullObject(s3object))
			throw new S3Exception(ExceptionResponseCode.USER_DATA_NOT_FOUND_IN_REQUEST);
		return s3object.getObjectContent();
	}

	@Override
	public String deleteDataObject(String bucketName, String fileName) throws S3Exception {
		s3client.deleteObject(bucketName, fileName);
		return "File deleted successfully!";
	}
}