package com.pedash.dao;

import com.pedash.entities.Revision;
import com.pedash.entities.Status;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by Yuliya Pedash on 21.06.2017.
 */
@Repository
public class RevisionHistoryDao {
    private final static String SELECT_LAST_COMMIT_TIME = "SELECT NVL(MAX(TIME), TO_DATE('01-01-1900', 'dd-mm-yyyy')) FROM revisions_history WHERE USER_ID <> :user_id";
    private final static String INSERT_REVISION_SQL = "INSERT INTO  REVISIONS_HISTORY (REMOTE_DOC_ID, STATUS, CHANGE, USER_ID)\n" +
            "    VALUES\n" +
            "      (:remote_doc_id, :status, :change, :user_id)";
    private final static String SELECT_ALL_BY_DOC_ID = "SELECT * FROM revisions_history WHERE remote_doc_id=:doc_id";

    @Resource
    NamedParameterJdbcTemplate jdbcTemplate;

    public LocalDateTime getTimeOfLastCommitNotOfThisUser(Integer userId) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("user_id", userId);
            return jdbcTemplate.queryForObject(SELECT_LAST_COMMIT_TIME, params, Timestamp.class).toLocalDateTime();
        } catch (DataAccessException e) {
            return null;
        }
    }

    public boolean save(Revision revision) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("remote_doc_id", revision.getRemoteDocId());
        params.addValue("status", revision.getStatus().getId());
        params.addValue("change", revision.getChange());
        params.addValue("user_id", revision.getUserId());
        return jdbcTemplate.update(INSERT_REVISION_SQL, params) > 0;

    }

    public List<Revision> getAllRevisionsByDocId(Integer docId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("doc_id", docId);
        return jdbcTemplate.query(SELECT_ALL_BY_DOC_ID, params, new RevisionDocRowMapper());

    }

    private static class RevisionDocRowMapper implements RowMapper<Revision> {

        public Revision mapRow(ResultSet resultSet, int i) throws SQLException {
            Revision revision = new Revision();
            revision.setId(resultSet.getInt("id"));
            revision.setRemoteDocId(resultSet.getInt("remote_doc_id"));
            revision.setStatus(Status.getStatusFromId(resultSet.getInt("status")));
            revision.setChange(resultSet.getString("change"));
            revision.setDateTime( resultSet.getTimestamp("time"));
            revision.setUserId(resultSet.getInt("user_id"));
            return revision;
        }
    }
}
