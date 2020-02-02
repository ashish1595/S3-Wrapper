package com.ashish.projects.s3.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "S3_BUCKET")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bucket implements Serializable {

	private static final long serialVersionUID = -670294039822166158L;

	@Id
	@Column(name="name")
	private String name;

	@Column(name="organisation")
	private String organisation;
}