package com.daniel.hangman.controllers;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.daniel.hangman.models.Game;
import com.daniel.hangman.services.GameService;

@Controller
public class StatisticsController extends GlobalController {

	@Autowired
	private GameService gameService;
	
	@ModelAttribute("data")
	public Map<String, Integer> data() {
		List<Game> games = gameService.findByOwner(getCurrentUser());
		Map<String, Integer> alphabet = new TreeMap<>();
		for (int i=65; i<=90; i++)
			alphabet.put(String.valueOf((char)i), 0);
		
		games.forEach(game -> {
			game.getLettersUsed().forEach(letter -> {
				alphabet.replace(letter, alphabet.get(letter)+1);
			});
		});
		
		Map<String, Integer> orderAlphabet = alphabet
		        .entrySet()
		        .stream()
		        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		
		
		return orderAlphabet;
	}
	
	@GetMapping("/statistics")
	public String loadStatistics() {
		return "statistics";
	}
	
}
