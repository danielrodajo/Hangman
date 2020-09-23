package com.daniel.hangman.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.daniel.hangman.models.AppUser;
import com.daniel.hangman.models.Game;
import com.daniel.hangman.repositories.GameRepository;

@Service
public class GameService {

	@Autowired
	GameRepository gameRepository;
	
	
	public Game save(Game u) {
		return gameRepository.save(u);
	}
	
	public Game edit(Game u) {
		return gameRepository.save(u);
	}
	
	public List<Game> findAll() {
		return gameRepository.findAll();
	}

	public Game findById(Long id) {
		return gameRepository.findById(id).orElse(null);
	}
	
	public List<Game> findByOwner(AppUser user) {
		return gameRepository.findByOwner(user);
	}
	
	public List<Game> findGamesWon() {
		return gameRepository.findGamesWon();
	}
	
}

