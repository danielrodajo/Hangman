package com.daniel.hangman.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Play {

	@Id @GeneratedValue
	private Long position;
	
	@ManyToOne
	private Game game;
	
	private String word;
	
	private String letter;
	
	private Integer attempts;
	
	
	public Play() {}
	
	public Play(Game game, String word, String letter, Integer attempts) {
		this.game = game;
		this.word = word;
		this.letter = letter;
		this.attempts = attempts;
	}


	//GETTERS & SETTERS--------------------------------------------------------------------------------------------------------
	public Long getPosition() {
		return position;
	}


	public void setPosition(Long position) {
		this.position = position;
	}


	public Game getGame() {
		return game;
	}


	public void setGame(Game game) {
		this.game = game;
	}


	public String getWord() {
		return word;
	}


	public void setWord(String word) {
		this.word = word;
	}


	public String getLetter() {
		return letter;
	}


	public void setLetter(String letter) {
		this.letter = letter;
	}


	public Integer getAttempts() {
		return attempts;
	}


	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}

}
