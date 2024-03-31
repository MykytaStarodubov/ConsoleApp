package ua.foxminded.springbootconsoleapp.service;

import ua.foxminded.springbootconsoleapp.model.Course;
import ua.foxminded.springbootconsoleapp.model.Group;
import ua.foxminded.springbootconsoleapp.model.Student;

import java.util.List;

public interface SchoolService {

	List<Group> findGroupsWithLessOrEqualsStudentCount(int count);

	List<Student> findStudentsByCourseName(String courseName);

	List<Group> findAllGroups();

	List<Course> findAllCourses();

	List<Student> findAllStudents();

	void addNewStudent(Student student);

	void deleteStudentById(int studentId);

	void addStudentToCourse(int studentId, int courseId);

	void removeStudentFromCourse(int studentId, int courseId);

	void addGroup(Group group);

	void addCourse(Course course);

	void addStudent(Student student);

	void updateStudent(Student student);
}
