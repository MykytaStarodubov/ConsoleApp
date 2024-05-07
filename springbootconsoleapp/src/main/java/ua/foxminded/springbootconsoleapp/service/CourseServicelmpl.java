package ua.foxminded.springbootconsoleapp.service;

import org.springframework.stereotype.Service;
import ua.foxminded.springbootconsoleapp.dao.CourseRepository;

import ua.foxminded.springbootconsoleapp.model.Course;

import java.util.List;

@Service
public class CourseServicelmpl implements CourseService {

	private final CourseRepository courseRepository;

	public CourseServicelmpl(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;

	}

	@Override
	public List<Course> findAllCourses() {
		return courseRepository.findAll();
	}

	@Override
	public void addCourse(Course course) {
		courseRepository.create(course);
	}
}
