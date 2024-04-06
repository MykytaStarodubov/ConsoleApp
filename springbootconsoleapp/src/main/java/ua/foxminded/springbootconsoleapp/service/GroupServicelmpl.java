package ua.foxminded.springbootconsoleapp.service;

import org.springframework.stereotype.Service;
import ua.foxminded.springbootconsoleapp.dao.GroupDao;
import ua.foxminded.springbootconsoleapp.dao.StudentDao;
import ua.foxminded.springbootconsoleapp.model.Group;



import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServicelmpl implements GroupService {

    
    private final GroupDao groupDao;
    private final StudentDao studentDao;

    public GroupServicelmpl(GroupDao groupDao, StudentDao studentDao) {
        this.groupDao = groupDao;
        this.studentDao = studentDao;
    }

    
    @Override
    public List<Group> findGroupsWithLessOrEqualsStudentCount(int count) {
        List<Group> allGroups = groupDao.findAll();
        return allGroups.stream()
                .filter(group -> studentDao.findByGroupId(group.getId()).size() <= count)
                .collect(Collectors.toList());
    }

    @Override
    public List<Group> findAllGroups() {
        return groupDao.findAll();
    }

    @Override
    public void addGroup(Group group) {
        groupDao.create(group);
    }
}
