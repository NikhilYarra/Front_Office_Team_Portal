package com.org.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.org.constants.AppConstants;

import lombok.Data;

@Entity
@Data
@Table(name= AppConstants.STUDENT_ENTITY_TBL)
public class StudentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studentId;
	private String studentName;
	private Long studentPhno;
	private String classMode;
	private String courseName;
	private String enqStatus;
	
	@CreationTimestamp
	private LocalDate createdTime;
	
	@UpdateTimestamp
	private LocalDate updatedTime;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private UserEntity user;
}
