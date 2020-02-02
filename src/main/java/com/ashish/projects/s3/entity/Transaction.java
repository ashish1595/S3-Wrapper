package com.ashish.projects.s3.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "S3_TRANSACTION")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction implements Serializable {

	private static final long serialVersionUID = 7036513923708649277L;

	@Id
	@SequenceGenerator(name = "s3_transaction_seq", sequenceName = "s3_transaction_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s3_transaction_seq")
	private Long id;

	@Column(name="file_name")
	private String fileName;

	@Column(name="bucket_name")
	private String bucketName;
	
	@Column(name="status")
	private Integer status;
}