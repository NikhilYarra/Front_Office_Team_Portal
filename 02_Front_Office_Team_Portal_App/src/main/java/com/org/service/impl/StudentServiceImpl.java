package com.org.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.binding.DashboardForm;
import com.org.binding.SearchForm;
import com.org.binding.StudentForm;
import com.org.constants.AppConstants;
import com.org.entity.CoursesEntity;
import com.org.entity.EnqStatusEntity;
import com.org.entity.StudentEntity;
import com.org.entity.UserEntity;
import com.org.repository.CoursesRepo;
import com.org.repository.EnqStatusRepo;
import com.org.repository.StudentRepo;
import com.org.repository.UserRepo;
import com.org.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CoursesRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo statusRepo;
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Override
	public DashboardForm getDashboardData(Integer userId) {
		DashboardForm response = new DashboardForm();
		
		Optional<UserEntity> findById = userRepo.findById(userId);
		
		if(findById.isPresent()) {
			UserEntity user = findById.get();
			
			List<StudentEntity> student = user.getStudentEntity();
			
			int totalCnt = student.size();
			
			int rolledCnt = student.stream().
					filter(e -> e.getEnqStatus().equalsIgnoreCase(AppConstants.ENROLLED)).
					collect(Collectors.toList()).size();
			
			int lostCnt = student.stream().
					filter(e -> e.getEnqStatus().equalsIgnoreCase(AppConstants.LOST)).
					collect(Collectors.toList()).size();		
			
			response.setTotalStudentCnt(totalCnt);
			response.setRolledCnt(rolledCnt);
			response.setLostCnt(lostCnt);
			
		}
		return response;
	}
	
	@Override
	public List<String> getCourses() {
		List<CoursesEntity> findAll = courseRepo.findAll();
		List<String> names = new ArrayList<>();
		
		for(CoursesEntity entity : findAll) {
			names.add(entity.getCourseName());
		}
		return names;
	}
	
	@Override
	public List<String> getStatus() {
		List<EnqStatusEntity> findAll = statusRepo.findAll();
		List<String> status = new ArrayList<>();
		
		for(EnqStatusEntity entity : findAll) {
			status.add(entity.getStatusName());
		}
		return status;
	}
	
	@Override
	public boolean saveStudent(StudentForm form, Integer userId) {
		Optional<UserEntity> findById = userRepo.findById(userId);
		if(findById.isPresent()) {
			UserEntity user = findById.get();
			StudentEntity student = new StudentEntity();
			if(null != form.getStudentId()) {
				StudentEntity student1 = studentRepo.findById(form.getStudentId()).get();
				student1.setStudentName(form.getStudentName());
				student1.setCourseName(form.getCourseName());
				student1.setClassMode(form.getClassMode());
				student1.setEnqStatus(form.getEnqStatus());
				student1.setStudentPhno(form.getStudentPhno());
				student1.setUser(user);
				
				studentRepo.save(student1);
				
				return true;
			}
			BeanUtils.copyProperties(form, student);
			student.setUser(user);
			studentRepo.save(student);
		}
		return true;
	}
	
	@Override
	public List<StudentEntity> getStudents() {
		Integer userId = (Integer) session.getAttribute(AppConstants.USER_ID);
		Optional<UserEntity> findById = userRepo.findById(userId);
		
		if(findById.isPresent()) {
			UserEntity userEntity = findById.get();
			List<StudentEntity> studentEntities = userEntity.getStudentEntity();
			
			return studentEntities;
		}
		return null;
	}
	
	@Override
	public List<StudentEntity> getFilteredData(SearchForm form, Integer userId) {
		Optional<UserEntity> findById = userRepo.findById(userId);
		if(findById.isPresent()) {
			UserEntity userEntity = findById.get();
			List<StudentEntity> students = userEntity.getStudentEntity();
			
			if(null!=form.getCourseName() & !"".equals(form.getCourseName())) {
				
				students = students.stream()
						.filter(e -> e.getCourseName().equalsIgnoreCase(form.getCourseName()))
						.collect(Collectors.toList());
			}
			
			if(null!=form.getClassMode() & !"".equals(form.getClassMode())){
				students = students.stream()
						.filter(e -> e.getClassMode().equalsIgnoreCase(form.getClassMode()))
						.collect(Collectors.toList());
			}
			
			if(null != form.getEnqStatus() & !"".equals(form.getEnqStatus())) {
				students = students.stream()
						.filter(e -> e.getEnqStatus().equalsIgnoreCase(form.getEnqStatus()))
						.collect(Collectors.toList());
			}
			
			return students;
		}
		return null;
	}
}
