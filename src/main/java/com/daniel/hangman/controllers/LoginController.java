package com.daniel.hangman.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.daniel.hangman.models.AppUser;
import com.daniel.hangman.services.UserService;

/**
 * Controlador encargado de gestionar las peticiones HTTP provenientes de la sección de SignIn/SignUp del sitio web
 * @author mañana
 *
 */
@Controller
public class LoginController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/auth/login")
	public String login(Model model) {
		//Pasamos el usuario a crear para acceder a la pagina
		model.addAttribute("user", new AppUser());
		return "login";
	}

	
	@PostMapping("/auth/register")
	public String register(@ModelAttribute AppUser user) {
		userService.save(user);
		return "redirect:/auth/login";
	}
	
}
