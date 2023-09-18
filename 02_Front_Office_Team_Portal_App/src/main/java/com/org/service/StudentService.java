package com.org.service;

import java.util.List;

import com.org.binding.DashboardForm;
import com.org.binding.SearchForm;
import com.org.binding.StudentForm;
import com.org.entity.StudentEntity;

public interface StudentService {

	public DashboardForm getDashboardData(Integer userId);
	
	public List<String> getCourses();
	
	public List<String> getStatus();
	
	public boolean saveStudent(StudentForm form, Integer userId);
	
	public List<StudentEntity> getStudents();
	
	public List<StudentEntity> getFilteredData(SearchForm form, Integer userId);
}
