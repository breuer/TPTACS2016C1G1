package com.g1.web.model;

import java.util.HashSet;
import java.util.Set;

public class Group {

    private long id;
    private String name;
    private User owner;
    private Set<Character> characters = new HashSet<Character>();

    public Group() {
        id = 0;
    }

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Character> getCharacters() {
        return this.characters;
    }

    public void addCharacter(Character character) {
        this.characters.add(character);
    }

    public void removeCharacter(Character character) {
        this.characters.remove(character);
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
