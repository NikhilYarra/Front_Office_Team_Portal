package com.org.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.org.binding.DashboardForm;
import com.org.binding.SearchForm;
import com.org.binding.StudentForm;
import com.org.constants.AppConstants;
import com.org.entity.StudentEntity;
import com.org.repository.StudentRepo;
import com.org.service.StudentService;

@Controller
public class StudentController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		
		return AppConstants.INDEX_PAGE;
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		Integer userId = (Integer) session.getAttribute(AppConstants.USER_ID);
		DashboardForm dashboardData = studentService.getDashboardData(userId);
		
		model.addAttribute(AppConstants.DASHBOARD_DATA, dashboardData);
		return AppConstants.DASHBOARD_PAGE;
	}
	
	@GetMapping("/addEnquiry")
	public String addEnquiryPage(Model model) {
		initForm(model);
		model.addAttribute(AppConstants.STUDENT, new StudentForm());
		
		return AppConstants.ADD_ENQ_PAGE;
	}
	
	@PostMapping("/addEnquiry")
	public String handleAddEnquiry(@Validated @ModelAttribute(AppConstants.STUDENT)StudentForm form,BindingResult result, Model model) {
		if(result.hasErrors()) {
			initForm(model);
			return AppConstants.ADD_ENQ_PAGE;
		}
		
		Integer userId = (Integer) session.getAttribute(AppConstants.USER_ID);
		boolean save = studentService.saveStudent(form,userId);
		if(save) {
			model.addAttribute(AppConstants.SUCC_MSG, AppConstants.SAVE_STUDENT);
		}else {
			model.addAttribute(AppConstants.ERR_MSG, AppConstants.NOT_SAVED_MSG);
		}
		
		initForm(model);
		model.addAttribute(AppConstants.STUDENT, new StudentForm());
		
		return AppConstants.ADD_ENQ_PAGE;
	}

	@GetMapping("/edit")
	public String editStudent(@RequestParam(AppConstants.STUDENT_ID)Integer studentId, Model model) {
		Optional<StudentEntity> findById = studentRepo.findById(studentId);
		StudentForm studentForm = new StudentForm();
		if(findById.isPresent()) {
			StudentEntity student = findById.get();
			BeanUtils.copyProperties(student, studentForm);
			initForm(model);
			model.addAttribute(AppConstants.STUDENT, student);
		}
		
		return AppConstants.ADD_ENQ_PAGE;
	}
	private void initForm(Model model) {
		List<String> courses = studentService.getCourses();
		List<String> status = studentService.getStatus();
		
		model.addAttribute(AppConstants.STATUS_NAMES, status);
		model.addAttribute(AppConstants.COURSES_NAME, courses);
		
	}
	
	
	@GetMapping("/viewEnquiries")
	public String viewEnquieriesPage(SearchForm form, Model model) {
		initForm(model);
		model.addAttribute(AppConstants.SEARCH, new SearchForm());
		List<StudentEntity> students = studentService.getStudents();
		model.addAttribute(AppConstants.STUDENTS, students);
		return AppConstants.VIEW_ENQ_PAGE;
	}
	
	@GetMapping("/filter-enquiries")
	public String getFilteredData(@RequestParam String course, @RequestParam String status,@RequestParam String mode, Model model) {
		SearchForm search = new SearchForm();
		search.setCourseName(course);
		search.setClassMode(mode);
		search.setEnqStatus(status);
		
		Integer userId = (Integer) session.getAttribute(AppConstants.USER_ID);
		List<StudentEntity> filteredData = studentService.getFilteredData(search, userId);
		
		model.addAttribute(AppConstants.FILTER_DATA, filteredData);
		return AppConstants.FILTERED_PAGE;
	}
}
