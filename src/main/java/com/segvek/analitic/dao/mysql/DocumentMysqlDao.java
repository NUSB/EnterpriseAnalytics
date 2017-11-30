package com.segvek.analitic.dao.mysql;

import com.segvek.analitic.dao.DocumentDAO;
import com.segvek.analitic.dao.ResponsibilityForDocumentsDAO;
import com.segvek.analitic.dao.lazy.DocumentLazy;
import com.segvek.analitic.model.Document;
import com.segvek.analitic.model.ResponsibilityForDocuments;
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
    DocumentRowMapper documentRowMapper;

    @Autowired
    ResponsibilityForDocumentsDAO responsibilityForDocumentsDAO;
    
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
        String sql="SELECT * FROM documents d WHERE d.name=?";
        DocumentLazy a = DataAccessUtils.singleResult(jdbcTemplate.query(sql, documentRowMapper, name));
        a.setDocumentDAO(this);
        return a; 
    }

    @Override
    public void save(final Document document) {
        final String INSERT_SQL = "INSERT INTO documents( name, annotation, positionX, positionY)VALUES ( ?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, document.getName());
                ps.setString(2, document.getAnnotation());
                ps.setInt(3, document.getPosition().x);
                ps.setInt(4, document.getPosition().y);
                return ps;
            }
        }, keyHolder);
        document.setId(keyHolder.getKey().intValue());
    }

    @Override
    public Document getDocumentById(int id) {
        String sql = "SELECT * FROM documents WHERE id=?";
        DocumentLazy a = DataAccessUtils.singleResult(jdbcTemplate.query(sql, documentRowMapper, id));
        a.setDocumentDAO(this);
        return a;
    }

    @Override
    public void update(Document document) {
        String sql = "UPDATE documents d SET d.name=?, d.annotation=?, "
                + "d.positionX=?, d.positionY=? WHERE d.id=?;";
        jdbcTemplate.update(sql
                , document.getName()
                , document.getAnnotation()
                , document.getPosition().x
                , document.getPosition().y
                , document.getId());
    }

    @Override
    public List<ResponsibilityForDocuments> getResponsibilityForDocument(Document document) {
        return responsibilityForDocumentsDAO.getResponsibilityForDocumentsByDocument(document);
    }

    @Override
    public void deleteDocument(Document document) {
        List<ResponsibilityForDocuments> list = responsibilityForDocumentsDAO.getResponsibilityForDocumentsByDocument(document);
        for(ResponsibilityForDocuments rfd:list){
            responsibilityForDocumentsDAO.delete(rfd);
        }
        String sql = "DELETE FROM documents WHERE id=?";
        jdbcTemplate.update(sql, document.getId());
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
