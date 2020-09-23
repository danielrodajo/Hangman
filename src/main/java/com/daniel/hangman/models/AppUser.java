package com.daniel.hangman.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Información de los usuarios del sistema
 * @author Daniel Rodajo
 *
 */
@Entity
public class AppUser {
	
	public static final int NUMBER_HINTS = 3;
	
	@Id @GeneratedValue
	private Long id;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private int hints;
	
//	private Set<String> customWords;
	
	
	public AppUser() {
		this.hints = NUMBER_HINTS;
	}

	public AppUser(String name, String email, String password, int hints) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.hints = hints;
	}

	
	//GETTERS & SETTERS--------------------------------------------------------------------------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getHints() {
		return hints;
	}

	public void setHints(int hints) {
		this.hints = hints;
	}

//	public Set<String> getCustomWords() {
//		return customWords;
//	}
//
//	public void setCustomWords(Set<String> customWords) {
//		this.customWords = customWords;
//	}
	
}
