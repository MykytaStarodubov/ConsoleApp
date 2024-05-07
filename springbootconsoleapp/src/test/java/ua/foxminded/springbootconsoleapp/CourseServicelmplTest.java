package ua.foxminded.springbootconsoleapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.foxminded.springbootconsoleapp.dao.CourseRepository;
import ua.foxminded.springbootconsoleapp.model.Course;
import ua.foxminded.springbootconsoleapp.service.CourseServicelmpl;

@ExtendWith(MockitoExtension.class)
public class CourseServicelmplTest {

	@Mock
	private CourseRepository courseDaoMock;

	@InjectMocks
	private CourseServicelmpl courseService;

	@Test
	public void testFindAllCourses() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Math", "Mathematics course"));
		courses.add(new Course(2, "English", "English language course"));
		when(courseDaoMock.findAll()).thenReturn(courses);

		List<Course> result = courseService.findAllCourses();

		assertEquals(2, result.size());
		assertEquals(courses.get(0), result.get(0));
		assertEquals(courses.get(1), result.get(1));
	}

	@Test
	public void testAddCourse() {
		Course course = new Course(1, "Math", "Mathematics course");

		courseService.addCourse(course);

		verify(courseDaoMock).create(course);
	}
}
