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
import java.util.*;

public class ConsoleApp {
	public static void main(String[] args) throws Exception {

		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/console_app",
				"postgres", "1234")) {

			System.out.println("Java JDBC PostgreSQL Example");

			String students = readFile("C://students.sql");
			String courses = readFile("C://courses.sql");
			String groups = readFile("C://groups.sql");

			connection.createStatement().execute(students);
			connection.createStatement().execute(courses);
			connection.createStatement().execute(groups);

			connection.close();

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

	private static final String[] firstNames = { "John", "William", "James", "David", "Robert", "Michael",
			"Christopher", "Matthew", "Daniel", "Anthony" };

	private static final String[] lastNames = { "Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis",
			"Garcia", "Rodriguez", "Wilson" };

	public static void generateData(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();

		for (int i = 0; i < 10; i++) {
			statement.executeUpdate("INSERT INTO groups (group_name) VALUES ('" + generateGroupName() + "')");
		}

		for (int i = 0; i < 200; i++) {
			statement.executeUpdate("INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (" + i
					+ ", " + generateGroupId() + ", '" + generateFirstName() + "', '" + generateLastName() + "')");
		}
	}

	private static String generateGroupName() {

		return "SampleGroupName";
	}

	private static int generateGroupId() {

		return 1;
	}

	private static String generateFirstName() {

		return "SampleFirstName";
	}

	private static String generateLastName() {

		return "SampleLastName";
	}
}
