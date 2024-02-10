package ua.foxminded.springbootconsoleapp;

import java.util.Optional;
import java.util.List;

public interface CourseDao {

	Optional<Course> findById(int id);

    Optional<Course> findByName(String name);

	List<Course> findAll();

	void create(Course course);

	void update(Course course);

	void deleteById(int id);

}
