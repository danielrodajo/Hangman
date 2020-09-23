package com.daniel.hangman;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.daniel.hangman.models.AppUser;
import com.daniel.hangman.models.Game;
import com.daniel.hangman.services.GameService;
import com.daniel.hangman.services.UserService;

@SpringBootApplication
public class HangmanApplication {

	public static void main(String[] args) {
		SpringApplication.run(HangmanApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner initData(UserService userService, GameService gameService) {
		return args -> {
//			AppUser user = new AppUser("daniel", "daniel.rodajo98@gmail.com", "daniel", 3);
//			userService.save(user);
//			Game game = new Game("paco", userService.findByName("daniel"));
//			game.setStartDate(LocalDateTime.now());
//			gameService.save(game);
//			Game game2 = new Game("mañana", userService.findByName("daniel"));
//			game2.setStartDate(LocalDateTime.now());
//			gameService.save(game2);
//			Game game3 = new Game("aborto", userService.findByName("daniel"));
//			game3.setStartDate(LocalDateTime.now());
//			gameService.save(game3);
//			Game game4 = new Game("noseque", userService.findByName("daniel"));
//			game4.setStartDate(LocalDateTime.now());
//			gameService.save(game4);
		};
	}

}
