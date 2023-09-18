package com.org.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.org.constants.AppConstants;

import lombok.Data;

@Entity
@Data
@Table(name= AppConstants.COURSE_NAMES_TBL)
public class CoursesEntity {

	@Id
	@GeneratedValue
	private Integer courseId;
	private String courseName;
}
