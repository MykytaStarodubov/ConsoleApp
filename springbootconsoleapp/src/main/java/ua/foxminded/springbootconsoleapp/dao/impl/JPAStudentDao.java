package ua.foxminded.springbootconsoleapp.dao.impl;

import org.springframework.stereotype.Repository;
import ua.foxminded.springbootconsoleapp.dao.StudentRepository;
import ua.foxminded.springbootconsoleapp.model.SqlQueriesUtil;
import ua.foxminded.springbootconsoleapp.model.Student;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class JPAStudentDao implements StudentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Student> findById(int id) {
        return Optional.ofNullable(entityManager.find(Student.class, id));
    }

    @Override
    public List<Student> findAll() {
        return entityManager.createQuery(SqlQueriesUtil.SELECT_FROM_STUDENT, Student.class).getResultList();
    }

    @Override
    public List<Student> findByGroupId(int groupId) {
        return entityManager.createQuery(SqlQueriesUtil.SELECT_FROM_STUDENT_GROUP_ID, Student.class)
                .setParameter("groupId", groupId)
                .getResultList();
    }
    
    @Transactional
    @Override
    public void create(Student student) {
        entityManager.persist(student);
    }
    
    @Transactional
    @Override
    public void update(Student student) {
        entityManager.merge(student);
    }
    
    @Transactional
    @Override
    public void deleteById(int id) {
        entityManager.createQuery(SqlQueriesUtil.DELETE_FROM_STUDENT_STUDENT_ID)
                .setParameter("id", id)
                .executeUpdate();
    }
}
