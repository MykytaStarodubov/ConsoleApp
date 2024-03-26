package ua.foxminded.springbootconsoleapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.sql.ResultSet;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.foxminded.springbootconsoleapp.dao.GroupDao;
import ua.foxminded.springbootconsoleapp.dao.StudentDao;
import ua.foxminded.springbootconsoleapp.dao.impl.JdbcStudentDao;
import ua.foxminded.springbootconsoleapp.model.Group;
import ua.foxminded.springbootconsoleapp.dao.impl.JdbcGroupDao;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.RowMapper;


@ExtendWith(MockitoExtension.class)
class JdbcGroupDaoTest {

	@Mock
	private JdbcTemplate jdbcTemplate;
	private GroupDao Dao;

	@InjectMocks
	private JdbcGroupDao jdbcGroupDao;

	@Test
	void testFindAll_ReturnsListOfGroups() {
	  
	    List<Group> expectedGroups = Arrays.asList(
	            new Group(1, "Group1"),
	            new Group(2, "Group2")
	    );

	
	    String expectedQuery = "SELECT * FROM groups";

	    when(jdbcTemplate.query(eq(expectedQuery), any(RowMapper.class))).thenReturn(expectedGroups);
	  
	    List<Group> actualGroups = jdbcGroupDao.findAll();
	   
	    assertEquals(expectedGroups, actualGroups);
	    verify(jdbcTemplate).query(eq(expectedQuery), any(RowMapper.class));
	}


	@Test
	void testCreate_ValidGroup_CreatesGroup() {

		Group group = new Group(1, "TestGroup");
		jdbcGroupDao.create(group);
		verify(jdbcTemplate).update(anyString(), eq(group.getName()));
	}

	@Test
	void testUpdate_ValidGroup_UpdatesGroup() {
		Group group = new Group(1, "UpdatedTestGroup");

		jdbcGroupDao.update(group);
		verify(jdbcTemplate).update(anyString(), eq(group.getName()), eq(group.getId()));
	}

}