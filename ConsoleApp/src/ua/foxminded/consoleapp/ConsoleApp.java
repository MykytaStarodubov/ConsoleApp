package ua.foxminded.consoleapp;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.FileReader;

public class ConsoleApp {

	private static final String[] firstNames = { "John", "William", "James", "David", "Robert", "Michael",
			"Christopher", "Matthew", "Daniel", "Anthony", "Jennifer", "Maria", "Lisa", "Jessica", "Sarah", "Amanda",
			"Samantha", "Emily", "Elizabeth", "Hannah" };

	private static final String[] lastNames = { "Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis",
			"Garcia", "Rodriguez", "Wilson", "Martinez", "Anderson", "Taylor", "Thomas", "Hernandez", "Moore", "Martin",
			"Jackson", "Thompson", "White" };

	private static final String[] courses = { "Math", "Biology", "Chemistry", "Physics", "English", "History",
			"Geography", "Computer Science", "Art", "Music" };

	public static void main(String[] args) throws Exception {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/console_app",
				"postgres", "1234")) {

			System.out.println("Java JDBC PostgreSQL Example");

			String students = readFile("C://students.sql");
			String courses = readFile("C://courses.sql");
			String groups = readFile("C://groups.sql");

			connection.createStatement().execute("CREATE TABLE IF NOT EXISTS students");
			connection.createStatement().execute("CREATE TABLE IF NOT EXISTScourses");
			connection.createStatement().execute("CREATE TABLE IF NOT EXISTS groups");

			generateData(connection);

			ConsoleMenu(connection);

		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
	}

	private static String readFile(String fileName) throws Exception {

		File file = new File(fileName);
		FileInputStream fileInputStream = new FileInputStream(file);

		byte[] bytes = new byte[(int) file.length()];
		fileInputStream.read(bytes);

		fileInputStream.close();

		return new String(bytes);
	}

	private static String generateGroupName() {
		Random random = new Random();
		StringBuilder name = new StringBuilder();

		for (int i = 0; i < 2; i++) {
			char letter = (char) ('A' + random.nextInt(26));
			name.append(letter);
		}

		name.append("-");

		for (int i = 0; i < 2; i++) {
			int digit = random.nextInt(10);
			name.append(digit);
		}

		return name.toString();
	}

	public static void generateData(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();

		for (int i = 0; i < 10; i++) {
			statement.executeUpdate("INSERT INTO groups (group_name) VALUES ('" + generateGroupName() + "')");
		}

		for (int i = 0; i < 200; i++) {
			List<Student> students = new ArrayList<>();
			generateStudents(students);
			for (Student student : students) {
				int groupId = generateGroupId();
				statement.executeUpdate("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES ("
						+ i + ", " + groupId + ", '" + student.fullName + "')");
			}

			for (int studentId = 1; studentId <= 200; studentId++) {
				int numCourses = 1 + new Random().nextInt(3);
				for (int j = 0; j < numCourses; j++) {
					int courseId = new Random().nextInt(courses.length) + 1;
					statement.executeUpdate("INSERT INTO student_courses (student_id, course_id) VALUES (" + studentId
							+ ", " + courseId + ")");
				}
			}
		}
	}

	private static int generateGroupId() {

		return new Random().nextInt(10) + 1;
	}

	private static class Student {
		private String fullName;

		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
	}

	private static void generateStudents(List<Student> students) {
		List<String> usedFullNames = new ArrayList<>();

		for (int i = 0; i < 200; i++) {
			String fullName = generateFullName(usedFullNames);
			Student student = new Student();
			student.setFullName(fullName);
			students.add(student);
		}
	}

	private static String generateFullName(List<String> usedFullNames) {
		Random random = new Random();
		while (true) {
			int firstNameIndex = random.nextInt(firstNames.length);
			int lastNameIndex = random.nextInt(lastNames.length);
			String fullName = firstNames[firstNameIndex] + " " + lastNames[lastNameIndex];

			if (!usedFullNames.contains(fullName)) {
				usedFullNames.add(fullName);
				return fullName;
			}
		}
	}

	private static void ConsoleMenu(Connection connection) throws SQLException {
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
		ResultSet resultSet = statement.executeQuery(
				"SELECT * FROM groups WHERE group_id IN (SELECT group_id FROM students GROUP BY group_id HAVING COUNT(*) <= ?)");
	}

	private static void findStudentsRelatedToCourse(Connection connection) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter course name: ");
		String courseName = scanner.nextLine();

		PreparedStatement statement = connection.prepareStatement(
				"SELECT s.student_id, s.first_name, s.last_name FROM students s JOIN student_courses sc ON s.student_id = sc.student_id JOIN courses c ON sc.course_id = c.course_id WHERE c.course_name = ?");
		statement.setString(1, courseName);

		ResultSet resultSet = statement.executeQuery();

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

		System.out.println("Student added to course successfully.");
	}

	private static void removeStudentFromCourse(Connection connection) throws SQLException {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter student ID: ");
		int studentId = scanner.nextInt();

		PreparedStatement statement = connection.prepareStatement("DELETE FROM student_courses WHERE student_id = ?");
		statement.setInt(1, studentId);
		statement.executeUpdate();

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

		PreparedStatement insertStatement = connection
				.prepareStatement("INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)");
		insertStatement.setString(1, firstName);
		insertStatement.setString(2, lastName);
		insertStatement.setInt(3, groupId);

		insertStatement.executeUpdate();

		System.out.println("Student added successfully.");
	}

	private static void deleteStudentById(Connection connection) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter student ID: ");
		int studentId = scanner.nextInt();

		Statement statement = connection.createStatement();
		statement.executeUpdate("DELETE FROM students WHERE student_id = ?", studentId);
		statement.executeUpdate("DELETE FROM student_courses WHERE student_id = ?", studentId);
		System.out.println("Student deleted successfully.");
	}

}