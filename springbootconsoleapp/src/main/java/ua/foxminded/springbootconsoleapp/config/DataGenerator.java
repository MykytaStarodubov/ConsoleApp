package ua.foxminded.springbootconsoleapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ua.foxminded.springbootconsoleapp.model.Course;
import ua.foxminded.springbootconsoleapp.model.Group;
import ua.foxminded.springbootconsoleapp.model.Student;
import ua.foxminded.springbootconsoleapp.service.CourseService;
import ua.foxminded.springbootconsoleapp.service.GroupService;
import ua.foxminded.springbootconsoleapp.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataGenerator implements ApplicationRunner {

    private final GroupService groupService;
    private final CourseService courseService;
    private final StudentService studentService;

    @Autowired
    public DataGenerator(GroupService groupService, CourseService courseService, StudentService studentService) {
        this.groupService = groupService;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (groupService.findAllGroups().isEmpty() && courseService.findAllCourses().isEmpty() && studentService.findAllStudents().isEmpty()) {
            generateData();
        }
    }

    private void generateData() {
        generateGroups();
        generateCourses();
        generateStudents();
        assignStudentsToGroups();
        assignCoursesToStudents();
    }

    private void generateGroups() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            String groupName = generateGroupName(random);
            Group group = new Group();
            group.setName(groupName);
            groupService.addGroup(group);
        }
    }

    private String generateGroupName(Random random) {
        StringBuilder groupName = new StringBuilder();
        groupName.append((char) ('A' + random.nextInt(26)));
        groupName.append((char) ('A' + random.nextInt(26)));
        groupName.append("-");
        groupName.append(random.nextInt(100));
        return groupName.toString();
    }

    private void generateCourses() {
        for (String courseName : getCourses()) {
            Course course = new Course();
            course.setName(courseName);
            course.setDescription("Description for " + courseName);
            courseService.addCourse(course);
        }
    }

    private String[] getCourses() {
        return new String[]{"Math", "Biology", "Chemistry", "Physics", "English", "History", "Geography", "Computer Science", "Art", "Music"};
    }

    private void generateStudents() {
        List<String> firstNames = getFirstNames();
        List<String> lastNames = getLastNames();
        Random random = new Random();
        for (int i = 0; i < 200; i++) {
            Student student = new Student();
            student.setFirstName(firstNames.get(random.nextInt(firstNames.size())));
            student.setLastName(lastNames.get(random.nextInt(lastNames.size())));
            studentService.addStudent(student);
        }
    }

    private List<String> getFirstNames() {
        return List.of("John", "William", "James", "David", "Robert", "Michael", "Christopher", "Matthew", "Daniel", "Anthony", "Jennifer", "Maria", "Lisa", "Jessica", "Sarah", "Amanda", "Samantha", "Emily", "Elizabeth", "Hannah");
    }

    private List<String> getLastNames() {
        return List.of("Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Garcia", "Rodriguez", "Wilson", "Martinez", "Anderson", "Taylor", "Thomas", "Hernandez", "Moore", "Martin", "Jackson", "Thompson", "White");
    }

    private void assignStudentsToGroups() {
        List<Group> groups = groupService.findAllGroups();
        List<Student> students = studentService.findAllStudents();
        Random random = new Random();
        for (Student student : students) {
            Group group = groups.get(random.nextInt(groups.size()));
            student.setGroupId(group.getId());
            studentService.updateStudent(student);
        }
    }

    private void assignCoursesToStudents() {
        List<Student> students = studentService.findAllStudents();
        List<Course> courses = courseService.findAllCourses();
        Random random = new Random();
        for (Student student : students) {
            int coursesCount = random.nextInt(3) + 1;
            List<Course> studentCourses = new ArrayList<>();
            for (int i = 0; i < coursesCount; i++) {
                studentCourses.add(courses.get(random.nextInt(courses.size())));
            }
            student.setCourses(studentCourses);
            studentService.updateStudent(student);
        }
    }
}
