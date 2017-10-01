package com.pedash.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Yuliya Pedash on 21.06.2017.
 */
@Repository
public class LastUpdateDao {
    public static final String INSERT_LAST_UPDATE ="INSERT INTO LAST_UPDATE(USER_ID) VALUES\n" +
            "  (:user_id)";
    public static final String SELECT_LAST_UPDATE = "SELECT MAX(update_time) FROM last_update WHERE user_id = :user_id";
    @Resource
    NamedParameterJdbcTemplate jdbcTemplate;

    public LocalDateTime getLastUpdate(Integer userId){
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("user_id", userId);
            return jdbcTemplate.queryForObject(SELECT_LAST_UPDATE, params, Timestamp.class).toLocalDateTime();
        }
        catch (EmptyResultDataAccessException e) {
        return null;
    }

    }
    public boolean insertLastUpdateOfUser(Integer userId){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id",userId);
        return  jdbcTemplate.update(INSERT_LAST_UPDATE, params) > 0;
    }
}
