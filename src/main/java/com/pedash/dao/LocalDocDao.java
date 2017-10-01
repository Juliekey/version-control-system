package com.pedash.dao;

import com.pedash.entities.LocalDoc;
import com.pedash.entities.RemoteDoc;
import com.pedash.entities.Status;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yuliya Pedash on 21.06.2017.
 */
@Repository
public class LocalDocDao {
    @Resource
    NamedParameterJdbcTemplate jdbcTemplate;
    private final static String DELETE_ALL_FROM_LOCAL_REPS_BY_USER = "DELETE  FROM local_reps WHERE user_id = :user_id";
    private final static String INSERT_DOC_SQL = "INSERT INTO LOCAL_REPS (REMOTE_DOC_ID, NAME, CONTENT, USER_ID) VALUES\n" +
            "  (:remote_doc_id, :name, :content, :user_id)";
    private final static String SELECT_LOCAL_DOCS_BY_USER = "SELECT * FROM local_reps WHERE user_id = :user_id AND status <> 1 /*deleted doc*/";
    private final static String INSERT_LOCAL_DOC_WITHOUT_REMOTE_SQL = "INSERT INTO LOCAL_REPS ( NAME, CONTENT, USER_ID) VALUES\n" +
            "  ( :name, :content, :user_id)";
    private final static String SELECT_BY_ID_SQL = "SELECT * FROM local_reps WHERE doc_id = :id";
    private final static String UPDATE_DOC_CONTENT = "UPDATE LOCAL_REPS " +
            "SET CONTENT = :content " +
            "WHERE DOC_ID = :doc_id ";
    private final static String UPDATE_DOC_STATUS_SQL = "UPDATE LOCAL_REPS " +
            "SET status = :status_id " +
            "WHERE doc_id = :doc_id ";
    private final static String SET_UPDATED_IF_NOT_ADDED_SQL = "UPDATE LOCAL_REPS " +
            "SET status = 9 /*doc updated*/" +
            "WHERE doc_id = :doc_id AND status <> 2 /*doc_added*/";
    private final static String SELECT_DOCS_WITH_NON_ZERO_STATUS_OF_USER = "SELECT * FROM local_reps WHERE user_id = :user_id AND status <> 0";
    private final static String UPDATE_REMOTE_DOC_FOR_LOCAL_DOC = "UPDATE LOCAL_REPS\n" +
            "SET remote_doc_id = :remote_doc_id " +
            "WHERE DOC_ID = :doc_id";
    private final static String SET_ALL_STATUSES_TO_ZERO_SQL = "UPDATE LOCAL_REPS\n" +
            "SET status = 0 " +
            "WHERE user_id = :user_id";
    private final static String DELETE_LOCAL_DOC = "DELETE  FROM local_reps WHERE doc_id = :doc_id";


    public boolean deleteAllFromLocalRepByUser(Integer userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        return jdbcTemplate.update(DELETE_ALL_FROM_LOCAL_REPS_BY_USER, params) > 0;
    }

    public void addAllRemoteDocsToLoacalRepOfUser(List<RemoteDoc> docs, Integer userId) {
        List<Map<String, Object>> batchParams = new ArrayList<>(docs.size());
        for (RemoteDoc remoteDoc : docs) {
            batchParams.add(new MapSqlParameterSource("remote_doc_id", remoteDoc.getDocId())
                    .addValue("name", remoteDoc.getName())
                    .addValue("content", remoteDoc.getContent())
                    .addValue("user_id", userId).getValues());

        }
        jdbcTemplate.batchUpdate(INSERT_DOC_SQL,
                batchParams.toArray(new Map[docs.size()]));
    }

    public List<LocalDoc> getAllDocumentsByUser(Integer userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        return jdbcTemplate.query(SELECT_LOCAL_DOCS_BY_USER, params, new LocalDocRowMapper());
    }

    public Integer createDoc(String docName, Integer userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", docName);
        params.addValue("content", " ");
        params.addValue("user_id", userId);
        jdbcTemplate.update(INSERT_LOCAL_DOC_WITHOUT_REMOTE_SQL, params, keyHolder, new String[]{"doc_id"});
        return new Integer(keyHolder.getKey().intValue());

    }

    public LocalDoc getDocById(Integer docId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", docId);
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, params, new LocalDocRowMapper());


    }

    public boolean updateContent(Integer docId, String newContent) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("content", newContent);
        params.addValue("doc_id", docId);
        return jdbcTemplate.update(UPDATE_DOC_CONTENT, params) > 0;
    }

    public boolean updateStatusForLocalDoc(Integer docId, Status status) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("status_id", status.getId());
        params.addValue("doc_id", docId);
        return jdbcTemplate.update(UPDATE_DOC_STATUS_SQL, params) > 0;
    }

    public boolean updateStatusToDocUpdatedIfDocNotAdded(Integer docId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("doc_id", docId);
        return jdbcTemplate.update(SET_UPDATED_IF_NOT_ADDED_SQL, params) > 0;
    }

    public List<LocalDoc> getDocsWithNonZeroStatusForUser(Integer userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        return jdbcTemplate.query(SELECT_DOCS_WITH_NON_ZERO_STATUS_OF_USER, params, new LocalDocRowMapper());
    }

    public boolean updateRemoteDocId(Integer docId, Integer remoteDocId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("remote_doc_id", remoteDocId);
        params.addValue("doc_id", docId);
        return jdbcTemplate.update(UPDATE_REMOTE_DOC_FOR_LOCAL_DOC, params) > 0;
    }

    public boolean delete(Integer docId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("doc_id", docId);
        return jdbcTemplate.update(DELETE_LOCAL_DOC, params) > 0;

    }

    public boolean setAllStatusesOfUserDocsToZero(Integer userId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("user_id", userId);
        return jdbcTemplate.update(SET_ALL_STATUSES_TO_ZERO_SQL, params) > 0;
    }


    private static class LocalDocRowMapper implements RowMapper<LocalDoc> {

        public LocalDoc mapRow(ResultSet resultSet, int i) throws SQLException {
            LocalDoc doc = new LocalDoc();
            doc.setDocId(resultSet.getInt("doc_id"));
            doc.setRemoteDocId(resultSet.getInt("remote_doc_id"));
            doc.setName(resultSet.getString("name"));
            doc.setContent(resultSet.getString("content"));
            doc.setStatus(Status.getStatusFromId(resultSet.getInt("status")));
            doc.setUserId(resultSet.getInt("user_id"));
            return doc;
        }
    }

}
