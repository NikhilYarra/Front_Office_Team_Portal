package com.org.binding;

import javax.validation.constraints.NotBlank; 
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class StudentForm {

	private Integer studentId;
	@NotBlank(message = "Student Name is Mandatory")
	private String studentName;
	@NotNull(message = "Student Phno is mandatory")
	private Long studentPhno;
	private String classMode;
	private String courseName;
	private String enqStatus;
}
