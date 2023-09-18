package com.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.entity.StudentEntity;

public interface StudentRepo extends JpaRepository<StudentEntity, Integer>{

}
