package ua.foxminded.springbootconsoleapp.service;

import java.util.List;

import ua.foxminded.springbootconsoleapp.model.Group;

public interface GroupService {
	List<Group> findGroupsWithLessOrEqualsStudentCount(int count);

	List<Group> findAllGroups();

	void addGroup(Group group);
}
