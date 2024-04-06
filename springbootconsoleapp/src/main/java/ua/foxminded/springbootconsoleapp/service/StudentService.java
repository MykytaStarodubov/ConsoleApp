package ua.foxminded.springbootconsoleapp.service;
import ua.foxminded.springbootconsoleapp.model.Student;

import java.util.List;

public interface StudentService {
	
	List<Student> findStudentsByCourseName(String courseName);

	List<Student> findAllStudents();

	void addNewStudent(Student student);

	void deleteStudentById(int studentId);

	void addStudentToCourse(int studentId, int courseId);

	void removeStudentFromCourse(int studentId, int courseId);

	void addStudent(Student student);

	void updateStudent(Student student);
}
