package ua.foxminded.springbootconsoleapp.dao;

import ua.foxminded.springbootconsoleapp.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupDao {

    Optional<Group> findById(int id);

    Optional<Group> findByName(String name);

    List<Group> findAll();

    void create(Group group);

    void update(Group group);

    void deleteById(int id);
    
    
}
