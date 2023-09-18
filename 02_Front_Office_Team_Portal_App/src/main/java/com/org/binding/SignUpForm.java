package com.org.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SignUpForm {

	@NotBlank(message = "Name is Mandatory")
	private String name;
	@Email(message = "Enter Valid Email Address")
	private String email;
	@NotNull(message = "Phone numbe is Mandatory")
	private Long phno;
}
