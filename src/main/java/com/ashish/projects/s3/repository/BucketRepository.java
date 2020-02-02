package com.ashish.projects.s3.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ashish.projects.s3.entity.Bucket;

@Repository
public interface BucketRepository extends CrudRepository<Bucket, Long> {
	
	List<Bucket> findByOrganisation(String organisation);
}