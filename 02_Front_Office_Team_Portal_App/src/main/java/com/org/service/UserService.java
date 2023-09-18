package com.org.service;

import org.springframework.stereotype.Service;

import com.org.binding.LoginForm;
import com.org.binding.SignUpForm;
import com.org.binding.UnlockForm;

@Service
public interface UserService {

	public boolean signUp(SignUpForm form);
	
	public boolean unlock(UnlockForm form);
	
	public String login(LoginForm form);
	
	public boolean forgetPwd(String email);
}
