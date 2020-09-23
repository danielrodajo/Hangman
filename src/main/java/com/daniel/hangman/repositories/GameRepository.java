package com.daniel.hangman.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.daniel.hangman.models.AppUser;
import com.daniel.hangman.models.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
	Optional<Game> findById(Long id);
	List<Game> findByOwner(AppUser u);
	
	@Query("SELECT g FROM Game g WHERE g.finished = 1 AND g.attempts > 0 ORDER BY g.puntuation DESC")
	List<Game> findGamesWon();
	
}
