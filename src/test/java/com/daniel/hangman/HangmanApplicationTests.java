package com.daniel.hangman;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.daniel.hangman.models.Play;

@SpringBootTest
class HangmanApplicationTests {

	@Test
	void contextLoads() {
		Play play = new Play();
		play.setLetter("A");
		
		assertEquals(play, "A");	
	}

}
