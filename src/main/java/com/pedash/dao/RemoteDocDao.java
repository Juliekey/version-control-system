package com.pedash.dao;

import com.pedash.entities.LocalDoc;
import com.pedash.entities.RemoteDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Yuliya Pedash on 20.06.2017.
 */
@Repository
public class RemoteDocDao {
    private final static String SELECT_ALL_REMOTE_DOCS = "SELECT doc_id, name, content FROM remote_rep";
    private final static String INSERT_REMOTE_DOC = "INSERT INTO remote_rep( NAME, CONTENT) VALUES\n" +
            "  ( :name, :content)";
    private static final String DELETE_REMOTE_DOC = "DELETE  FROM REMOTE_REP WHERE doc_id = :doc_id";
    private final static String SELECT_BY_ID = "SELECT doc_id, name, content FROM remote_rep WHERE doc_id = :doc_id";
    private final static String UPDATE_REMOTE_DOC_CONTENT = "UPDATE remote_rep\n" +
            "SET content = :content " +
            "WHERE DOC_ID = :doc_id";
    @Resource
    NamedParameterJdbcTemplate jdbcTemplate;

    public List<RemoteDoc> getAllDocuments() {
        return jdbcTemplate.query(SELECT_ALL_REMOTE_DOCS, new RemoteDocRowMapper());
    }

    public Integer save(LocalDoc doc) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", doc.getName());
        params.addValue("content", doc.getContent());
        jdbcTemplate.update(INSERT_REMOTE_DOC, params, keyHolder, new String[]{"doc_id"});
        return new Integer(keyHolder.getKey().intValue());
    }

    public boolean delete(Integer remotedDocId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("doc_id", remotedDocId);
        return jdbcTemplate.update(DELETE_REMOTE_DOC, params) > 0;
    }

    public RemoteDoc getById(Integer remoteDocId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("doc_id", remoteDocId);
        return jdbcTemplate.queryForObject(SELECT_BY_ID, params, new RemoteDocRowMapper());

    }

    public boolean updateContent(Integer docId, String content) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("content", content);
        params.addValue("doc_id", docId);
        return jdbcTemplate.update(UPDATE_REMOTE_DOC_CONTENT, params) > 0;

    }

    private static class RemoteDocRowMapper implements RowMapper<RemoteDoc> {

        public RemoteDoc mapRow(ResultSet resultSet, int i) throws SQLException {
            RemoteDoc doc = new RemoteDoc();
            doc.setDocId(resultSet.getInt("doc_id"));
            doc.setName(resultSet.getString("name"));
            doc.setContent(resultSet.getString("content"));
            return doc;
        }
    }

}
