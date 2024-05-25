package ua.foxminded.springbootconsoleapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.springbootconsoleapp.dao.CourseRepository;
import ua.foxminded.springbootconsoleapp.dao.*;
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
	private CourseRepository courseRepository;

	@Mock
	private GroupRepository groupRepository;

	@Mock
	private StudentRepository studentRepository;

	@InjectMocks
	private StudentServicelmpl studentService;

	@Test
	void findStudentsByCourseName_ShouldReturnStudents_WhenCourseExists() {
		String courseName = "Math";
		int courseId = 1;
		Student student = new Student(1, "John", "Doe", 1);
		List<Student> students = Collections.singletonList(student);

		when(courseRepository.findByName(courseName))
				.thenReturn(Optional.of(new Course(courseId, courseName, "Math Description")));
		when(studentRepository.findByGroupId(courseId)).thenReturn(students);

		List<Student> result = studentService.findStudentsByCourseName(courseName);

		assertEquals(students, result);
	}

	@Test
	void addNewStudent_ShouldCallStudentDaoCreate() {
		Student student = new Student(1, "John Doe", 1);

		studentService.addNewStudent(student);

		verify(studentRepository).create(student);

	}

	@Test
	void deleteStudentById_ShouldCallStudentDaoDeleteById() {
		int studentId = 1;

		studentService.deleteStudentById(studentId);

		verify(studentRepository).deleteById(studentId);
	}

	@Test
	void addStudentToCourse_ShouldUpdateStudentWithCourseId() {
		int studentId = 1;
		int courseId = 2;
		Student student = new Student(studentId, "John Doe", 1);

		when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

		studentService.addStudentToCourse(studentId, courseId);

		assertEquals(courseId, student.getGroupId());
		verify(studentRepository).update(student);
	}

	@Test
	void removeStudentFromCourse_ShouldUpdateStudentWithMinusOneGroupId() {
		int studentId = 1;
		int courseId = 1;
		Student student = new Student(studentId, "John Doe", courseId);

		when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

		studentService.removeStudentFromCourse(studentId, courseId);

		assertEquals(-1, student.getGroupId());
		verify(studentRepository).update(student);
	}

	@Test
	void findAllStudents_ShouldReturnListOfStudents() {
		List<Student> students = Collections.singletonList(new Student(1, "John Doe", 1));

		when(studentRepository.findAll()).thenReturn(students);

		List<Student> result = studentService.findAllStudents();

		assertEquals(students, result);
	}

	@Test
	void addStudent_ShouldCallStudentDaoCreate() {
		Student student = new Student(1, "John Doe", 1);

		studentService.addStudent(student);

		verify(studentRepository).create(student);
	}

	@Test
	void updateStudent_ShouldCallStudentDaoUpdate() {
		Student student = new Student(1, "John Doe", 1);

		studentService.updateStudent(student);

		verify(studentRepository).update(student);
	}
}
