package com.pedash.dao;

import com.pedash.entities.Group;
import com.pedash.entities.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Yuliya Pedash on 05.06.2017.
 */
@Repository
public class UserDao {
    private final static String SELECT_ALL_USERS = "SELECT * FROM users";
    private final static String DELETE_USER_SQL = "DELETE FROM users WHERE id = :id";
    private final static String SELECT_BY_NAME = "SELECT * FROM users WHERE name = :name";

    private final static String INSERT_USER_SQL = "INSERT INTO USERS  (name, PASSWORD, GROUP_ID) VALUES\n" +
            "  (:name, :password, :group_id) ";
    private final static String UPDATE_SET_TO_DEFAULT_GROUP_SQL = "UPDATE users SET GROUP_ID = 1 WHERE group_id = :group_id";
    @Resource
    NamedParameterJdbcTemplate jdbcTemplate;

    public boolean deleteById(Integer id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.update(DELETE_USER_SQL, params) > 0;
    }

    public List<User> getAll() {
        return jdbcTemplate.query(SELECT_ALL_USERS, new UserRowMapper());
    }
    public User getByName(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        return jdbcTemplate.queryForObject(SELECT_BY_NAME, params, new UserRowMapper());
    }

    public boolean insert(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", user.getName());
        params.addValue("password", user.getPassword());
        params.addValue("group_id", user.getGroupId());
        return jdbcTemplate.update(INSERT_USER_SQL, params) > 0;
    }

    public boolean setAllUsersToDefaultGroup(Integer groupId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("group_id", groupId);
        return jdbcTemplate.update(UPDATE_SET_TO_DEFAULT_GROUP_SQL, params) > 0;
    }

    private static class UserRowMapper implements RowMapper<User> {

        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            user.setEnabled(resultSet.getInt("enabled"));
            user.setGroupId(resultSet.getInt("group_id"));
            return user;
        }
    }

}
