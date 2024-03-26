package ua.foxminded.springbootconsoleapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import ua.foxminded.springbootconsoleapp.dao.impl.JdbcCourseDao;
import ua.foxminded.springbootconsoleapp.model.Course;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.springbootconsoleapp.dao.StudentDao;
import ua.foxminded.springbootconsoleapp.dao.impl.JdbcStudentDao;
import ua.foxminded.springbootconsoleapp.model.Student;


import org.mockito.Mock;


import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.foxminded.springbootconsoleapp.dao.impl.JdbcCourseDao;
import ua.foxminded.springbootconsoleapp.model.Course;

@ExtendWith(MockitoExtension.class)
class JdbcCourseDaoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private JdbcCourseDao jdbcCourseDao;

    private Course testCourse;

    @BeforeEach
    void setUp() {
        testCourse = new Course(1, "TestCourse", "Test description");
    }
    
    @Test
    void testFindAll_ReturnsListOfCourses() {
        
        List<Course> expectedCourses = Arrays.asList(
                new Course(1, "Course1", "Description1"),
                new Course(2, "Course2", "Description2")
        );

       
        String expectedQuery = "SELECT * FROM courses";

        
        when(jdbcTemplate.query(eq(expectedQuery), any(JdbcCourseDao.CourseRowMapper.class))).thenReturn(expectedCourses);

     
        List<Course> actualCourses = jdbcCourseDao.findAll();

        
        assertEquals(expectedCourses.size(), actualCourses.size());
        assertTrue(actualCourses.containsAll(expectedCourses));

       
        verify(jdbcTemplate).query(eq(expectedQuery), any(JdbcCourseDao.CourseRowMapper.class));
    }

    @Test
    void testCreate_Course_CreatesCourse() {
        jdbcCourseDao.create(testCourse);
        verify(jdbcTemplate).update(anyString(), eq(testCourse.getName()), eq(testCourse.getDescription()));
    }

    @Test
    void testUpdate_Course_UpdatesCourse() {
        jdbcCourseDao.update(testCourse);
        verify(jdbcTemplate).update(anyString(), eq(testCourse.getName()), eq(testCourse.getDescription()), eq(testCourse.getId()));
    }
    

}
