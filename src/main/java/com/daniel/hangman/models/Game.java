package com.daniel.hangman.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;

/**
 * Clase representativa de cada partida que pueda jugarse
 * @author Daniel Rodajo
 *
 */
@Entity
public class Game {
	
	@Id @GeneratedValue
	private Long id;
	
	private String word;
	
	//Atributo auxiliar del atributo 'word' que contiene dicha palabra formateada (todo a mayusculas, acentos quitados, etc.), evitando perder la palabra original
	private String formattedWord;
	
	private Integer attempts;
	
	@ElementCollection
	private List<String> lettersUsed;
	
	@OneToMany
	private List<Play> plays;
	
	@CreationTimestamp
	private LocalDateTime startDate;
	
	@ManyToOne
	private AppUser owner;
	
	private boolean finished;
	
	//Puntuacion final, que se generará una vez se finalice la partida.
	private Long puntuation;
	
	public Game() {}

	public Game(String word, AppUser owner) {
		super();
		this.word = word;
		this.formattedWord = formatWord(word);
		this.owner = owner;
		attempts = 5;
		finished = false;
		lettersUsed = new ArrayList<>();
		plays = new ArrayList<>();
	}

	
	private String formatWord(String word) {
		return StringUtils.stripAccents(word).toUpperCase();
	}
	
	/**
	 * Calcula una puntuacion en base a ciertos factores de la partida, y devuelve el numero correspondiente
	 * @return
	 */
	private Long generatePuntuation() {
		Long puntuation = 0L;
		LocalDateTime endDate = LocalDateTime.now();
		long miliseconds = (50000 - (Duration.between(startDate, endDate).toMillis()/2))/2;
		
		if (miliseconds < 1) 
			miliseconds = 1;
		
		//Aplicamos incentivo por ganar la partida en muy poco tiempo
		if (attempts > 0) {
			if (miliseconds <= 5) miliseconds *=5;
			else if (miliseconds <= 10) miliseconds *= 3;
			else if (miliseconds <= 20) miliseconds *= 2;
			
			miliseconds *= attempts+1;
		}
		
		puntuation += miliseconds;	
		return puntuation;
	}

	
	//GETTERS & SETTERS------------------------------------------------------------------------------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getAttempts() {
		return attempts;
	}

	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public AppUser getOwner() {
		return owner;
	}

	public void setOwner(AppUser owner) {
		this.owner = owner;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
		if (finished)
			puntuation = generatePuntuation();
	}

	public List<String> getLettersUsed() {
		return lettersUsed;
	}

	public void setLettersUsed(List<String> lettersUsed) {
		this.lettersUsed = lettersUsed;
	}

	public String getFormattedWord() {
		return formattedWord;
	}

	public void setFormattedWord(String word) {
		this.formattedWord = formatWord(word);
	}

	public Long getPuntuation() {
		return puntuation;
	}

	public void setPuntuation(Long puntuation) {
		this.puntuation = puntuation;
	}

	public List<Play> getPlays() {
		return plays;
	}

	public void setPlays(List<Play> plays) {
		this.plays = plays;
	}

}
