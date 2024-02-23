package ua.foxminded.springbootconsoleapp.model;

public class Student {
    private int studentId;
    private String firstName;
    private String lastName;
    private int groupId;

    public Student(int studentId, String firstName, String lastName, int groupId) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
    }

    public Student() {

    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
