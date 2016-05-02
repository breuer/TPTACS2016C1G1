package com.g1.web.service;

import com.g1.HttpException;
import com.g1.web.model.Character;
import com.g1.web.model.Group;
import com.g1.web.model.User;
import com.g1.web.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Qualifier("characterService")
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    public GroupService() {
    }

    public List<Group> getAll() {
        return groupRepository.getItems();
    }

    public List<Group> getByUser(User user) {
        return groupRepository.getByUser(user);
    }

    public Group getById(long id){
        return groupRepository.getById(id);
    }

    public Group getByUserAndName(User user, String groupName) {
        return groupRepository.getByUserAndName(user, groupName);
    }

    public Integer countByUser(User user) {
        return groupRepository.countByUser(user);
    }

    public Long saveGroup(User user, Group group) throws HttpException {

        List<String> names = this.getByUser(user).stream().map(group1 -> group.getName()).collect(Collectors.toList());

        // validar que el nombre de grupo no exista
        if (names.contains(group.getName()))
            throw new HttpException(
                    ResponseEntity
                            .status(HttpStatus.CONFLICT)
                            .body("El usuario ya tiene un grupo con ese nombre"));

        group.setOwner(user);
        group.setId(groupRepository.getCounter().incrementAndGet());

        groupRepository.addItem(group);

        return group.getId();
    }

    public void addCharacter(Group group, Character character) {
        groupRepository.addCharacter(group, character);
    }

    public void removeCharacter(Group group, Character character) {
        groupRepository.removeCharacter(group, character);
    }

    public List<Character> intersection(long idGrupo1, long idGrupo2) {
        Set<Character> chars1 = this.getById(idGrupo1).getCharacters();
        Set<Character> chars2 = this.getById(idGrupo2).getCharacters();

        return chars1.stream().filter(chars2::contains).collect(Collectors.toList());
    }
}
