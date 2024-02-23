package ua.foxminded.springbootconsoleapp;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ua.foxminded.springbootconsoleapp.dao.GroupDao;
import ua.foxminded.springbootconsoleapp.model.Group;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertThrows;


public class JdbcGroupDaoTest {

    @Mock
    private GroupDao groupDao;

    @BeforeEach
    public void setUp() {
       
        Mockito.reset(groupDao);
    }

    @Test
    public void createGroupTest() {
    	Group group = new Group(1, "Test Group");

        groupDao.create(group);

        Mockito.verify(groupDao).create(group);
    }

    @Test
    public void findGroupByIdTest() {
        Group group = new Group(1, "Test Group");
        Mockito.when(groupDao.findById(1)).thenReturn(Optional.of(group));

        Optional<Group> foundGroup = groupDao.findById(1);

        assertThat(foundGroup).isPresent();
        assertThat(foundGroup.get()).isEqualTo(group);

       
        Mockito.verify(groupDao).findById(1);
    }

    @Test
    public void findGroupByIdNotFoundTest() {
        Mockito.when(groupDao.findById(1)).thenReturn(Optional.empty());

        Optional<Group> foundGroup = groupDao.findById(1);

        assertThat(foundGroup).isEmpty();

        Mockito.verify(groupDao).findById(1);
    }

    @Test
    public void findByNameTest() {
        Group group = new Group(1, "Test Group");
        Mockito.when(groupDao.findByName("Test Group")).thenReturn(Optional.of(group));

        Optional<Group> foundGroup = groupDao.findByName("Test Group");

        assertThat(foundGroup).isPresent();
        assertThat(foundGroup.get()).isEqualTo(group);

        Mockito.verify(groupDao).findByName("Test Group");
    }

    @Test
    public void findByNameNotFoundTest() {
        Mockito.when(groupDao.findByName("Nonexistent")).thenReturn(Optional.empty());

        Optional<Group> foundGroup = groupDao.findByName("Nonexistent");

        assertThat(foundGroup).isEmpty();

        Mockito.verify(groupDao).findByName("Nonexistent");
    }

    @Test
    public void findAllGroupsTest() {
        Group group1 = new Group(1, "Group 1");
        Group group2 = new Group(2, "Group 2");
        List<Group> groups = Arrays.asList(group1, group2);
        Mockito.when(groupDao.findAll()).thenReturn(groups);

        List<Group> foundGroups = groupDao.findAll();

        assertThat(foundGroups).containsExactlyInAnyOrder(group1, group2);

   
        Mockito.verify(groupDao).findAll();
    }

    @Test
    public void updateGroupTest() {
        Group group = new Group(1, "Test Group");
        Mockito.when(groupDao.findById(1)).thenReturn(Optional.of(group));

        group.setName("Updated Name");
        groupDao.update(group);

   
        Mockito.verify(groupDao).findById(1);
        Mockito.verify(groupDao).update(group);
    }

    @Test
    public void updateGroupNonExistentTest() {
        Group group = new Group(1, "Test Group");
        Mockito.when(groupDao.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> groupDao.update(group));

   
        Mockito.verify(groupDao).findById(1);
     
    }
}
