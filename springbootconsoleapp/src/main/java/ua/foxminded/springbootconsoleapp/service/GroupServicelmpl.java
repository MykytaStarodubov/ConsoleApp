package ua.foxminded.springbootconsoleapp.service;

import org.springframework.stereotype.Service;
import ua.foxminded.springbootconsoleapp.dao.GroupRepository;
import ua.foxminded.springbootconsoleapp.dao.*;
import ua.foxminded.springbootconsoleapp.model.Group;



import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServicelmpl implements GroupService {

    
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    public GroupServicelmpl(GroupRepository groupRepository, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    
    @Override
    public List<Group> findGroupsWithLessOrEqualsStudentCount(int count) {
        List<Group> allGroups = groupRepository.findAll();
        return allGroups.stream()
                .filter(group -> studentRepository.findByGroupId(group.getId()).size() <= count)
                .collect(Collectors.toList());
    }

    @Override
    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public void addGroup(Group group) {
    	groupRepository.create(group);
    }
}
