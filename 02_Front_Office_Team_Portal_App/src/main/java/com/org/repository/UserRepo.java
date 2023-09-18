package com.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Integer>{

	public UserEntity findByEmail(String email);
	
	public UserEntity findByEmailAndPwzd(String email, String pwzd);
}
