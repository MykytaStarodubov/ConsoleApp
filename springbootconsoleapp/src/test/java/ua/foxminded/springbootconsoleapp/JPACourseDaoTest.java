package ua.foxminded.springbootconsoleapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.springbootconsoleapp.dao.CourseDao;
import ua.foxminded.springbootconsoleapp.dao.impl.JPACourseDao;
import ua.foxminded.springbootconsoleapp.model.Course;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ua.foxminded.springbootconsoleapp.dao.impl.JPACourseDao;
import ua.foxminded.springbootconsoleapp.dao.impl.JPAGroupDao;
import ua.foxminded.springbootconsoleapp.dao.impl.JPAStudentDao;
import ua.foxminded.springbootconsoleapp.model.Course;
import ua.foxminded.springbootconsoleapp.model.Group;
import ua.foxminded.springbootconsoleapp.model.Student;

@DataJpaTest
class JPACourseDaoTest {

    @Autowired
    private JPACourseDao jpaCourseDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testFindAll_ReturnsListOfCourses() {
        List<Course> expectedCourses = Arrays.asList(
                new Course(1, "Course1", "Description1"),
                new Course(2, "Course2", "Description2")
        );

        List<Course> actualCourses = jpaCourseDao.findAll();

        assertEquals(expectedCourses, actualCourses);
    }

    @Test
    void testCreate_Course_CreatesCourse() {
        Course testCourse = new Course(1, "TestCourse", "Test description");
        jpaCourseDao.create(testCourse);

        entityManager.flush();

        Course createdCourse = entityManager.find(Course.class, testCourse.getId());
        assertEquals(testCourse, createdCourse);
    }

    @Test
    void testUpdate_Course_UpdatesCourse() {
        Course testCourse = new Course(1, "TestCourse", "Updated description");
        jpaCourseDao.update(testCourse);

        entityManager.flush();

        Course updatedCourse = entityManager.find(Course.class, testCourse.getId());
        assertEquals(testCourse, updatedCourse);
    }

}

