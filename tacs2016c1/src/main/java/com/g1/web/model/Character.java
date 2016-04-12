package com.g1.web.model;

public class Character {

	private long id;
    
    private String name;
     
 
    public Character(){
        id=0;
    }
    
    public Character(String name, long id) {
    	this.name = name;
    	this.id = id;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
