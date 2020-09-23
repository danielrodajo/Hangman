package com.daniel.hangman.storage;

import java.util.List;

public interface StorageService {
	
	String getRandomWord();
	List<String> getWords();
	boolean updateWord(String original, String updated);
	boolean addWords(List<String> words);
	boolean removeWord(String word);
	List<String> getWordsFromString(String text);
	
	boolean isUser();
	void setUser(boolean isUser);
}
