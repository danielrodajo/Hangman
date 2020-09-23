package com.daniel.hangman.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.daniel.hangman.models.AppUser;
import com.daniel.hangman.services.UserService;

/**
 * Clase controladora genérica que contiene aquellos datos y/o métodos comunes para los distintos controladores
 * @author mañana
 *
 */
@Controller
@Primary
public class GlobalController {

	@Autowired
	UserService userService;
	
	protected AppUser user;
	
	@ModelAttribute("current_user")
	public AppUser getCurrentUser() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		user = userService.findByName(name);
		return user;
	}
	
}
