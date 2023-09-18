package com.org.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.org.binding.LoginForm;
import com.org.binding.SignUpForm;
import com.org.binding.UnlockForm;
import com.org.constants.AppConstants;
import com.org.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/signUp")
	public String signUpPage(Model model) {
		model.addAttribute(AppConstants.USER, new SignUpForm());
		return AppConstants.SIGNUP_PAGE;
	}
	
	@PostMapping("/signUp")
	public String handleSignUp(@Validated @ModelAttribute(AppConstants.USER)SignUpForm form,BindingResult result, Model model) {
		if(result.hasErrors()) {
			return AppConstants.SIGNUP_PAGE;
		}
		
		boolean status = userService.signUp(form);
		if(status) {
			model.addAttribute(AppConstants.SUCC_MSG, AppConstants.CHECK_YOUR_MAIL);
		}else {
			model.addAttribute(AppConstants.ERR_MSG, AppConstants.UNIQUE_EMAIL_MSG);
		}
		return AppConstants.SIGNUP_PAGE;
	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {
		UnlockForm unlockObj = new UnlockForm();
		unlockObj.setEmail(email);
		model.addAttribute(AppConstants.UNLOCK_PAGE, unlockObj);
		return AppConstants.UNLOCK_PAGE;
	}
	
	@PostMapping("/unlock")
	public String handleUnlock(@Validated @ModelAttribute(AppConstants.UNLOCK_PAGE)UnlockForm form,BindingResult result, Model model) {
		if(result.hasErrors()) {
			return AppConstants.UNLOCK_PAGE;
		}
		
		if(form.getNewPwzd().equals(form.getCofirmPwzd())) {
			boolean status = userService.unlock(form);
			if(status) {
				model.addAttribute(AppConstants.SUCC_MSG, AppConstants.PASSWORD_UPDATED_AND_UNLOCKED);
			}else {
				model.addAttribute(AppConstants.ERR_MSG, AppConstants.PASSWORD_NOT_UPDATED);
			}
		}else {
			model.addAttribute(AppConstants.ERR_MSG, AppConstants.BOTH_PWD_SHOULD_SAME_ERROR);
		}
		UnlockForm unlockObj = new UnlockForm();
		unlockObj.setEmail(form.getEmail());
		model.addAttribute(AppConstants.UNLOCK_PAGE, unlockObj);
		
		return AppConstants.UNLOCK_PAGE;
	}
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute(AppConstants.LOGIN, new LoginForm());
		
		return AppConstants.LOGIN;
	}
	
	@PostMapping("/login")
	public String handleLogin(@Validated @ModelAttribute(AppConstants.LOGIN) LoginForm form,BindingResult result, Model model) {
		if(result.hasErrors()) {
			return AppConstants.LOGIN;
		}
		String status = userService.login(form);
		
		if(status.contains(AppConstants.SUCCESS)){
			
			return AppConstants.RE_DIRECT;
		}else {
			model.addAttribute(AppConstants.ERR_MSG, status);
			
			return AppConstants.LOGIN;
		}
		
	}
	
	@GetMapping("/forget")
	public String forgetPwdPage() {
		
		return AppConstants.FORGET_PAGE;
	}
	
	@PostMapping("/forgetPwd")
	public String handleForgerPwd(@RequestParam(AppConstants.EMAIL) String email, Model model) {
		
		boolean status = userService.forgetPwd(email);
		
		if(status) {
			model.addAttribute(AppConstants.SUCC_MSG, AppConstants.PASSWORD_SEND_MSG);
		}else {
			model.addAttribute(AppConstants.ERR_MSG, AppConstants.INVALID_EMAIL);
		}
		
		return AppConstants.FORGET_PAGE;
	}
}
