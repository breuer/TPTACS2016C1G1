package com.g1.web.service;

import com.g1.config.MarvelConfig;
import com.g1.web.model.Character;
import com.g1.web.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;

@Service
@Qualifier("characterService")
public class CharacterService {

    @Autowired
    CharacterRepository characterRepository;
    @Autowired
    MarvelService marvelService;

    public CharacterService() {
    }

    @PostConstruct
    private void Initialization() {
    /*  -silverhati: como los objetos autowired se instancian luego del constructor,
        meto creé un método de inicialización @PostConstruct. */

        try {
            //Leer personajes de Marvel guardarlos en el repo
            characterRepository.setAll(marvelService.getAllCharacters());
        }
        catch (HttpServerErrorException e) {
        // TODO: completar con algo (log?)
        } catch (HttpClientErrorException e) {
        // TODO: completar con algo (log?)
        }
    }
    private enum Order {
        ID, NAME, RANKING
    }

    public List<Character> getAll() {
        return characterRepository.getItems();
    }

    public List<Character> get(int offset, int limit) {
        try {
            return characterRepository.getItems(offset, limit);
        }
        catch (IllegalArgumentException e){
            throw e;
        }
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