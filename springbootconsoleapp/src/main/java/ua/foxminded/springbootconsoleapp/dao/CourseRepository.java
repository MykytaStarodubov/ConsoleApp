package ua.foxminded.springbootconsoleapp.dao;

import ua.foxminded.springbootconsoleapp.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    Optional<Course> findById(int id);

    Optional<Course> findByName(String name);

    List<Course> findAll();

    void create(Course course);

    void update(Course course);

    void deleteById(int id);

}
