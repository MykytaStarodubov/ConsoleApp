package ua.foxminded.springbootconsoleapp.model;

public class SqlQueriesUtil {	
		
	public static final String DELETE_FROM_GROUP_ID = "DELETE FROM Group g WHERE g.id = :id";
	public static final String SELECT_FROM_GROUP = "SELECT g FROM Group g";
	public static final String SELECT_FROM_GROUP_NAME = "SELECT g FROM Group g WHERE g.name = :name";
	public static final String SELECT_FROM_STUDENT = "SELECT s FROM Student s";
	public static final String SELECT_FROM_STUDENT_GROUP_ID = "SELECT s FROM Student s WHERE s.groupId = :groupId";
	public static final String DELETE_FROM_STUDENT_STUDENT_ID = "DELETE FROM Student s WHERE s.studentId = :id";
	public static final String DELETE_FROM_COURSE_NAME = "SELECT c FROM Course c WHERE c.name = :name";
	public static final String DELETE_FROM_COURSE = "SELECT c FROM Course c";	
	
}