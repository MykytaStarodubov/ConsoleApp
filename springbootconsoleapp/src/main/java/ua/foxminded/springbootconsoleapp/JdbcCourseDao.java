package ua.foxminded.springbootconsoleapp;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Repository
public class JdbcCourseDao implements CourseDao {

	public static final String FIND_BY_NAME = "SELECT * FROM courses WHERE course_name = ?";

	public final JdbcTemplate jdbcTemplate;
	public final RowMapper<Course> courseRowMapper = new CourseRowMapper();

	public JdbcCourseDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Optional<Course> findByName(String name) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_BY_NAME, courseRowMapper, courseRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Course> findById(int id) {
		String sql = "SELECT * FROM courses WHERE course_id = ?";
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, courseRowMapper, id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Course> findAll() {
		String sql = "SELECT * FROM courses";
		return jdbcTemplate.query(sql, courseRowMapper);
	}

	@Override
	public void create(Course course) {
		String sql = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
		jdbcTemplate.update(sql, course.getName(), course.getDescription());
	}

	@Override
	public void update(Course course) {
		String sql = "UPDATE courses SET course_name = ?, course_description = ? WHERE course_id = ?";
		jdbcTemplate.update(sql, course.getName(), course.getDescription(), course.getId());
	}

	@Override
	public void deleteById(int id) {
		String sql = "DELETE FROM courses WHERE course_id = ?";
		jdbcTemplate.update(sql, id);
	}

	public static class CourseRowMapper implements RowMapper<Course> {

		@Override
		public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
			Course course = new Course();
			course.setId(rs.getInt("course_id"));
			course.setName(rs.getString("course_name"));
			course.setDescription(rs.getString("course_description"));
			return course;
		}
	}

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(JdbcCourseDao.class, args);
		JdbcCourseDao dao = context.getBean(JdbcCourseDao.class);

		List<Course> allCourses = dao.findAll();
		System.out.println("All courses: " + allCourses);
		
	}
	
	@SpringBootApplication(scanBasePackages = {"ua.foxminded.springbootconsoleapp", "ua.foxminded.springbootconsoleapp.config"})
	public class SpringBootConsoleApp {
	    public static void main(String[] args) {
	        SpringApplication.run(SpringBootConsoleApp.class, args);
	    }
	}

	@Configuration
	public class JdbcConfig {

	    @Bean
	    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	        return new JdbcTemplate(dataSource);
	    }
	}

}
