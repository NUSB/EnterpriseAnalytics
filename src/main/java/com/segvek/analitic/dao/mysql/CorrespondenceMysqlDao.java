package com.segvek.analitic.dao.mysql;

import com.segvek.analitic.dao.AcountDAO;
import com.segvek.analitic.dao.CorrespondenceDAO;
import com.segvek.analitic.dao.DocumentDAO;
import com.segvek.analitic.dao.lazy.CorrespondenceLazy;
import com.segvek.analitic.model.Acount;
import com.segvek.analitic.model.Correspondence;
import com.segvek.analitic.model.Document;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service(value = "correspondenceDAO")
public class CorrespondenceMysqlDao implements CorrespondenceDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CorrespondenceRowMapper correspondenceRowMapper;

    @Autowired
    AcountDAO acountDAO;

    @Autowired
    DocumentDAO documentDAO;

    @Override
    public List<Correspondence> getAllCorrespondences() {
        String sql = "Select * from correspondence";
        List<CorrespondenceLazy> acounts = jdbcTemplate.query(sql, correspondenceRowMapper);
        List<Correspondence> res = new ArrayList<Correspondence>();
        for (CorrespondenceLazy a : acounts) {
            a.setCorrespondenceDAO(this);
            res.add(a);
        }
        return res;
    }

    @Override
    public Correspondence getCorrespondeceById(int id) {
        String sql = "Select * FROm correspondence where id=?";
        CorrespondenceLazy a = DataAccessUtils.singleResult(jdbcTemplate.query(sql, correspondenceRowMapper, id));
        a.setCorrespondenceDAO(this);
        return a;
    }

    @Override
    public Acount getDebetAcountByCorrespondence(Correspondence correspondence) {
        String sql = "SELECT a.id FROM acount a INNER JOIN correspondence c ON a.id = c.debet WHERE c.id=?";
        return DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, (rs, i) -> 
                        acountDAO.getAcountById(rs.getInt("id"))
                , correspondence.getId()));
    }

    @Override
    public Acount getCreditAcountByCorrespondence(Correspondence correspondence) {
        String sql = "SELECT a.id FROM acount a INNER JOIN correspondence c ON a.id = c.credit WHERE c.id=?";
        return DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, (rs, i) -> 
                        acountDAO.getAcountById(rs.getInt("id"))
                , correspondence.getId()));
    }

    @Override
    public Document getDocumentByCorrespondence(Correspondence document) {
        String sql = "SELECT d.id FROM documents d INNER JOIN correspondence c ON d.id = c.documentId WHERE c.id=?";
        return DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, (rs, i) -> 
                        documentDAO.getDocumentById(rs.getInt("id"))
                , document.getId()));
    }

}

@Service
class CorrespondenceRowMapper implements RowMapper<CorrespondenceLazy> {

    @Override
    public CorrespondenceLazy mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CorrespondenceLazy(rs.getInt("id"), null, null, null, rs.getString("annotation"));
    }

}
