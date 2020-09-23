package com.daniel.hangman.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daniel.hangman.models.Game;
import com.daniel.hangman.models.Play;
import com.daniel.hangman.repositories.PlayRepository;

@Service
public class PlayService {

	@Autowired
	PlayRepository playRepository;
	
	
	public Play save(Play u) {
		return playRepository.save(u);
	}
	
	public Play edit(Play u) {
		return playRepository.save(u);
	}
	
	public List<Play> findAll() {
		return playRepository.findAll();
	}
	
	public List<Play> findByGame(Game g) {
		return playRepository.findByGame(g);
	}
	
}

