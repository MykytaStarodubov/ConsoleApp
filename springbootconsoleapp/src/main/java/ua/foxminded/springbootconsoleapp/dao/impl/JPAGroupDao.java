package ua.foxminded.springbootconsoleapp.dao.impl;

import org.springframework.stereotype.Repository;
import ua.foxminded.springbootconsoleapp.dao.GroupRepository;
import ua.foxminded.springbootconsoleapp.model.Group;
import ua.foxminded.springbootconsoleapp.model.*;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class JPAGroupDao implements GroupRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Optional<Group> findById(int id) {
		return Optional.ofNullable(entityManager.find(Group.class, id));
	}

	@Override
	public Optional<Group> findByName(String name) {
		return entityManager.createQuery(SqlQueriesUtil.SELECT_FROM_GROUP_NAME, Group.class)
				.setParameter("name", name).getResultList().stream().findFirst();

	}

	@Override
	public List<Group> findAll() {
		return entityManager.createQuery(SqlQueriesUtil.SELECT_FROM_GROUP, Group.class).getResultList();
	}
	
	@Transactional
	@Override
	public void create(Group group) {
		entityManager.persist(group);
	}
	
	@Transactional
	@Override
	public void update(Group group) {
		entityManager.merge(group);
	}
	
	@Transactional
	@Override
	public void deleteById(int id) {
		entityManager.createQuery(SqlQueriesUtil.DELETE_FROM_GROUP_ID, Group.class).setParameter("id", id).executeUpdate();
	}
	
	public abstract Group getReferenceById(Integer id);
}
