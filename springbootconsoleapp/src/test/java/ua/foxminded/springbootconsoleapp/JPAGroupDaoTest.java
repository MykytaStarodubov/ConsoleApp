package ua.foxminded.springbootconsoleapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.springbootconsoleapp.dao.GroupDao;
import ua.foxminded.springbootconsoleapp.dao.impl.JPAGroupDao;
import ua.foxminded.springbootconsoleapp.model.Group;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class JPAGroupDaoTest {

    @Autowired
    private JPAGroupDao jpaGroupDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testFindAll_ReturnsListOfGroups() {
        List<Group> expectedGroups = Arrays.asList(
                new Group(1, "Group1"),
                new Group(2, "Group2")
        );

        List<Group> actualGroups = jpaGroupDao.findAll();

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void testCreate_Group_CreatesGroup() {
        Group testGroup = new Group(1, "TestGroup");
        jpaGroupDao.create(testGroup);

        entityManager.flush();

        Group createdGroup = entityManager.find(Group.class, testGroup.getId());
        assertEquals(testGroup, createdGroup);
    }

    @Test
    void testUpdate_Group_UpdatesGroup() {
        Group testGroup = new Group(1, "UpdatedTestGroup");
        jpaGroupDao.update(testGroup);

        entityManager.flush();

        Group updatedGroup = entityManager.find(Group.class, testGroup.getId());
        assertEquals(testGroup, updatedGroup);
    }
}


