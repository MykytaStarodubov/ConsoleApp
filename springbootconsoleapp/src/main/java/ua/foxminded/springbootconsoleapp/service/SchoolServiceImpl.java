package ua.foxminded.springbootconsoleapp.service;

import org.springframework.stereotype.Service;
import ua.foxminded.springbootconsoleapp.dao.CourseDao;
import ua.foxminded.springbootconsoleapp.dao.GroupDao;
import ua.foxminded.springbootconsoleapp.dao.StudentDao;
import ua.foxminded.springbootconsoleapp.model.Course;
import ua.foxminded.springbootconsoleapp.model.Group;
import ua.foxminded.springbootconsoleapp.model.Student;
import ua.foxminded.springbootconsoleapp.service.SchoolService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolServiceImpl implements SchoolService {

    private final CourseDao courseDao;
    private final GroupDao groupDao;
    private final StudentDao studentDao;

    public SchoolServiceImpl(CourseDao courseDao, GroupDao groupDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.groupDao = groupDao;
        this.studentDao = studentDao;
    }

    @Override
    public List<Group> findGroupsWithLessOrEqualsStudentCount(int count) {
        List<Group> allGroups = groupDao.findAll();
        return allGroups.stream()
                .filter(group -> studentDao.findByGroupId(group.getId()).size() <= count)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByCourseName(String courseName) {
        Course course = courseDao.findByName(courseName).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        return studentDao.findByGroupId(course.getId());
    }

    @Override
    public void addNewStudent(Student student) {
        studentDao.create(student);
    }

    @Override
    public void deleteStudentById(int studentId) {
        studentDao.deleteById(studentId);
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        Student student = studentDao.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        student.setGroupId(courseId);
        studentDao.update(student);
    }

    @Override
    public void removeStudentFromCourse(int studentId, int courseId) {
        Student student = studentDao.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        if (student.getGroupId() == courseId) {
            student.setGroupId(-1);
            studentDao.update(student);
        }
    }
    
    @Override
    public List<Group> findAllGroups() {
        return groupDao.findAll();
    }

    @Override
    public List<Course> findAllCourses() {
        return courseDao.findAll();
    }

    @Override
    public List<Student> findAllStudents() {
        return studentDao.findAll();
    }

    @Override
    public void addGroup(Group group) {
        groupDao.create(group);
    }

    @Override
    public void addCourse(Course course) {
        courseDao.create(course);
    }

    @Override
    public void addStudent(Student student) {
        studentDao.create(student);
    }

    @Override
    public void updateStudent(Student student) {
        studentDao.update(student);
    }
}

