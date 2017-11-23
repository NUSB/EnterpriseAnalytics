
package com.segvek.analitic.dao.mysql;

import com.segvek.analitic.dao.UserDAO;
import com.segvek.analitic.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMysqlDao implements UserDAO{

    @Autowired
    UserRowMapper userRowMapper;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Override
    public List<User> getAllUser() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public void save(User user) {
        String sql="INSERT INTO users(username, password, enabled)VALUES (?,?,?);";
        jdbcTemplate.update(sql,user.getName(),user.getPassword(),user.isEnabled());
    }
    
    
}

@Service
class UserRowMapper implements RowMapper<User>{

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getString("username"), rs.getString("password"), rs.getBoolean("enabled"));
    }
    
}
