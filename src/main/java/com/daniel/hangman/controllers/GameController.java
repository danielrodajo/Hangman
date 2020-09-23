package com.daniel.hangman.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.daniel.hangman.models.Game;
import com.daniel.hangman.models.Play;
import com.daniel.hangman.services.GameService;
import com.daniel.hangman.services.PlayService;
import com.daniel.hangman.services.UserService;
import com.daniel.hangman.storage.StorageService;

/**
 * Controlador encargado de gestionar las peticiones HTTP provenientes de la partida
 * @author mañana
 *
 */
@Controller
public class GameController extends GlobalController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private PlayService playService;
	
	@Autowired
	@Qualifier("global")
	private StorageService storageService;

	@Autowired
	@Qualifier("user")
	private StorageService storageUserService;
	
	private Game currentGame;

	@ModelAttribute("current_game")
	public Game getCurrentGame() {
		//Devolvemos un objeto vacio únicamente para evitar errores iniciales de null pointers
		return new Game("", getCurrentUser());
	}
	
	@PostConstruct
	public void init() {
//		System.out.println("EJECUTADO METODO INIT");
//		storageUserService.setUser(true);
//		System.out.println(storageService.isUser());
//		System.out.println(storageUserService.isUser());
	}
	
	
	/**
	 * Se encarga de generar una nueva partida, obteniendo la palabra para jugar dicha partida,
	 * la almacena en la BBDD y redirecciona al método encargado de iniciarla.
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/game/new/{mode}/{id}")
	public String newGame(Model model, @PathVariable long mode, @PathVariable long id) {
		if (userService.findById(id) == null)
			return "redirect:/";
		
		String word = null;
		if (mode == 1)
			word = storageService.getRandomWord();
		else if (mode == 2) {
			if (storageUserService.getWords().isEmpty())
				return "redirect:/index";
			word = storageUserService.getRandomWord();
		}
		Game game = new Game(word, getCurrentUser());
		gameService.save(game);
		return "redirect:/game/"+game.getId();
	}
	
	
	/**
	 * Reanuda una partida ya existente
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/game/edit/{id}")
	public String editGame(Model model, @PathVariable long id) {
		if (gameService.findById(id) == null)
			return "redirect:/";
		return "redirect:/game/"+id;
	}
	

	/**
	 * Gestiona la peticion HTTP para el inicio de una partida (ya sea una nueva, o una reanudada).
	 * También es la encargada de recibir la letra a comprobar en el juego, y hacer el proceso de validación.
	 * @param model
	 * @param id
	 * @param query
	 * @return
	 */
	@GetMapping("/game/{id}")
	public String initGame(Model model, @PathVariable long id, @RequestParam(name="q", required=false) String query) {
		Game game = gameService.findById(id);
		if (game == null || game.isFinished())
			return "redirect:/index";
		
		//Si no es nula ni está vacía la consulta, empieza el proceso de validación
		if (query != null && !query.trim().isEmpty()) {
			String letter = query.toUpperCase();
			
			//Si aun no ha sido usada, y el caracter es una letra alfabetica...
			if (!game.getLettersUsed().contains(letter) && (letter.charAt(0) >= 'A' && letter.charAt(0) <='Z')) {	
				if (!game.getFormattedWord().contains(letter)) {
					boolean hasAttempts = loseAttempt(game);
					//Si se acabaron los intentos, fin del juego
					if (!hasAttempts)
						game.setFinished(true);
				}	
				//Agregamos la letra al listado de letras usadas y generamos el objeto Play que contenga los datos de esta jugada
				game.getLettersUsed().add(letter);
				game.getPlays().add(generatePlay(game, letter));
				
				//Si ya acertó la palabra, fin del juego
				if (guessedWord(game))  
					game.setFinished(true);
				//Guardamos cambios de la partida en la BBDD
				gameService.edit(game);
			}
		}
		
		//Añadimos una pista adicional en caso de ganar la partida
		if (game.isFinished() && game.getAttempts() > 0) {
			user.setHints(user.getHints()+1);
			userService.edit(user);
		}
		
		currentGame = game;
		model.addAttribute("current_game", game);
		return "game";
	}
	
	/**
	 * Devuelve una pista la cual consta de 3 letras, 1 correcta y 2 incorrectas
	 * @return
	 */
	@RequestMapping("/game/help")
    public @ResponseBody String takeHint() {
		//Si no le quedan pistas al usuario, no devolvemos nada
		if (user.getHints() == 0)
			return "";
		//Restamos una pista en caso de tener pistas disponibles
		user.setHints(user.getHints()-1);
		
		//Creamos listas que contendrán, una las letrás que contiene la palabra, y la otra el resto de letras 
		List<Character> lettersFromWord = new ArrayList<>();
		List<Character> alphabet = new ArrayList<>();
		
		//Lista que guardará las letras que finalmente devolvamos
		List<String> letters = new ArrayList<>();
		
		//Rellenamos set de las letras de la palabra formateada (filtramos aquellas que ya hayan sido usadas) y finalmente barajamos las letras
		for (int i=0; i<currentGame.getFormattedWord().length(); i++) 
			if (!currentGame.getLettersUsed().contains(String.valueOf(currentGame.getFormattedWord().charAt(i))))
				lettersFromWord.add(currentGame.getFormattedWord().charAt(i));
		Collections.shuffle(lettersFromWord);
		
		//Extraemos la primera letra
		letters.add(String.valueOf(lettersFromWord.get(0)));
		
		//Rellenamos set de las letras del alfabeto (filtramos aquellas que sean válidas porque la contiene la palabra y que ya hayan sido usadas) y finalmente barajamos las letras
		for (int i=65; i<=90; i++)  {
			if (!currentGame.getFormattedWord().contains(String.valueOf(String.valueOf((char)i))) && !currentGame.getLettersUsed().contains(String.valueOf((char)i)))
				alphabet.add((char)i);
		}
		Collections.shuffle(alphabet);
		
		//Extraemos las letras inválidas ya filtradas y barajadas
		while (letters.size() < 3)
			letters.add(String.valueOf(alphabet.remove(0)));				
		
		
		//Actualizamos el nº de pistas en la BBDD
		userService.edit(user);
		//Barajamos las letras recuperadas para que no sea predecible el resultado en base al orden
		Collections.shuffle(letters);
		
		//Devolvemos 3 <li> con una letra cada uno, que posteriormente será agregado al listado <ul> de 'game.html'
        return "<li class=\"letterhint\">"+letters.get(0)+"</li> <li class=\"letterhint\">"+letters.get(1)+"</li> <li class=\"letterhint\">"+letters.get(2)+"</li>";
    }
	
	
	/**
	 * Resta un intento a la partida y devuelve un indicador de si todavía le quedan intentos
	 * @param game
	 * @return
	 */
	private boolean loseAttempt(Game game) {
		if (game.getAttempts() > 0)
			game.setAttempts(game.getAttempts()-1);
		return game.getAttempts() > 0;
	}
	
	/**
	 * Devuelve un indicador de si ya se averiguó la palabra de la partida, comprobando si todas las letras
	 * de la palabra están contenidas en la lista de letras usadas.
	 * @param game
	 * @return
	 */
	private boolean guessedWord(Game game) {
		for (int i=0; i<game.getFormattedWord().length(); i++) {
			if (!game.getLettersUsed().contains(game.getFormattedWord().substring(i,i+1)))
				return false;
		}
	
		return true;
	}
	
	
	
	private Play generatePlay(Game game, String letter) {
		String word = "";
		
		for (int i=0; i < game.getFormattedWord().length(); i++) {
			if (game.getLettersUsed().contains(game.getFormattedWord().substring(i, i+1)))
				word += game.getWord().substring(i, i+1);
			else 
				word += "_";
		}
		
		Play play = new Play(game, word, letter, game.getAttempts());
		playService.save(play);
		return play;
	}

}
