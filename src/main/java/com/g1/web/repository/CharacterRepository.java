package com.g1.web.repository;

import com.g1.web.model.Character;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class CharacterRepository extends IRepository<Character> {

    public CharacterRepository() { // TODO settear ranking de otra forma
        this.addItem(new Character("iron man", 1) {{
            setRanking(15);
        }});
        this.addItem(new Character("thor", 2) {{
            setRanking(20);
        }});
        this.addItem(new Character("spider man", 3) {{
            setRanking(12);
        }});
        this.addItem(new Character("ant man", 4) {{
            setRanking(5);
        }});
    }

    public Character getById(Long id) {
        return this.getItem(character -> character.getId().equals(id));
    }

    @Override
    public AtomicLong getCounter() {
        return counter;
    }
}