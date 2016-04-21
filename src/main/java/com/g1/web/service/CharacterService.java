package com.g1.web.service;

import com.g1.web.model.Character;
import com.g1.web.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Qualifier("characterService")
public class CharacterService {

    public CharacterService() {
    }

    private enum Order {
        ID, NAME, RANKING
    }

    @Autowired
    CharacterRepository characterRepository;

    public List<Character> getAll() {
        return characterRepository.getAll();
    }

    public Character getById(Long idPersonaje) {
        return characterRepository.getById(idPersonaje);
    }

    public List<Character> OrderCharacters(List<Character> characters, String order) {
        Comparator<Character> comparator;

        try {
            switch (Order.valueOf(order.toUpperCase().trim())) {
                case ID: {
                    comparator = (o1, o2) -> o1.getId() > o2.getId() ? 1 : -1;
                    break;
                }
                case NAME:
                    comparator = (o1, o2) -> o1.getName().length() > o2.getName().length() ? 1 : -1;
                    break;
                case RANKING:
                    comparator = (o1, o2) -> o1.getRanking() > o2.getRanking() ? 1 : -1;
                    break;
                default:
                    comparator = (o1, o2) -> 1;
            }

            characters.sort(comparator);

        }catch (IllegalArgumentException ignored){};

        return characters;
    }
}