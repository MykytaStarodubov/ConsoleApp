package ua.foxminded.springbootconsoleapp.service;

import org.springframework.stereotype.Service;
import ua.foxminded.springbootconsoleapp.dao.CourseRepository;
import ua.foxminded.springbootconsoleapp.dao.GroupRepository;
import ua.foxminded.springbootconsoleapp.dao.*;
import ua.foxminded.springbootconsoleapp.model.Course;
import ua.foxminded.springbootconsoleapp.model.Student;

import java.util.List;

@Service
public class StudentServicelmpl implements StudentService {

	private final CourseRepository courseRepository;
	private final GroupRepository groupRepository;
	private final StudentRepository studentRepository;

	public StudentServicelmpl(CourseRepository courseRepository, GroupRepository groupRepository,
			StudentRepository studentRepository) {
		this.courseRepository = courseRepository;
		this.groupRepository = groupRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public List<Student> findStudentsByCourseName(String courseName) {
	    Course course = courseRepository.findByName(courseName)
	            .orElseThrow(() -> new CourseNotFoundException("Course not found: " + courseName));
	    return studentRepository.findByGroupId(course.getId());
	}

	@Override
	public void addNewStudent(Student student) {
	    studentRepository.create(student);
	}

	@Override
	public void deleteStudentById(int studentId) {
	    studentRepository.deleteById(studentId);
	}

	@Override
	public void addStudentToCourse(int studentId, int courseId) {
	    Student student = studentRepository.findById(studentId)
	            .orElseThrow(() -> new StudentNotFoundException("Student not found: " + studentId));
	    student.setGroupId(courseId);
	    studentRepository.update(student);
	}

	@Override
	public void removeStudentFromCourse(int studentId, int courseId) {
	    Student student = studentRepository.findById(studentId)
	            .orElseThrow(() -> new StudentNotFoundException("Student not found: " + studentId));
	    if (student.getGroupId() == courseId) {
	        student.setGroupId(-1);
	        studentRepository.update(student);
	    }
	}

	@Override
	public List<Student> findAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public void addStudent(Student student) {
		studentRepository.create(student);
	}

	@Override
	public void updateStudent(Student student) {
		studentRepository.update(student);
	}
	
	public class StudentNotFoundException extends RuntimeException {

	    public StudentNotFoundException(String message) {
	        super(message);
	    }
	}

	public class CourseNotFoundException extends RuntimeException {

	    public CourseNotFoundException(String message) {
	        super(message);
	    }
	}

}

