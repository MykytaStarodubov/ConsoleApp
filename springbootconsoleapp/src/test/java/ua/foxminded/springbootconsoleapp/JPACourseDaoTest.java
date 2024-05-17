package ua.foxminded.springbootconsoleapp;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.springbootconsoleapp.model.Course;
import java.util.Arrays;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import ua.foxminded.springbootconsoleapp.dao.impl.JPACourseDao;

@ExtendWith(MockitoExtension.class)
class JPACourseDaoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Course> query;

    @InjectMocks
    private JPACourseDao jpaCourseDao = new JPACourseDaoImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByName_ShouldReturnCourse_WhenCourseExists() {
        Course course = new Course(1, "Math", "Mathematics course");
        when(entityManager.createQuery(anyString(), eq(Course.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(course));

        Optional<Course> result = jpaCourseDao.findByName("Math");

        assertTrue(result.isPresent());
        assertEquals(course, result.get());
    }

    @Test
    void findAll_ShouldReturnAllCourses() {
        Course course1 = new Course(1, "Math", "Mathematics course");
        Course course2 = new Course(2, "Science", "Science course");
        List<Course> courses = Arrays.asList(course1, course2);
        when(entityManager.createQuery(anyString(), eq(Course.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(courses);

        List<Course> result = jpaCourseDao.findAll();

        assertEquals(2, result.size());
        assertEquals(courses, result);
    }

    @Test
    void create_ShouldPersistCourse() {
        Course course = new Course(1, "Math", "Mathematics course");
        doNothing().when(entityManager).persist(course);

        jpaCourseDao.create(course);

        verify(entityManager, times(1)).persist(course);
    }

    @Test
    void update_ShouldMergeCourse() {
        Course course = new Course(1, "Math", "Mathematics course");
        when(entityManager.merge(course)).thenReturn(course);

        jpaCourseDao.update(course);

        verify(entityManager, times(1)).merge(course);
    }

    @Test
    void deleteById_ShouldRemoveCourse_WhenCourseExists() {
        Course course = new Course(1, "Math", "Mathematics course");
        when(entityManager.find(Course.class, 1)).thenReturn(course);
        doNothing().when(entityManager).remove(course);

        jpaCourseDao.deleteById(1);

        verify(entityManager, times(1)).remove(course);
    }

    @Test
    void deleteById_ShouldNotRemoveCourse_WhenCourseDoesNotExist() {
        when(entityManager.find(Course.class, 1)).thenReturn(null);

        jpaCourseDao.deleteById(1);

        verify(entityManager, never()).remove(any(Course.class));
    }

    // Concrete subclass of JPACourseDao for testing
    private static class JPACourseDaoImpl extends JPACourseDao {
        @Override
        public Course getReferenceById(Integer id) {
            return null; // or provide a mock implementation
        }
    }
}