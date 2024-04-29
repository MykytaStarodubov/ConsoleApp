package ua.foxminded.springbootconsoleapp.dao.impl;

import org.springframework.stereotype.Repository;
import ua.foxminded.springbootconsoleapp.dao.GroupDao;
import ua.foxminded.springbootconsoleapp.model.Group;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JPAGroupDao implements GroupDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Group> findById(int id) {
        return Optional.ofNullable(entityManager.find(Group.class, id));
    }

    @Override
    public Optional<Group> findByName(String name) {
        return entityManager.createQuery("SELECT g FROM Group g WHERE g.name = :name", Group.class)
                .setParameter("name", name)
                .getResultList().stream().findFirst();
    }

    @Override
    public List<Group> findAll() {
        return entityManager.createQuery("SELECT g FROM Group g", Group.class).getResultList();
    }

    @Override
    public void create(Group group) {
        entityManager.persist(group);
    }

    @Override
    public void update(Group group) {
        entityManager.merge(group);
    }

    @Override
    public void deleteById(int id) {
        entityManager.createQuery("DELETE FROM Group g WHERE g.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
