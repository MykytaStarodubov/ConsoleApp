package ua.foxminded.springbootconsoleapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.springbootconsoleapp.dao.impl.JPACourseDao;
import ua.foxminded.springbootconsoleapp.dao.impl.JPAStudentDao;
import ua.foxminded.springbootconsoleapp.model.Course;
import ua.foxminded.springbootconsoleapp.model.Student;
import ua.foxminded.springbootconsoleapp.dao.StudentRepository;
import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@ExtendWith(MockitoExtension.class)
class JPAStudentDaoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Student> query;

    @InjectMocks
    private JPAStudentDao jpaStudentDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_ShouldReturnStudent_WhenStudentExists() {
        Student student = new Student(1, "John", "Doe", 1);
        when(entityManager.find(Student.class, 1)).thenReturn(student);

        Optional<Student> result = jpaStudentDao.findById(1);

        assertTrue(result.isPresent());
        assertEquals(student, result.get());
    }

    @Test
    void findAll_ShouldReturnAllStudents() {
        Student student1 = new Student(1, "John", "Doe", 1);
        Student student2 = new Student(2, "Jane", "Smith", 1);
        List<Student> students = Arrays.asList(student1, student2);
        when(entityManager.createQuery(anyString(), eq(Student.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(students);

        List<Student> result = jpaStudentDao.findAll();

        assertEquals(2, result.size());
        assertEquals(students, result);
    }

    @Test
    void create_ShouldPersistStudent() {
        Student student = new Student(1, "John", "Doe", 1);
        doNothing().when(entityManager).persist(student);

        jpaStudentDao.create(student);

        verify(entityManager, times(1)).persist(student);
    }

    @Test
    void update_ShouldMergeStudent() {
        Student student = new Student(1, "John", "Doe", 1);
        when(entityManager.merge(student)).thenReturn(student);

        jpaStudentDao.update(student);

        verify(entityManager, times(1)).merge(student);
    }
}