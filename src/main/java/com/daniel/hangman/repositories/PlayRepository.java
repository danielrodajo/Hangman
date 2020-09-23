package com.daniel.hangman.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniel.hangman.models.Game;
import com.daniel.hangman.models.Play;

@Repository
public interface PlayRepository extends JpaRepository<Play, Long> {
	List<Play> findByGame(Game g);
}
