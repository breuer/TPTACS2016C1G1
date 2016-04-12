package com.g1.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.g1.web.model.User;
import com.g1.web.model.Character;;

@Service
@Qualifier("characterService")
public class CharacterService {
	
	private static List<Character> characters;
    
    static{
    	characters= populateDummyCharacters();
    }

    private static List<Character> populateDummyCharacters(){
        List<Character> characters = new ArrayList<Character>();
        characters.add(new Character("iron man", 1));
        characters.add(new Character("thor", 2));
        characters.add(new Character("spider man", 3));
        characters.add(new Character("ant man", 4));
        return characters;
    }
	public List<Character> findAllCharacters() {
        return characters;
    }
}
