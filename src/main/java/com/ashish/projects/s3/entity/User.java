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
@Table(name = "S3_USER")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

	private static final long serialVersionUID = 7036513923708649277L;

	@Id
	@SequenceGenerator(name = "s3_user_seq", sequenceName = "s3_user_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s3_user_seq")
	@Column(name = "id")
	private Long id;

	@Column(name="organisation")
	private String organisation;

	@Column(name="access_key")
	private String accessKey;

	@Column(name="bucket_initials")
	private String bucketInitials;

	@Column(name="default_bucket")
	private String defaultBucket;
	
	@Column(name="is_deleted")
	private Integer isDeleted;
}