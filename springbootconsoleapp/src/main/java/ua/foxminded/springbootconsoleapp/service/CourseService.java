package ua.foxminded.springbootconsoleapp.service;

import java.util.List;

import ua.foxminded.springbootconsoleapp.model.Course;

public interface CourseService {

	List<Course> findAllCourses();

	void addCourse(Course course);
}
