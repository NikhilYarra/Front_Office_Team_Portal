package com.org.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.org.entity.CoursesEntity;
import com.org.entity.EnqStatusEntity;
import com.org.repository.CoursesRepo;
import com.org.repository.EnqStatusRepo;

@Component
public class DataLoader implements ApplicationRunner{
	
	@Autowired
	private CoursesRepo coursesRepo;
	
	@Autowired
	private EnqStatusRepo statusRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		coursesRepo.deleteAll();
		
		CoursesEntity c1 = new CoursesEntity();
		c1.setCourseName("Java");
		
		CoursesEntity c2 = new CoursesEntity();
		c2.setCourseName("Python");
		
		CoursesEntity c3 = new CoursesEntity();
		c3.setCourseName("Devops");
		
		CoursesEntity c4 = new CoursesEntity();
		c4.setCourseName("AWS");
		
		coursesRepo.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		statusRepo.deleteAll();
		
		EnqStatusEntity s1 = new EnqStatusEntity();
		s1.setStatusName("New");
		
		EnqStatusEntity s2 = new EnqStatusEntity();
		s2.setStatusName("Enrolled");
		
		EnqStatusEntity s3 = new EnqStatusEntity();
		s3.setStatusName("Lost");
		
		statusRepo.saveAll(Arrays.asList(s1,s2,s3));
		
	}
}

