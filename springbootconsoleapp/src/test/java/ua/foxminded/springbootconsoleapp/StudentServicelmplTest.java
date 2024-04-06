package ua.foxminded.springbootconsoleapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.springbootconsoleapp.dao.CourseDao;
import ua.foxminded.springbootconsoleapp.dao.GroupDao;
import ua.foxminded.springbootconsoleapp.dao.StudentDao;
import ua.foxminded.springbootconsoleapp.model.Course;
import ua.foxminded.springbootconsoleapp.model.Student;
import ua.foxminded.springbootconsoleapp.service.StudentServicelmpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

/**
 * 
 */
@ExtendWith(MockitoExtension.class)
class StudentServicelmplTest {

	@Mock
	private CourseDao courseDao;

	@Mock
	private GroupDao groupDao;

	@Mock
	private StudentDao studentDao;

	@InjectMocks
	private StudentServicelmpl studentService;

	@Test
	void findStudentsByCourseName_ShouldReturnStudents_WhenCourseExists() {
		String courseName = "Math";
		int courseId = 1;
		Student student = new Student(1, "John", "Doe", 1);
		List<Student> students = Collections.singletonList(student);

		when(courseDao.findByName(courseName))
				.thenReturn(Optional.of(new Course(courseId, courseName, "Math Description")));
		when(studentDao.findByGroupId(courseId)).thenReturn(students);

		List<Student> result = studentService.findStudentsByCourseName(courseName);

		assertEquals(students, result);
	}

	@Test
	void findStudentsByCourseName_ShouldThrowException_WhenCourseDoesNotExist() {
		String courseName = "Invalid Course";

		when(courseDao.findByName(courseName)).thenReturn(Optional.empty());

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			studentService.findStudentsByCourseName(courseName);
		});

		assertEquals("Course not found", exception.getMessage());
	}

	@Test
	void addNewStudent_ShouldCallStudentDaoCreate() {
		Student student = new Student(1, "John Doe", 1);

		studentService.addNewStudent(student);

		verify(studentDao).create(student);

	}

	@Test
	void deleteStudentById_ShouldCallStudentDaoDeleteById() {
		int studentId = 1;

		studentService.deleteStudentById(studentId);

		verify(studentDao).deleteById(studentId);
	}

	@Test
	void addStudentToCourse_ShouldUpdateStudentWithCourseId() {
		int studentId = 1;
		int courseId = 2;
		Student student = new Student(studentId, "John Doe", 1);

		when(studentDao.findById(studentId)).thenReturn(Optional.of(student));

		studentService.addStudentToCourse(studentId, courseId);

		assertEquals(courseId, student.getGroupId());
		verify(studentDao).update(student);
	}

	@Test
	void removeStudentFromCourse_ShouldUpdateStudentWithMinusOneGroupId() {
		int studentId = 1;
		int courseId = 1;
		Student student = new Student(studentId, "John Doe", courseId);

		when(studentDao.findById(studentId)).thenReturn(Optional.of(student));

		studentService.removeStudentFromCourse(studentId, courseId);

		assertEquals(-1, student.getGroupId());
		verify(studentDao).update(student);
	}

	@Test
	void findAllStudents_ShouldReturnListOfStudents() {
		List<Student> students = Collections.singletonList(new Student(1, "John Doe", 1));

		when(studentDao.findAll()).thenReturn(students);

		List<Student> result = studentService.findAllStudents();

		assertEquals(students, result);
	}

	@Test
	void addStudent_ShouldCallStudentDaoCreate() {
		Student student = new Student(1, "John Doe", 1);

		studentService.addStudent(student);

		verify(studentDao).create(student);
	}

	@Test
	void updateStudent_ShouldCallStudentDaoUpdate() {
		Student student = new Student(1, "John Doe", 1);

		studentService.updateStudent(student);

		verify(studentDao).update(student);
	}
}
