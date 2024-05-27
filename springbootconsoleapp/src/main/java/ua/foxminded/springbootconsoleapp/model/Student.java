package ua.foxminded.springbootconsoleapp.model;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor 
@AllArgsConstructor 
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
    @JoinTable(name = "student_courses", 
               joinColumns = @JoinColumn(name = "student_id"), 
               inverseJoinColumns = @JoinColumn(name = "course_id"))
    
    private List<Course> courses;

    public Student(int studentId, String firstName, String lastName, int groupId) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
    }

    public Student(int studentId, String name, int groupId) {
        this.studentId = studentId;
        this.groupId = groupId;
        parseName(name);
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
}
