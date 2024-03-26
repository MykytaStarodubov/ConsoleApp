package ua.foxminded.springbootconsoleapp;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.springbootconsoleapp.dao.StudentDao;
import ua.foxminded.springbootconsoleapp.dao.impl.JdbcStudentDao;
import ua.foxminded.springbootconsoleapp.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;


class JdbcStudentDaoTest {

	@Mock
	private JdbcTemplate jdbcTemplate;
	private StudentDao dao;

	private JdbcStudentDao jdbcStudentDao;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		jdbcStudentDao = new JdbcStudentDao(jdbcTemplate);
		this.dao = jdbcStudentDao;
	}

	@Test
	void testCreateStudent() {
		Student student = new Student(1, "John", "Doe", 1);
		jdbcStudentDao.create(student);

		verify(jdbcTemplate).update("INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)",
				student.getGroupId(), student.getFirstName(), student.getLastName());
	}

	@Test
	void testUpdateStudent() {
		int studentId = 1;
		Student studentToUpdate = new Student(studentId, "John", "UpdatedLastName", 2);

		when(jdbcTemplate.update(any(String.class), any(Object[].class))).thenReturn(1);

		jdbcStudentDao.update(studentToUpdate);

		verify(jdbcTemplate).update(
				"UPDATE students SET group_id = ?, first_name = ?, last_name = ? WHERE student_id = ?",
				studentToUpdate.getGroupId(), studentToUpdate.getFirstName(), studentToUpdate.getLastName(), studentId);
	}

	@Test
	void testFindAllStudents() {

		List<Student> expectedStudents = List.of(new Student(1, "John", "Doe", 1), new Student(2, "Jane", "Doe", 2));

		String expectedQuery = "SELECT * FROM students";

		when(jdbcTemplate.query(eq(expectedQuery), any(RowMapper.class))).thenReturn(expectedStudents);

		List<Student> actualStudents = dao.findAll();

		assertEquals(expectedStudents, actualStudents);
		verify(jdbcTemplate).query(eq(expectedQuery), any(RowMapper.class));
	}

}
