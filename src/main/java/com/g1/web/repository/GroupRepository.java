package com.g1.web.repository;

import com.g1.web.model.Character;
import com.g1.web.model.Group;
import com.g1.web.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GroupRepository extends IRepository<Group> {

    public GroupRepository() {
    }

    @Override
    public AtomicLong getCounter() {
        return counter;
    }

    public Group getById(Long id) {
        return this.getItem(group -> group.getId().equals(id));
    }

    public List<Group> getByUser(User user) {
        return this.filterItems(group -> group.getOwner().getId().equals(user.getId()));
    }

    public Integer countByUser(User user) {
        return this.getByUser(user).size();
    }

    public Group getByUserAndName(User user, String groupName) {
        try {
            return this.filterItems(group -> group.getOwner().getId().equals(user.getId()) && group.getName().equals(groupName)).get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void addCharacter(Group group, Character character) {
        this.getById(group.getId()).addCharacter(character);
    }

    public void removeCharacter(Group group, Character character) {
        this.getById(group.getId()).removeCharacter(character);
    }
}