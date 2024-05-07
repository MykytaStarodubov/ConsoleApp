package ua.foxminded.springbootconsoleapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.springbootconsoleapp.config.DatabaseConnection;
import ua.foxminded.springbootconsoleapp.dao.CourseRepository;
import ua.foxminded.springbootconsoleapp.dao.GroupDao;
import ua.foxminded.springbootconsoleapp.dao.StudentDao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


@Service
public class ConsoleInteractionService {
	public final StudentDao studentDao;
	public final GroupDao groupDao;
	public final CourseRepository courseRepository;

	@Autowired
	public ConsoleInteractionService(StudentDao studentDao, GroupDao groupDao,
			CourseRepository courseRepository) {
		this.studentDao = studentDao;
		this.groupDao = groupDao;
		this.courseRepository = courseRepository;
	}

	public void consoleService(Connection connection) throws SQLException {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\nConsole Menu:");
			System.out.println("1. Find all groups with less or equal students number");
			System.out.println("2. Find all students related to the course with the given name");
			System.out.println("3. Add a new student");
			System.out.println("4. Delete a student by the STUDENT_ID");
			System.out.println("5. Add a student to the course (from a list)");
			System.out.println("6. Remove the student from one of their courses");
			System.out.println("7. Exit");

			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				findGroupsWithLessOrEqualStudents(connection);
				break;
			case 2:
				findStudentsRelatedToCourse(connection);
				break;
			case 3:
				addNewStudent(connection);
				break;
			case 4:
				deleteStudentById(connection);
				break;
			case 5:
				addStudentToCourse(connection);
				break;
			case 6:
				removeStudentFromCourse(connection);
				break;
			case 7:
				System.out.println("Exiting...");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private static void findGroupsWithLessOrEqualStudents(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeQuery(
				"SELECT * FROM groups WHERE group_id IN (SELECT group_id FROM students GROUP BY group_id HAVING COUNT(*) <= ?)");
		statement.close();
	}

	private static void findStudentsRelatedToCourse(Connection connection) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter course name: ");
		String courseName = scanner.nextLine();

		PreparedStatement statement = connection.prepareStatement(
				"SELECT s.student_id, s.first_name, s.last_name FROM students s JOIN student_courses sc ON s.student_id = sc.student_id JOIN courses c ON sc.course_id = c.course_id WHERE c.course_name = ?");
		statement.setString(1, courseName);
		statement.close();
	}

	private static void addStudentToCourse(Connection connection) throws SQLException {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter student ID: ");
		int studentId = scanner.nextInt();

		System.out.print("Enter course ID: ");
		int courseId = scanner.nextInt();

		PreparedStatement statement = connection
				.prepareStatement("INSERT INTO student_courses (student_id, course_id) VALUES (?, ?)");
		statement.setInt(1, studentId);
		statement.setInt(2, courseId);
		statement.executeUpdate();
		statement.close();

		System.out.println("Student added to course successfully.");
	}

	private static void removeStudentFromCourse(Connection connection) throws SQLException {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter student ID: ");
		int studentId = scanner.nextInt();

		PreparedStatement statement = connection.prepareStatement("DELETE FROM student_courses WHERE student_id = ?");
		statement.setInt(1, studentId);
		statement.executeUpdate();
		statement.close();

		System.out.println("Student removed from course successfully.");
	}

	private static void addNewStudent(Connection connection) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter first name: ");
		String firstName = scanner.nextLine();
		System.out.print("Enter last name: ");
		String lastName = scanner.nextLine();
		System.out.print("Enter group ID: ");
		int groupId = scanner.nextInt();

		PreparedStatement insertStatement = connection.prepareStatement(
				"INSERT INTO console_app_schema.students (first_name, last_name, group_id) VALUES (?, ?, ?)");
		insertStatement.setString(1, firstName);
		insertStatement.setString(2, lastName);
		insertStatement.setInt(3, groupId);

		insertStatement.executeUpdate();
		insertStatement.close();

		System.out.println("Student added successfully.");
	}

	private static void deleteStudentById(Connection connection) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter student ID: ");
		int studentId = scanner.nextInt();

		Statement statement = connection.createStatement();
		statement.executeUpdate("DELETE FROM students WHERE student_id = ?", studentId);
		statement.executeUpdate("DELETE FROM student_courses WHERE student_id = ?", studentId);
		statement.close();
		System.out.println("Student deleted successfully.");
	}

}
