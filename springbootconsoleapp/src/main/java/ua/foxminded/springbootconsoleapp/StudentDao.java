package ua.foxminded.springbootconsoleapp;
import java.util.Optional;
import java.util.List;

public interface StudentDao {
	Optional<Student> findById(int id);

	List<Student> findAll();

	List<Student> findByGroupId(int groupId);

	void create(Student student);

	void update(Student student);

	void deleteById(int id);

}