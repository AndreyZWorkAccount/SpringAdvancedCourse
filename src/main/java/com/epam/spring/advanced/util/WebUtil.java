package com.epam.spring.advanced.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

public class WebUtil {

	private static final String STATUS_LINE = "Status message:";


	public static Model setMainAttributes(Model model, String status) {
		model.addAttribute("status", STATUS_LINE + status);
		return model;
	}

}
