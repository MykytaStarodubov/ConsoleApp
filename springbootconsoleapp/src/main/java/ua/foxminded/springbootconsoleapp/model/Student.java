package ua.foxminded.springbootconsoleapp.model;

import java.util.List;

public class Student {
	private int studentId;
	private String firstName;
	private String lastName;
	private int groupId;
	private List<Course> courses;

	public Student(int studentId, String firstName, String lastName, int groupId) {
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.groupId = groupId;
	}

	public Student(int studentId, String name, int groupId) {
		this.studentId = studentId;
		String[] nameParts = name.split(" ");
		this.firstName = nameParts[0];
		if (nameParts.length > 1) {
			this.lastName = nameParts[1];
		} else {
			this.lastName = "";
		}
		this.groupId = groupId;
	}

	public Student() {

	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
}
