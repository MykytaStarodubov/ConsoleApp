package ua.foxminded.springbootconsoleapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.foxminded.springbootconsoleapp.dao.impl.JdbcStudentDao;
import ua.foxminded.springbootconsoleapp.model.Student;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import org.springframework.jdbc.core.RowMapper;

public class JdbcStudentDaoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private JdbcStudentDao studentDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCRUDOperations() {
        // Create
        Student student = new Student();
        student.setGroupId(1);
        student.setFirstName("John");
        student.setLastName("Doe");

        when(jdbcTemplate.update(anyString(), anyInt(), anyString(), anyString())).thenReturn(1);

        studentDao.create(student);

        // Read
        Student expectedStudent = new Student();
        expectedStudent.setStudentId(1);
        expectedStudent.setGroupId(1);
        expectedStudent.setFirstName("John");
        expectedStudent.setLastName("Doe");

        when(jdbcTemplate.queryForObject(anyString(), any(), studentDao.studentRowMapper)).thenReturn(expectedStudent);


        Optional<Student> retrievedStudentOptional = studentDao.findById(1);
        assertTrue(retrievedStudentOptional.isPresent());
        Student retrievedStudent = retrievedStudentOptional.get();
        assertEquals(expectedStudent.getFirstName(), retrievedStudent.getFirstName());
        assertEquals(expectedStudent.getLastName(), retrievedStudent.getLastName());

        // Update
        Student updatedStudent = new Student();
        updatedStudent.setStudentId(1);
        updatedStudent.setGroupId(2);
        updatedStudent.setFirstName("Jane");
        updatedStudent.setLastName("Smith");

        when(jdbcTemplate.update(anyString(), anyInt(), anyString(), anyString(), anyInt())).thenReturn(1);

        studentDao.update(updatedStudent);

        // Verify update
        verify(jdbcTemplate, times(1)).update(anyString(), anyInt(), anyString(), anyString(), anyInt());

        // Delete
        when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(1);

        studentDao.deleteById(1);

        // Verify delete
        verify(jdbcTemplate, times(1)).update(anyString(), anyInt());
    }
}
