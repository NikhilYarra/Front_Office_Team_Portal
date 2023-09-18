package com.org.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginForm {

	@Email(message = "Enter Valid Email Address")
	private String email;
	
	@NotBlank(message = "Password is Mandatory")
	private String pwzd;
}
