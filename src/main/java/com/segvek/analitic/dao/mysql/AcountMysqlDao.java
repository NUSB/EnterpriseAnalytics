package com.segvek.analitic.dao.mysql;

import com.segvek.analitic.dao.AcountDAO;
import com.segvek.analitic.dao.lazy.AcountLazy;
import com.segvek.analitic.model.Acount;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service(value = "acountDAO")
public class AcountMysqlDao implements AcountDAO{
    
    @Autowired
    JdbcTemplate template;
    
    @Autowired
    AcountRowMapper acountRowMapper;
    
    @Override
    public List<Acount> getAllAcount() {
        String sql="Select * from acount ORDER BY code";
        List<AcountLazy> acounts = template.query(sql, acountRowMapper);
        List<Acount> res = new ArrayList<Acount>();
        for(AcountLazy a:acounts){
            a.setAcountDao(this);
            res.add(a);
        }
        return res;
    }   

    @Override
    public Acount getParentByAcount(Acount acount) {
        String sql = "SELECT a1.* FROM acount a INNER JOIN acount a1 ON a.parentId = a1.id WHERE a.id=?";
        return DataAccessUtils.singleResult(template.query(sql, acountRowMapper, acount.getId()));
    }
}

@Service
class AcountRowMapper implements RowMapper<AcountLazy>{
    @Override
    public AcountLazy mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AcountLazy(rs.getInt("id")
                , rs.getString("name")
                , rs.getString("type")
                , rs.getBoolean("group")
                , null,rs.getString("code")
                , rs.getString("anotation")
                , new Dimension(
                        rs.getInt("positionX"),
                        rs.getInt("positionY")
                ));
    }  
}
