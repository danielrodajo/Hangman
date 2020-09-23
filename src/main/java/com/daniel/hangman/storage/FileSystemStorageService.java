package com.daniel.hangman.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daniel.hangman.controllers.GlobalController;
import com.daniel.hangman.models.CustomStringList;

@Service("global")
public class FileSystemStorageService implements StorageService {

	@Autowired
	private GlobalController controller;

	private final String DIRECTORY = "wordstores";
	private final String SYSTEMFILENAME = "palabras.txt";
	
	private boolean isUser = false;
	
	@PostConstruct
	public void init() {
		if (!Files.exists(getFile().toPath())) {
			try {
				getFile().createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getRandomWord() {
		try {
			List<String> lines = FileUtils.readLines(getFile(), Charset.forName("UTF-8"));
			
			Random random = new Random();
			return lines.get(random.nextInt(lines.size()));			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<String> getWords() {
		try {
			List<String> lines = FileUtils.readLines(getFile(), Charset.forName("UTF-8"));
			lines.forEach(System.out::println);
			List<String> words = new ArrayList<>();
			lines.forEach(word -> words.add(word));
			return words;		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean updateWord(String original, String updated) {
		CustomStringList currentWords = new CustomStringList(getWords());
		if (!currentWords.contains(original) || currentWords.contains(updated) || !isWordValid(updated))
			return false;
		
		currentWords.remove(original);
		currentWords.add(updated);
		Collections.sort(currentWords);
		
		return writeWords(currentWords, getFile());
	}

	@Override
	public boolean addWords(List<String> words) {
		if (!Files.exists(getFile().toPath()))
			try {
				getFile().createNewFile();
			} catch (IOException e) {
				return false;
			}
		
		CustomStringList customList = new CustomStringList(getWords());
		words.forEach(tempWord -> {
			if (!((CustomStringList)customList).contains(tempWord))
				customList.add(tempWord);
		});
		
		Collections.sort(customList);
		
		return writeWords(customList, getFile());
	}

	@Override
	public boolean removeWord(String word) {
		CustomStringList currentWords = new CustomStringList(getWords());
		if (!currentWords.contains(word)) 
			return false;		
		currentWords.remove(word);
		return writeWords(currentWords,getFile());
	}
	
	public List<String> getWordsFromString(String text) {
		List<String> words = new CustomStringList();
		
		String[] fileWords = text.split("\\s+");
		for (String word: fileWords) {
			word = word.replaceAll("[^\\w]", "");
			if (!words.contains(word) && containsOnlyLetters(word))
				words.add(word);
		}
		
		return words;
	}
	
	private boolean containsOnlyLetters(String word) {
		word = word.toLowerCase();
	      char[] charArray = word.toCharArray();
	      for (int i = 0; i < charArray.length; i++) {
	         char ch = charArray[i];
	         if (!(ch >= 'a' && ch <= 'z')) {
	            return false;
	         }
	      }
	      return true;
	}


	private File getFile() {
		return (isUser())?getFileUser():getFileSystem();
	}
	
	private File getFileSystem() {
    	return new File(DIRECTORY+File.separator+SYSTEMFILENAME);
    }
    
	private File getFileUser() {
    	return new File(DIRECTORY+File.separator+getFilenameUser());
    }
    
    private String getFilenameUser() {
    	return controller.getCurrentUser().getId()+".txt";
    }

    
    private boolean isWordValid(String updated) {
		if (updated.trim().length() == 0)
			return false;
		char[] letters = updated.toUpperCase().toCharArray();
		for (char letter:letters) {
			if (letter < 'A' || letter > 'Z')
				return false;
		}
		return true;
	}
    
    private boolean writeWords(CustomStringList customList, File file) {
		try (PrintWriter writer = new PrintWriter(file)) {
			customList.forEach(word -> {
				writer.printf("%s\r\n", word);
			});
		} catch (FileNotFoundException e) {
			return false;
		}	
		return true;
	}

    
    
	public boolean isUser() {
		return isUser;
	}

	public void setUser(boolean isUser) {
		this.isUser = isUser;
	}

}
