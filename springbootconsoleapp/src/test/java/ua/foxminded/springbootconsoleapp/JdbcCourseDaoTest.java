package ua.foxminded.springbootconsoleapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest

public class JdbcCourseDaoTest {

	@Autowired
	private JdbcCourseDao courseDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private JdbcCourseDao CourseRowMapper;

	@Container
	private static PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.6"))
			.withDatabaseName("test").withUsername("root").withPassword("test");

	
	@BeforeEach
	void setUp() {
		jdbcTemplate.execute("TRUNCATE TABLE courses");
	}

	@Test
	void findByIdShouldReturnCourseWhenCourseExists() {
		Course course = new Course(1, "Java Programming", "Learn Java fundamentals");
		courseDao.create(course);

		Optional<Course> foundCourse = courseDao.findById(1);
		assertTrue(foundCourse.isPresent());
		assertEquals(course, foundCourse.get());
	}

	@Test
	public void findByIdShouldReturnEmptyOptionalWhenCourseDoesNotExist() {
		Optional<Course> foundCourse = courseDao.findById(1);
		Course emptyCourse = new Course();
		Course actualCourse = foundCourse.orElseGet(() -> emptyCourse);
		assertEquals(emptyCourse, actualCourse);
	}

	@Test
	void findAllShouldReturnAllCourses() {
		Course course1 = new Course(1, "Java Programming", "Learn Java fundamentals");
		Course course2 = new Course(2, "Database Design", "Learn database concepts");
		courseDao.create(course1);
		courseDao.create(course2);

		List<Course> courses = courseDao.findAll();
		 assertEquals(Arrays.asList(course1, course2), courses);
	}

	@Test
	void createShouldAddCourseToDatabase() {
		Course course = new Course(1, "Java Programming", "Learn Java fundamentals");
		courseDao.create(course);

		List<Course> courses = jdbcTemplate.query("SELECT * FROM courses", CourseRowMapper.courseRowMapper);
		assertEquals(Arrays.asList(course), courses);
	}

	@Test
	void updateShouldModifyExistingCourse() {
		Course course = new Course(1, "Java Programming", "Learn Java fundamentals");
		courseDao.create(course);

		Course updatedCourse = new Course(1, "Python Programming", "Learn Python basics");
		courseDao.update(updatedCourse);

		Optional<Course> foundCourse = courseDao.findById(1);
		assertTrue(foundCourse.isPresent());
		assertEquals(updatedCourse, foundCourse.get());
	}
}