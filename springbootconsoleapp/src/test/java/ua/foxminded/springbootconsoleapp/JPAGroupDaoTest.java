package ua.foxminded.springbootconsoleapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ua.foxminded.springbootconsoleapp.model.Course;
import ua.foxminded.springbootconsoleapp.model.Group;
import java.util.Arrays;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import ua.foxminded.springbootconsoleapp.dao.impl.JPACourseDao;
import ua.foxminded.springbootconsoleapp.dao.GroupRepository;
import ua.foxminded.springbootconsoleapp.dao.impl.JPAGroupDao;
import ua.foxminded.springbootconsoleapp.model.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.springbootconsoleapp.model.Group;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@ExtendWith(MockitoExtension.class)
class JPAGroupDaoTest {

	@Mock
	private EntityManager entityManager;

	@Mock
	private TypedQuery<Group> query;

	@InjectMocks
	private JPAGroupDao jpaGroupDao = new JPAGroupDaoImpl();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void findByName_ShouldReturnGroup_WhenCourseExists() {
		Group group = new Group(1, "AB");
		when(entityManager.createQuery(anyString(), eq(Group.class))).thenReturn(query);
		when(query.setParameter(anyString(), anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(Arrays.asList(group));

		Optional<Group> result = jpaGroupDao.findByName("AB");

		assertTrue(result.isPresent());
		assertEquals(group, result.get());
	}

	@Test
	void findAll_ShouldReturnAllGroups() {
		Group course1 = new Group(1, "AB");
		Group course2 = new Group(2, "BC");
		List<Group> courses = Arrays.asList(course1, course2);
		when(entityManager.createQuery(anyString(), eq(Group.class))).thenReturn(query);
		when(query.getResultList()).thenReturn(courses);

		List<Group> result = jpaGroupDao.findAll();

		assertEquals(2, result.size());
		assertEquals(courses, result);
	}

	@Test
	void create_ShouldPersistGroup() {
		Group course = new Group(1, "AB");
		doNothing().when(entityManager).persist(course);

		jpaGroupDao.create(course);

		verify(entityManager, times(1)).persist(course);
	}

	@Test
	void update_ShouldMergeGroup() {
		Group course = new Group(1, "AB");
		when(entityManager.merge(course)).thenReturn(course);

		jpaGroupDao.update(course);

		verify(entityManager, times(1)).merge(course);
	}

	private static class JPAGroupDaoImpl extends JPAGroupDao {
		@Override
		public Group getReferenceById(Integer id) {
			return null;
		}
	}

}
