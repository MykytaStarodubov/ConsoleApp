package ua.foxminded.springbootconsoleapp.service;

import org.springframework.stereotype.Service;
import ua.foxminded.springbootconsoleapp.dao.CourseDao;

import ua.foxminded.springbootconsoleapp.model.Course;

import java.util.List;

@Service
public class CourseServicelmpl implements CourseService {

	private final CourseDao courseDao;

	public CourseServicelmpl(CourseDao courseDao) {
		this.courseDao = courseDao;

	}

	@Override
	public List<Course> findAllCourses() {
		return courseDao.findAll();
	}

	@Override
	public void addCourse(Course course) {
		courseDao.create(course);
	}
}
