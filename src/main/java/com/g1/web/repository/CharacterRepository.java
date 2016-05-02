package com.g1.web.repository;

import com.g1.web.model.Character;
import java.util.List;
import java.util.Iterator;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class CharacterRepository extends IRepository<Character> {

    public CharacterRepository() {
    }

    public void setAll(List<Character> characterList){
        Iterator<Character> iter = characterList.iterator();
        while (iter.hasNext()) {
            this.addItem(iter.next());
        }
    }

    public Character getById(Long id) {
        return this.getItem(character -> character.getId().equals(id));
    }

    @Override
    public AtomicLong getCounter() {
        return counter;
    }

}