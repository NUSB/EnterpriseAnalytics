package com.segvek.analitic.dao.mysql;

import com.segvek.analitic.dao.DocumentDAO;
import com.segvek.analitic.dao.lazy.DocumentLazy;
import com.segvek.analitic.model.Document;
import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service(value = "documentDAO")
public class DocumentMysqlDao implements DocumentDAO{

    @Autowired
    JdbcTemplate jdbcTemplate;
    
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
    public void save(Document acount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteDocumentById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Document getDocumentById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Document acount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

@Service
class DocumentRowMapper implements RowMapper<DocumentLazy>{

    @Override
    public DocumentLazy mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DocumentLazy(rs.getInt("id")
                ,rs.getString("name") 
                ,rs.getString("annotation")
                ,new Point(rs.getInt("positionX")
                        , rs.getInt("positionY")) );
    }
    
}


