package com.org.binding;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UnlockForm {

	private String email;
	@NotBlank(message = "Enter the Passsword, You got on your Email")
	private String tempPwzd;
	@NotBlank(message = "New Password is Mandatory")
	private String newPwzd;
	@NotBlank(message = "New Password and Confirm Password should be same")
	private String cofirmPwzd;
}
