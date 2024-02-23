package ua.foxminded.springbootconsoleapp.dao;

import ua.foxminded.springbootconsoleapp.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDao {

    Optional<Course> findById(int id);

    Optional<Course> findByName(String name);

    List<Course> findAll();

    void create(Course course);

    void update(Course course);

    void deleteById(int id);

}
