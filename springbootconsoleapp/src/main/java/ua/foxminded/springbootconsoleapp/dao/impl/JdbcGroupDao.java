package ua.foxminded.springbootconsoleapp.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.foxminded.springbootconsoleapp.dao.GroupDao;
import ua.foxminded.springbootconsoleapp.model.Group;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcGroupDao implements GroupDao {

    public static final String FIND_BY_NAME = "SELECT * FROM groups WHERE group_name = ?";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Group> groupRowMapper = new GroupRowMapper();

    public JdbcGroupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Group> findById(int id) {
        String sql = "SELECT * FROM groups WHERE group_id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(FIND_BY_NAME, groupRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Group> findAll() {
        String sql = "SELECT * FROM groups";
        return jdbcTemplate.query(sql, groupRowMapper);
    }

    @Override
    public void create(Group group) {
        String sql = "INSERT INTO groups (group_name) VALUES (?)";
        jdbcTemplate.update(sql, group.getName());
    }

    @Override
    public void update(Group group) {
        String sql = "UPDATE groups SET group_name = ? WHERE group_id = ?";
        jdbcTemplate.update(sql, group.getName(), group.getId());
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM groups WHERE group_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Group> findByName(String name) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(FIND_BY_NAME, groupRowMapper, name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static class GroupRowMapper implements RowMapper<Group> {

        @Override
        public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
            Group group = new Group();
            group.setId(rs.getInt("group_id"));
            group.setName(rs.getString("group_name"));
            return group;
        }
    }

}