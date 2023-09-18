package com.org.entity;


import java.util.List; 

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.org.constants.AppConstants;

import lombok.Data;

@Entity
@Data
@Table(name= AppConstants.USER_ENTITY_TBL)
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	private String name;
	
	private String email;
	
	private String pwzd;
	
	private Long phno;
	
	private String accStatus;
	
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<StudentEntity> studentEntity;
}
