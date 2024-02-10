package ua.foxminded.springbootconsoleapp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcStudentDao implements StudentDao {

	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Student> studentRowMapper = new StudentRowMapper();

	public JdbcStudentDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Optional<Student> findById(int id) {
		String sql = "SELECT * FROM students WHERE student_id = ?";
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(sql, studentRowMapper, id));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Student> findAll() {
		String sql = "SELECT * FROM students";
		return jdbcTemplate.query(sql, studentRowMapper);
	}

	@Override
	public List<Student> findByGroupId(int groupId) {
		String sql = "SELECT * FROM students WHERE group_id = ?";
		return jdbcTemplate.query(sql, studentRowMapper, groupId);
	}

	@Override
	public void create(Student student) {
		String sql = "INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, student.getGroupId(), student.getFirstName(), student.getLastName());
	}

	@Override
	public void update(Student student) {
		String sql = "UPDATE students SET group_id = ?, first_name = ?, last_name = ? WHERE student_id = ?";
		jdbcTemplate.update(sql, student.getGroupId(), student.getFirstName(), student.getLastName());
	}

	@Override
	public void deleteById(int id) {
		String sql = "DELETE FROM students WHERE student_id = ?";
		jdbcTemplate.update(sql, id);
	}


	private static class StudentRowMapper implements RowMapper<Student> {

		@Override
		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
		    Student student = new Student();
		    student.setStudentId(rs.getInt("student_id"));  
		    student.setGroupId(rs.getInt("group_id"));
		    student.setFirstName(rs.getString("first_name"));
		    student.setLastName(rs.getString("last_name"));
		    return student;
		}

	}
}
