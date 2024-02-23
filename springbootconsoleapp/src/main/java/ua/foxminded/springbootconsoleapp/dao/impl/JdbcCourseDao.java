package ua.foxminded.springbootconsoleapp.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.foxminded.springbootconsoleapp.dao.CourseDao;
import ua.foxminded.springbootconsoleapp.model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

}
