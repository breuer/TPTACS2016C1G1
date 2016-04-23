package com.g1.web.model;

public class Character {

	private long id;
    
    private String name;

	private long ranking;
 
    public Character(){
        id=0;
    }
    
    public Character(String name, long id) {
    	this.name = name;
    	this.id = id;
    }

	public Long getId() {
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

	public long getRanking() {
		return ranking;
	}

	public void setRanking(long ranking) {
		this.ranking = ranking;
	}

	@Override
	public boolean equals(Object obj) {
		return !(obj == null || obj.getClass() != getClass()) && ((Character) obj).id == id;
	}
}
