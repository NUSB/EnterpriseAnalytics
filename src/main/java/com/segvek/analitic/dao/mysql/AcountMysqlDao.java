package com.segvek.analitic.dao.mysql;

import com.segvek.analitic.dao.AcountDAO;
import com.segvek.analitic.model.Acount;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class AcountMysqlDao implements AcountDAO{
    
    @Autowired
    JdbcTemplate template;
    
    @Autowired
    AcountRowMapper acountRowMapper;
    
    @Override
    public List<Acount> getAllAcount() {
        String sql="Select * from acount ORDER BY code";
        return template.query(sql, acountRowMapper);
    }   
}

@Service
class AcountRowMapper implements RowMapper<Acount>{
    @Override
    public Acount mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Acount(rs.getInt("id")
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
