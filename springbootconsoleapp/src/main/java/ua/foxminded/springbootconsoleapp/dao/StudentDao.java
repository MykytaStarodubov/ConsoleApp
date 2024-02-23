package ua.foxminded.springbootconsoleapp.dao;

import ua.foxminded.springbootconsoleapp.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {
    Optional<Student> findById(int id);

    List<Student> findAll();

    List<Student> findByGroupId(int groupId);

    void create(Student student);

    void update(Student student);

    void deleteById(int id);

}