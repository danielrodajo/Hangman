package com.daniel.hangman.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.daniel.hangman.models.CustomStringList;
import com.daniel.hangman.storage.StorageService;

@Controller
public class WordsConfigurationController extends GlobalController {

	private List<String> tempWords;
	
	@Autowired
	@Qualifier("global")
	private StorageService storageService;

	@Autowired
	@Qualifier("user")
	private StorageService storageUserService;
	
	@PostConstruct
	public void init() {
		System.out.println("INIT EJECUTADO");
		storageUserService.setUser(true);
	}
	
	@ModelAttribute("userwords")
	public List<String> getWords() {
		return storageUserService.getWords();
	}
	
	@GetMapping("/wordconfiguration")
	public String wordconfiguration() {
		tempWords = new CustomStringList();
		return "wordconfiguration";
	}
	
	@PostMapping("/wordconfiguration/upload")
	public @ResponseBody  List<String> formatWords(@RequestParam("file") MultipartFile multipartFile) {
		try {
			tempWords = storageService.getWordsFromString(new String(multipartFile.getBytes()));				
		} catch (IOException e) {
			e.printStackTrace();
		}
		tempWords = tempWords.stream().filter(word -> word.length() > 0).collect(Collectors.toList());
		return tempWords;
	}
	
	@PostMapping("/wordconfiguration/upload/success")
	public String saveWords() {
		storageUserService.addWords(tempWords);
		return "redirect:/wordconfiguration";
	}

	@GetMapping("/wordconfiguration/delete/{word}")
	public String deleteWord(@PathVariable String word) {
		storageUserService.removeWord(word);
		return "redirect:/wordconfiguration";
	}
	
	@PostMapping("/wordconfiguration/edit")
	public @ResponseBody Boolean editWord(@RequestParam("original") String original, @RequestParam("new") String newWord) {
		if (original.equalsIgnoreCase(newWord))
			return false;
		return storageUserService.updateWord(original, newWord);
	}
	
}
