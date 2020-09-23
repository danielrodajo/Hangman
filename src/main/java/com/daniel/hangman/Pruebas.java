package com.daniel.hangman;

import java.util.ArrayList;
import java.util.List;

import com.daniel.hangman.models.Play;

public class Pruebas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<Play> plays = new ArrayList<>();
		Play p = new Play();
		p.setLetter("P");
		plays.add(p);
		
		System.out.println(plays.contains("P"));
		System.out.println(plays.get(0).equals("P"));
		
		System.out.println("P".equals(plays.get(0)));
		
	}

}
