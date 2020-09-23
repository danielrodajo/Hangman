package com.daniel.hangman.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.daniel.hangman.models.Game;
import com.daniel.hangman.services.GameService;

/**
 * Controlador encargado de gestionar las peticiones HTTP provenientes del indice del sitio web
 * @author mañana
 *
 */
@Controller
public class IndexController extends GlobalController {
	
	private Boolean globalscore;
	
	@Autowired
	private GameService gameService;

	
	@ModelAttribute("games")
	public List<Game> allGames() {
		return gameService.findGamesWon();
	}
	
	@ModelAttribute("globalscore")
	public Boolean isGlobalScore() {
		return globalscore;
	}
	
	@ModelAttribute("mygames")
	public List<Game> myGames() {
		return gameService.findByOwner(getCurrentUser());
	}
	
	@ModelAttribute("resume_game")
	public Game game() {
		return new Game("", getCurrentUser());
	}
	
	
	@GetMapping({"/", "/index"})
	public String index() {
		globalscore = true;
		return "index";
	}
	
	@GetMapping("/game/resume/{id}")
	public String resumeGame(Model model, @PathVariable long id) {
		Game game = gameService.findById(id);
		if (game == null)
			return "redirect:/index";
		
		model.addAttribute("resume_game", game);
		return "resume";
	}
	
}
