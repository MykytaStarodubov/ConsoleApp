package ua.foxminded.springbootconsoleapp;

import java.util.Optional;
import java.util.List;

public interface GroupDao {

	Optional<Group> findById(int id);

	List<Group> findAll();

	void create(Group group);

	void update(Group group);

	void deleteById(int id);

}
