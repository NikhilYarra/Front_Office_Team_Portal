package com.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.entity.CoursesEntity;

public interface CoursesRepo extends JpaRepository<CoursesEntity, Integer>{

}
