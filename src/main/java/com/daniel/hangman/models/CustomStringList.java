package com.daniel.hangman.models;

import java.util.ArrayList;
import java.util.List;

public class CustomStringList extends ArrayList<String> {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -4877597985383546020L;

	public CustomStringList() {}
	
	public CustomStringList(List<String> list) {
		this.addAll(list);
	}
	
	@Override
	    public boolean contains(Object o) {
	        String paramStr = (String)o;
	        for (String s : this) {
	            if (paramStr.equalsIgnoreCase(s)) return true;
	        }
	        return false;
	    }
	
}
