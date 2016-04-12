package com.g1.web.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.g1.web.model.User;
import com.g1.web.model.Character;;

@Service
@Qualifier("characterService")
public class CharacterService {
	
	private static List<Character> characters;
	private static Set<Character> favouriteCharacters = new HashSet<Character>();
    
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
	
	public void markFavorite(long id) {
		for (Character c : characters) {
			if(c.getId() == id) {
				favouriteCharacters.add(c);
			}
		}
	}

	public void unmarkFavorite(long id) {
		Character cDelete = null;
		for (Character c : favouriteCharacters) {
			if(c.getId() == id) {
				cDelete = c;
			}
		}
		if(cDelete != null)
			favouriteCharacters.remove(cDelete);		
	}

	
	public List<Character> findAllFavorites() {
        return new ArrayList<Character>(favouriteCharacters);
    }
}
