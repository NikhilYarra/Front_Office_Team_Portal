package com.org.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.org.constants.AppConstants;

import lombok.Data;

@Entity
@Data
@Table(name= AppConstants.ENQ_STATUS_TBL)
public class EnqStatusEntity {

	@Id
	@GeneratedValue
	private Integer statusId;
	private String statusName;
}
