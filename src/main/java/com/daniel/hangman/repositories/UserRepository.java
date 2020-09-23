package com.daniel.hangman.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniel.hangman.models.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Long> {
	Optional<AppUser> findByEmailIgnoreCase(String email);
	Optional<AppUser> findByNameIgnoreCase(String name);
	Optional<AppUser> findById(Long id);
}
