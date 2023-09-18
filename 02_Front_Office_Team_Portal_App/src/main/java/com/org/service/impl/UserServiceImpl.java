package com.org.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.binding.LoginForm;
import com.org.binding.SignUpForm;
import com.org.binding.UnlockForm;
import com.org.constants.AppConstants;
import com.org.entity.UserEntity;
import com.org.repository.UserRepo;
import com.org.service.UserService;
import com.org.utils.EmailUtils;
import com.org.utils.PwdUtils;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private EmailUtils mail;

	@Override
	public boolean signUp(SignUpForm form) {
		
		UserEntity userEntity = userRepo.findByEmail(form.getEmail());
		if(null != userEntity) {
			return false;
		}
		
		UserEntity user = new UserEntity();
		BeanUtils.copyProperties(form, user);
		
		String tempPwzd = PwdUtils.generatePwzd();
		user.setPwzd(tempPwzd);
		
		user.setAccStatus(AppConstants.LOCKED);
		
		userRepo.save(user);
		
		String to = user.getEmail();
		String subject = AppConstants.MAIL_SUBJECT;
		
		StringBuffer body = new StringBuffer("");
		body.append("<h2>Use the below temporary password to UNLOCK your Account</h2>");
		body.append("<br/>");
		body.append("Temporary password : "+tempPwzd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email="+to+"\">Click Here to UNLOCk your Account");
		
		mail.sendEmail(to, subject, body.toString());
		
		return true;
	}

	@Override
	public boolean unlock(UnlockForm form) {
		UserEntity user = userRepo.findByEmail(form.getEmail());
		
		if(user.getPwzd().equals(form.getTempPwzd())) {
			user.setPwzd(form.getCofirmPwzd());
			user.setAccStatus(AppConstants.UNLOCK);
			userRepo.save(user);
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public String login(LoginForm form) {
		UserEntity user = userRepo.findByEmailAndPwzd(form.getEmail(), form.getPwzd());
		
		if(null == user) {
			return AppConstants.INVALID_CREDENTIALS_MSG;
		}
		if(user.getAccStatus().equals(AppConstants.LOCKED)) {
			return AppConstants.LOCKED_MSG;
		}
		
		session.setAttribute(AppConstants.USER_ID, user.getUserId());
		
		return AppConstants.SUCCESS;
	}
	
	@Override
	public boolean forgetPwd(String email) {
		
		UserEntity user = userRepo.findByEmail(email);
		
		if(null == user) {
			return false;
		}
		
		String subject = AppConstants.MAIL_RECOVER_PWD_SUBJECT;
		String body = AppConstants.MAIL_PWD_BODY+user.getPwzd();
		
		mail.sendEmail(email, subject, body);
		
		return true;
	}
}
