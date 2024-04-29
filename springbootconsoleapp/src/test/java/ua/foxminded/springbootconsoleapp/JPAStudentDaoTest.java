package ua.foxminded.springbootconsoleapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.springbootconsoleapp.dao.impl.JPAStudentDao;
import ua.foxminded.springbootconsoleapp.model.Student;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ua.foxminded.springbootconsoleapp.dao.impl.JPAStudentDao;
import ua.foxminded.springbootconsoleapp.model.Student;

@DataJpaTest
class JPAStudentDaoTest {

    @Autowired
    private JPAStudentDao jpaStudentDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testFindAll_ReturnsListOfStudents() {
        List<Student> expectedStudents = Arrays.asList(
                new Student(1, "John", "Doe", 1),
                new Student(2, "Jane", "Doe", 2)
        );

        List<Student> actualStudents = jpaStudentDao.findAll();

        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void testCreate_Student_CreatesStudent() {
        Student testStudent = new Student(1, "John", "Doe", 1);
        jpaStudentDao.create(testStudent);

        entityManager.flush();

        Student createdStudent = entityManager.find(Student.class, testStudent.getStudentId());
        assertEquals(testStudent, createdStudent);
    }

    @Test
    void testUpdate_Student_UpdatesStudent() {
        Student testStudent = new Student(1, "John", "Doe", 1);
        jpaStudentDao.update(testStudent);

        entityManager.flush();

        Student updatedStudent = entityManager.find(Student.class, testStudent.getStudentId());
        assertEquals(testStudent, updatedStudent);
    }
}
