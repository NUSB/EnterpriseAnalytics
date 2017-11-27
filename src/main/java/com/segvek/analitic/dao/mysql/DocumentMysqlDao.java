package com.segvek.analitic.dao.mysql;

import com.segvek.analitic.dao.BisnesRoleDAO;
import com.segvek.analitic.dao.DocumentDAO;
import com.segvek.analitic.dao.lazy.DocumentLazy;
import com.segvek.analitic.model.BisnesRole;
import com.segvek.analitic.model.Document;
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

@Service(value = "documentDAO")
public class DocumentMysqlDao implements DocumentDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    BisnesRoleDAO bisnesRoleDAO;

    @Autowired
    DocumentRowMapper documentRowMapper;

    @Override
    public List<Document> getAllDocument() {
        String sql = "SELECT * FROM documents;";
        List<DocumentLazy> acounts = jdbcTemplate.query(sql, documentRowMapper);
        List<Document> res = new ArrayList<Document>();
        for (DocumentLazy a : acounts) {
            a.setDocumentDAO(this);
            res.add(a);
        }
        return res;
    }

    @Override
    public Document getDocumentByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(final Document document) {
        final String INSERT_SQL = "INSERT INTO documents( name, annotation, positionX, positionY)VALUES ( ?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL,Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, document.getName());
                ps.setString(2, document.getAnnotation());
                ps.setInt(3, document.getPosition().x);
                ps.setInt(4, document.getPosition().y);
                return ps;
            }
        },keyHolder);
        document.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void deleteDocumentById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Document getDocumentById(int id) {
        String sql = "SELECT * FROM documents WHERE id=?";
        DocumentLazy a = DataAccessUtils.singleResult(jdbcTemplate.query(sql, documentRowMapper, id));
        a.setDocumentDAO(this);
        return a;
    }

    @Override
    public void update(Document acount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<BisnesRole, String> getBisnesRolesByDocument(Document document) {
        class MapElement {

            BisnesRole br;
            String annotation;

            public MapElement(BisnesRole br, String annotation) {
                this.br = br;
                this.annotation = annotation;
            }
        }
        class MapElementRowMapper implements RowMapper<MapElement> {

            @Override
            public MapElement mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new MapElement(
                        bisnesRoleDAO.getBisnesRoleById(rs.getInt("idBisnesRole")),
                        rs.getString("annotation"));
            }
        }
        String sql = "SELECT * FROM `documents-hash-bisnes_roles` WHERE idDocument=?";

        List<MapElement> res = jdbcTemplate.query(sql, new MapElementRowMapper(), document.getId());
        Map<BisnesRole, String> map = new HashMap<BisnesRole, String>(res.size());
        for (MapElement el : res) {
            map.put(el.br, el.annotation);
        }

        return map;
    }

}

@Service
class DocumentRowMapper implements RowMapper<DocumentLazy> {

    @Override
    public DocumentLazy mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DocumentLazy(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("annotation"),
                new Point(rs.getInt("positionX"),
                        rs.getInt("positionY")));
    }

}
