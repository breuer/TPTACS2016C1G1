package com.g1.web.model;

import java.util.HashSet;
import java.util.Set;

public class Group {

	private long id;
	private String name;
	private Set<Character> character;
	
	public Group(){
        id=0;
    }
	
	public Group(String name) {
		this.name = name;
		this.character = new HashSet<Character>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Character> getCharacter() {
		return character;
	}
	public void setCharacter(Set<Character> character) {
		this.character = character;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
