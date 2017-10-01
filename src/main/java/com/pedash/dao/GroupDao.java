package com.pedash.dao;

import com.pedash.entities.Group;
import com.pedash.entities.Role;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Yuliya Pedash on 05.06.2017.
 */
@Repository
public class GroupDao {
    private final static String INSERT_GROUP_SQL = "INSERT INTO GROUPS (NAME, ROLE_ID)\n" +
            "    VALUES (:name, :role_id)";
    private final static String SELECT_ALL_GROUPS_SQL = "SELECT * FROM groups";
    private final static String SELECT_BY_ID_SQL = "SELECT * FROM groups WHERE id = :id ";
    private final static String DELETE_BY_ID_SQL = "DELETE FROM groups WHERE id = :id ";

    @Resource
    NamedParameterJdbcTemplate jdbcTemplate;

    public boolean deleteById(Integer id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.update(DELETE_BY_ID_SQL, params) > 0;
    }

    public boolean insert(Group group) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", group.getName());
        params.addValue("role_id", group.getRole().getId());
        return jdbcTemplate.update(INSERT_GROUP_SQL, params) > 0;
    }

    public List<Group> getAll() {
        return jdbcTemplate.query(SELECT_ALL_GROUPS_SQL, new GroupRowMapper());
    }

    public Group getById(Integer id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return (Group) jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, params, new GroupRowMapper());
    }

    private static class GroupRowMapper implements RowMapper<Group> {

        public Group mapRow(ResultSet resultSet, int i) throws SQLException {
            Group group = new Group();
            group.setId(resultSet.getInt("id"));
            group.setName(resultSet.getString("name"));
            group.setRole(Role.getRoleFromId(resultSet.getInt("role_id")));
            return group;
        }
    }
}
