package com.ashish.projects.s3.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ashish.projects.s3.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	List<User> findByOrganisationAndIsDeleted(String organisation, Integer isDeleted);
}