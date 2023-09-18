package com.org.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import com.org.constants.AppConstants;

@Component
public class PwdUtils {

	public static String generatePwzd() {
		String characters = AppConstants.CHARACTERS;
		return RandomStringUtils.random( 15, characters );
	}
}
