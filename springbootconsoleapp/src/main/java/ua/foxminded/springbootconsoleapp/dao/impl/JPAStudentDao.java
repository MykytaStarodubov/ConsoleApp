package ua.foxminded.springbootconsoleapp.dao.impl;

import org.springframework.stereotype.Repository;
import ua.foxminded.springbootconsoleapp.dao.StudentRepository;
import ua.foxminded.springbootconsoleapp.model.Student;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JPAStudentDao implements StudentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Student> findById(int id) {
        return Optional.ofNullable(entityManager.find(Student.class, id));
    }

    @Override
    public List<Student> findAll() {
        return entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

    @Override
    public List<Student> findByGroupId(int groupId) {
        return entityManager.createQuery("SELECT s FROM Student s WHERE s.groupId = :groupId", Student.class)
                .setParameter("groupId", groupId)
                .getResultList();
    }

    @Override
    public void create(Student student) {
        entityManager.persist(student);
    }

    @Override
    public void update(Student student) {
        entityManager.merge(student);
    }

    @Override
    public void deleteById(int id) {
        entityManager.createQuery("DELETE FROM Student s WHERE s.studentId = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
