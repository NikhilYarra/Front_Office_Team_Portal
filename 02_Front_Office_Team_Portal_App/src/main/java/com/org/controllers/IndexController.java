package com.org.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.org.constants.AppConstants;

@Controller
public class IndexController {

	@GetMapping("/")
	public String IndexPage() {
		return AppConstants.INDEX_PAGE;
	}
}
