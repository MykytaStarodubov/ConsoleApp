package ua.foxminded.springbootconsoleapp.model;

import java.util.List;
import jakarta.persistence.*;


@Entity
@Table(name = "students")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private int studentId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "group_id")
	private int groupId;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "student_courses", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> courses;

	public Student(int studentId, String firstName, String lastName, int groupId) {
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.groupId = groupId;
	}

	public Student(int studentId, String name, int groupId) {
		this.studentId = studentId;
		parseName(name);
		this.groupId = groupId;
	}
	
	private void parseName(String name) {
		  String[] nameParts = name.split(" ");
		  this.firstName = nameParts[0];
		  if (nameParts.length > 1) {
		    this.lastName = nameParts[1];
		  } else {
		    this.lastName = "";
		  }
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
