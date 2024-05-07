package ua.foxminded.springbootconsoleapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.foxminded.springbootconsoleapp.dao.CourseRepository;
import ua.foxminded.springbootconsoleapp.model.Course;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = {"spring.config.location=classpath:/application-test.yaml"})
public class JPACourseDaoTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void testFindAll_ReturnsListOfCourses() {
        List<Course> expectedCourses = List.of(
                new Course(1, "Course1", "Description1"),
                new Course(2, "Course2", "Description2")
        );

        courseRepository.saveAll(expectedCourses);

        List<Course> actualCourses = courseRepository.findAll();

        assertEquals(expectedCourses.size(), actualCourses.size());
        expectedCourses.forEach(expectedCourse -> assertContainsCourse(actualCourses, expectedCourse));
    }

    @Test
    void testCreate_Course_CreatesCourse() {
        Course testCourse = new Course(1, "TestCourse", "Test description");

        courseRepository.save(testCourse);

        Course createdCourse = courseRepository.findById(testCourse.getId()).orElse(null);

        assertNotNull(createdCourse);
        assertEquals(testCourse, createdCourse);
    }

    @Test
    void testUpdate_Course_UpdatesCourse() {
        Course testCourse = new Course(1, "TestCourse", "Updated description");

        courseRepository.save(testCourse);

        testCourse.setDescription("New updated description");

        courseRepository.save(testCourse);

        Course updatedCourse = courseRepository.findById(testCourse.getId()).orElse(null);

        assertNotNull(updatedCourse);
        assertEquals(testCourse, updatedCourse);
    }

    private void assertContainsCourse(List<Course> courses, Course courseToFind) {
        for (Course course : courses) {
            if (course.getId() == courseToFind.getId()) {
                assertEquals(courseToFind, course);
                return;
            }
        }
        throw new AssertionError("Course not found: " + courseToFind);
    }
}
