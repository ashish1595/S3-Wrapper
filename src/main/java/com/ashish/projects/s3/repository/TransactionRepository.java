package com.ashish.projects.s3.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ashish.projects.s3.entity.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}