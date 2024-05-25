package ua.foxminded.springbootconsoleapp.dao.impl;

import org.springframework.stereotype.Repository;
import ua.foxminded.springbootconsoleapp.dao.CourseRepository;
import ua.foxminded.springbootconsoleapp.model.Course;
import ua.foxminded.springbootconsoleapp.model.SqlQueriesUtil;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class JPACourseDao implements CourseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Course> findByName(String name) {
        return entityManager.createQuery(SqlQueriesUtil.DELETE_FROM_COURSE_NAME, Course.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst();
    }
    

    @Override
    public Optional<Course> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Course.class, id));
    }
    
  
    @Override
    public List<Course> findAll() {
        return entityManager.createQuery(SqlQueriesUtil.DELETE_FROM_COURSE, Course.class)
                .getResultList();
    }
    
    @Transactional
    @Override
    public void create(Course course) {
        entityManager.persist(course);
    }
    
    @Transactional
    @Override
    public void update(Course course) {
        entityManager.merge(course);
    }
    
    @Transactional
    @Override
    public void deleteById(int id) {
        Course course = entityManager.find(Course.class, id);
        if (course != null) {
            entityManager.remove(course);
        }
    }
}

