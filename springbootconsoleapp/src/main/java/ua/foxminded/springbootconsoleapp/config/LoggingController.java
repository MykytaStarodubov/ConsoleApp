package ua.foxminded.springbootconsoleapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.foxminded.springbootconsoleapp.dao.GroupDao;
import ua.foxminded.springbootconsoleapp.dao.StudentDao;
import ua.foxminded.springbootconsoleapp.service.CourseService;
import ua.foxminded.springbootconsoleapp.service.GroupService;
import ua.foxminded.springbootconsoleapp.service.StudentService;
import ua.foxminded.springbootconsoleapp.model.Course;
import ua.foxminded.springbootconsoleapp.model.Student;
import ua.foxminded.springbootconsoleapp.model.Group;

import java.util.List;
import java.util.Scanner;

@Component
public class LoggingController implements ApplicationRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingController.class);

    private final GroupService groupService;
    private final CourseService courseService;
    private final StudentService studentService;
    private final Scanner scanner;

    @Autowired
    public LoggingController(GroupService groupService, CourseService courseService, StudentService studentService) {
        this.groupService = groupService;
        this.courseService = courseService;
        this.studentService = studentService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(ApplicationArguments args) {
        displayMenu();
    }

    @GetMapping("/menu")
    public void displayMenu() {
        logger.info("Displaying console menu...");
        System.out.println("Choose an option:");
        System.out.println("1. Find all groups with less or equal student count");
        System.out.println("2. Find all students related to a course with the given name");
        System.out.println("3. Add a new student");
        System.out.println("4. Delete student by STUDENT_ID");
        System.out.println("5. Add a student to a course (from the list)");
        System.out.println("6. Remove the student from one of their courses");
        System.out.println("0. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                findGroupsWithLessOrEqualStudentCount();
                break;
            case 2:
                findStudentsRelatedToCourse();
                break;
            case 3:
                addNewStudent();
                break;
            case 4:
                deleteStudentById();
                break;
            case 5:
                addStudentToCourse();
                break;
            case 6:
                removeStudentFromCourse();
                break;
            case 0:
                logger.info("Exiting...");
                System.exit(0);
                break;
            default:
                logger.warn("Invalid choice entered.");
                System.out.println("Invalid choice. Please enter a valid option.");
        }
    }

    private void findGroupsWithLessOrEqualStudentCount() {
        logger.info("Finding groups with less or equal student count...");
        System.out.print("Enter the maximum number of students: ");
        int maxStudentCount = scanner.nextInt();
        List<Group> groups = groupService.findGroupsWithLessOrEqualsStudentCount(maxStudentCount);
        if (groups.isEmpty()) {
            logger.info("No groups found with less or equal student count.");
            System.out.println("No groups found with less or equal student count.");
        } else {
            logger.info("Groups found with less or equal student count:");
            for (Group group : groups) {
                System.out.println(group);
            }
        }
    }

    private void findStudentsRelatedToCourse() {
        logger.info("Finding students related to a course...");
        System.out.print("Enter the name of the course: ");
        String courseName = scanner.nextLine();
        List<Student> students = studentService.findStudentsByCourseName(courseName);
        if (students.isEmpty()) {
            logger.info("No students found related to the course '{}'.", courseName);
            System.out.println("No students found related to the course '" + courseName + "'.");
        } else {
            logger.info("Students found related to the course '{}':", courseName);
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private void addNewStudent() {
        logger.info("Adding a new student...");
        System.out.print("Enter the first name of the student: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter the last name of the student: ");
        String lastName = scanner.nextLine();
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        studentService.addStudent(student);
        logger.info("New student added: {}", student);
        System.out.println("New student added successfully.");
    }

    private void deleteStudentById() {
        logger.info("Deleting a student by STUDENT_ID...");
        System.out.print("Enter the ID of the student to delete: ");
        int studentId = scanner.nextInt();
        studentService.deleteStudentById(studentId);
        logger.info("Student with ID {} deleted successfully.", studentId);
        System.out.println("Student with ID " + studentId + " deleted successfully.");
    }

    private void addStudentToCourse() {
        logger.info("Adding a student to a course...");
        System.out.print("Enter the ID of the student: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter the ID of the course: ");
        int courseId = scanner.nextInt();
        studentService.addStudentToCourse(studentId, courseId);
        logger.info("Student with ID {} added to course with ID {}.", studentId, courseId);
        System.out.println("Student with ID " + studentId + " added to course with ID " + courseId + ".");
    }

    private void removeStudentFromCourse() {
        logger.info("Removing a student from one of their courses...");
        System.out.print("Enter the ID of the student: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter the ID of the course to remove the student from: ");
        int courseId = scanner.nextInt();
        studentService.removeStudentFromCourse(studentId, courseId);
        logger.info("Student with ID {} removed from course with ID {}.", studentId, courseId);
        System.out.println("Student with ID " + studentId + " removed from course with ID " + courseId + ".");
    }
    
}
