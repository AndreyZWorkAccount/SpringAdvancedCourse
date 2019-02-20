package com.epam.spring.advanced.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

public class WebUtil {

	private static final String FIELD_USER = "login";

	private static final String FIELD_ROLES = "login_roles";

	private static final String STATUS_LINE = "Status message:";
	
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static Model setUserInfo(Model model) {
		Authentication auth = getAuthentication();
		model.addAttribute(FIELD_USER, auth.getName());
		model.addAttribute(FIELD_ROLES, auth.getAuthorities());
		return model;
	}

	public static Model setMainAttributes(Model model, String status) {
		model.addAttribute("status", STATUS_LINE + status);
		return setUserInfo(model);
	}
	
	public static String getMd5Password(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
	    return encoder.encodePassword(password, null);
	}
}
