package ua.foxminded.springbootconsoleapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import ua.foxminded.springbootconsoleapp.dao.*;
import ua.foxminded.springbootconsoleapp.model.Group;
import ua.foxminded.springbootconsoleapp.model.Student;
import ua.foxminded.springbootconsoleapp.service.GroupServicelmpl;

@ExtendWith(MockitoExtension.class)
public class GroupServicelmplTest {
	@Mock
	private GroupRepository groupRepositoryMock;

	@Mock
	private StudentRepository studentRepositoryMock;

	@InjectMocks
	private GroupServicelmpl groupService;

	@Test
	public void testFindGroupsWithLessOrEqualsStudentCount() {
		// Arrange
		List<Group> groups = new ArrayList<>();
		groups.add(new Group(1, "Group 1"));
		groups.add(new Group(2, "Group 2"));

		List<Student> studentsGroup1 = new ArrayList<>();
		studentsGroup1.add(new Student(1, "John", "Doe", 1));
		studentsGroup1.add(new Student(2, "Jane", "Doe", 1));

		List<Student> studentsGroup2 = new ArrayList<>();

		when(groupRepositoryMock.findAll()).thenReturn(groups);
		when(studentRepositoryMock.findByGroupId(1)).thenReturn(studentsGroup1);
		when(studentRepositoryMock.findByGroupId(2)).thenReturn(studentsGroup2);

		List<Group> result = groupService.findGroupsWithLessOrEqualsStudentCount(1);

		assertEquals(1, result.size());
		assertEquals(2, result.get(0).getId());
		assertEquals("Group 2", result.get(0).getName());
	}

	@Test
	public void testFindAllGroups() {

		List<Group> groups = new ArrayList<>();
		groups.add(new Group(1, "Group 1"));
		groups.add(new Group(2, "Group 2"));

		when(groupRepositoryMock.findAll()).thenReturn(groups);

		List<Group> result = groupService.findAllGroups();

		assertEquals(2, result.size());
		assertEquals(groups.get(0), result.get(0));
		assertEquals(groups.get(1), result.get(1));
	}

	@Test
	public void testAddGroup() {
	
		Group group = new Group(1, "Group 1");

		groupService.addGroup(group);

		verify(groupRepositoryMock).create(group);
	}
}