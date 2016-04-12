package com.g1.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.g1.web.model.User;
import com.g1.web.model.Character;
import com.g1.web.model.Group;
import com.g1.web.pojo.CharacterRanking;

@Service
@Qualifier("characterService")
public class CharacterService {
	
	private static List<Character> characters;
	private static Set<Character> favouriteCharacters = new HashSet<Character>();
	private static Set<Group> groups = new HashSet<Group>();
	private static final AtomicLong counterGroup = new AtomicLong();
    
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
	
	public int markFavorite(long id) {
		int found = -1;
		for (Character c : characters) {
			if(c.getId() == id) {
				favouriteCharacters.add(c);
				found = 1;
			}
		}
		return found;
	}

	public int unmarkFavorite(long id) {
		Character cDelete = null;
		int found = -1;
		for (Character c : favouriteCharacters) {
			if(c.getId() == id) {
				found = 1;
				cDelete = c;
			}
		}
		if(found > 0)
			favouriteCharacters.remove(cDelete);
		return found;
	}

	
	public List<Character> findAllFavorites() {
        return new ArrayList<Character>(favouriteCharacters);
    }
	
	public void createGroup(String name) {
		Group g = new Group(name);
		g.setId(counterGroup.incrementAndGet());
		groups.add(g);
	}
	
	public Group getGroupByName(String name) {
		Group group = null;
		for(Group g : groups) {
			if (g.getName().equals(name))
				group = g;
		}
		return group;
	}

	public Group getGroupById(long id) {
		Group group = null;
		for(Group g : groups) {
			if (g.getId() == id)
				group = g;
		}
		return group;
	}
	
	public void addCharacterToGroup(String name, long id) {
		for(Group g : groups) {
			if (g.getName().equals(name)){
				for (Character c : characters) {
					if(c.getId() == id) {
						g.getCharacters().add(c);
					}
				}			
			}				
		}		
	}
	
	public void deleteCharacterFromGroup(String name, long id) {
		for(Group g : groups) {
			if (g.getName().equals(name)){
				Character characterToDelete = null;
				for (Character c : g.getCharacters()) {
					if(c.getId() == id) {
						characterToDelete = c;
					}
				}
				if (characterToDelete != null)
					g.getCharacters().remove(characterToDelete);
			}				
		}		
	}
	
	public Integer countGroup(){
		return groups.size();
	}
	
	public Integer countFavoriteCharacter() {
		Integer count = 0;
		for(Group g : groups) {
			count += g.getCharacters().size();
		}
		return count;
	}
	
	public List<Character> getRankingByOrder(String order) {
		Map<Long, Integer> rankingMap = new HashMap<Long, Integer>();
		Integer count = null;
		for(Group g : groups) {
			for(Character c : g.getCharacter()) {
				count = rankingMap.get(c.getId());
				if(count == null)
					rankingMap.put(c.getId(), 1);
				else{
					rankingMap.put(c.getId(), count ++);
				}				
			}
		}
		/*Iterator i = rankingMap.entrySet().iterator();
		List<CharacterRanking> cr = new ArrayList<CharacterRanking>();
		while (i.hasNext()) {
			Map.Entry e = (Map.Entry) i.next();
			cr.add(new CharacterRanking(e.getKey(), ))
		}*/
		return null;
	}
	
	public List<Character> getIntersection(long idGrupo1, long idGrupo2) {
		
		return null;
	}
}
