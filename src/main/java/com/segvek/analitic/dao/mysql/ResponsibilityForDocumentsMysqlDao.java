/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segvek.analitic.dao.mysql;

import com.segvek.analitic.dao.BisnesRoleDAO;
import com.segvek.analitic.dao.DocumentDAO;
import com.segvek.analitic.dao.ResponsibilityForDocumentsDAO;
import com.segvek.analitic.dao.lazy.ResponsibilityForDocumentsLazy;
import com.segvek.analitic.model.BisnesRole;
import com.segvek.analitic.model.Document;
import com.segvek.analitic.model.ResponsibilityForDocuments;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service("responsibilityForDocumentsDAO")
public class ResponsibilityForDocumentsMysqlDao implements ResponsibilityForDocumentsDAO{

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    DocumentDAO documentDAO;
    
    @Autowired
    BisnesRoleDAO bisnesRoleDAO;
    
    @Autowired
    ResponsibilityForDocumentsRowMapper responsibilityForDocumentsRowMapper;
    
    @Override
    public List<ResponsibilityForDocuments> getResponsibilityForDocumentsByDocument(Document document) {
        String sql = "SELECT * FROM `documents-hash-bisnes_roles` WHERE idDocument=?";
        List<ResponsibilityForDocumentsLazy> list = jdbcTemplate.query(sql, responsibilityForDocumentsRowMapper,document.getId());
        List<ResponsibilityForDocuments> res = new ArrayList<ResponsibilityForDocuments>();
        for(ResponsibilityForDocumentsLazy r:list){
            r.setResponsibilityForDocumentsDAO(this);
            r.setDocument(document);
            res.add(r);
        }
        return res;
    }

    @Override
    public Document getDocumentByResponsibilityForDocuments(ResponsibilityForDocuments rfd) {
        String sql="SELECT d.id FROM documents d INNER JOIN `documents-hash-bisnes_roles` dr ON d.id = dr.idDocument WHERE dr.id=?";
        return jdbcTemplate.queryForObject(sql,new RowMapper<Document>() {
            @Override
            public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
                return documentDAO.getDocumentById(rs.getInt("id"));
            }
        },rfd.getId());
    }

    @Override
    public BisnesRole getBisnesRoleByResponsibilityForDocuments(ResponsibilityForDocuments rfd) {
        String sql="SELECT br.id FROM bisnes_roles br INNER JOIN `documents-hash-bisnes_roles` dr ON br.id = dr.idBisnesRole WHERE dr.id=?";
        return jdbcTemplate.queryForObject(sql,new RowMapper<BisnesRole>() {
            @Override
            public BisnesRole mapRow(ResultSet rs, int rowNum) throws SQLException {
                return bisnesRoleDAO.getBisnesRoleById(rs.getInt("id"));
            }
        },rfd.getId());
    }
    
}

@Service
class ResponsibilityForDocumentsRowMapper implements RowMapper<ResponsibilityForDocumentsLazy>{

    @Override
    public ResponsibilityForDocumentsLazy mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ResponsibilityForDocumentsLazy(rs.getInt("id"), null, null, rs.getString("annotation"));
    }
    
}