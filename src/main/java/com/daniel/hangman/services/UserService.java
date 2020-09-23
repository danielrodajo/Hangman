package com.daniel.hangman.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.daniel.hangman.models.AppUser;
import com.daniel.hangman.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	public AppUser save(AppUser u) {
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		return userRepository.save(u);
	}
	
	public AppUser edit(AppUser u) {
		return userRepository.save(u);
	}
	
	public AppUser findByName(String name) {
		return userRepository.findByNameIgnoreCase(name).orElse(null);
	}
	
	public AppUser findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
}
