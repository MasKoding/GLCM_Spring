package com.gpch.login.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Columns;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

@Entity
public class Ekstraksi {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String FileName;
	
	@Null
	private String IdLabel;


	private String CreatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date CreatedAt; 
	
	@Nullable
	private String UpdatedBy;
	
	@Nullable
	private Date UpdateAt;
	
	@Column(nullable = true,precision = 30,scale = 18)
	private Double Contrast;
	
	@Column(nullable = true,precision = 30,scale = 18)
	private Double Homogenity;
	
	@Column(nullable = true,precision = 30,scale = 18)
	private Double Entropy;
	

	@Column(nullable = true,precision = 30,scale = 18)
	private Double Energy;
	

	@Column(nullable = true,precision = 30,scale = 18)
	private Double Dissimilarity;
	
	
	private boolean IsActive;
	
	private boolean IsDeleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public String getIdLabel() {
		return IdLabel;
	}

	public void setIdLabel(String idLabel) {
		IdLabel = idLabel;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public Date getCreatedAt() {
		return CreatedAt;
	}

	public void setCreatedAt(Date createdAt) {
		CreatedAt = createdAt;
	}

	public String getUpdatedBy() {
		return UpdatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		UpdatedBy = updatedBy;
	}

	public Date getUpdateAt() {
		return UpdateAt;
	}

	public void setUpdateAt(Date updateAt) {
		UpdateAt = updateAt;
	}

	public boolean isIsActive() {
		return IsActive;
	}

	public void setIsActive(boolean isActive) {
		IsActive = isActive;
	}

	public boolean isIsDeleted() {
		return IsDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		IsDeleted = isDeleted;
	}

	public Double getContrast() {
		return Contrast;
	}

	public void setContrast(Double contrast) {
		Contrast = contrast;
	}

	public Double getHomogenity() {
		return Homogenity;
	}

	public void setHomogenity(Double homogenity) {
		Homogenity = homogenity;
	}

	public Double getEntropy() {
		return Entropy;
	}

	public void setEntropy(Double entropy) {
		Entropy = entropy;
	}

	public Double getEnergy() {
		return Energy;
	}

	public void setEnergy(Double energy) {
		Energy = energy;
	}

	public Double getDissimilarity() {
		return Dissimilarity;
	}

	public void setDissimilarity(Double dissimilarity) {
		Dissimilarity = dissimilarity;
	}

	
	
	
}
