/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segvek.analitic.dao.mysql;

import com.segvek.analitic.dao.UserRoleDAO;
import com.segvek.analitic.model.User;
import com.segvek.analitic.model.UserRole;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service(value = "userRoleDAO")
public class UserRoleMysqlDao implements UserRoleDAO{

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    UserRoleRowMapper userRoleRowMapper;
    
    @Override
    public List<UserRole> getRolesBuUser(User user) {
        String sql = "SELECT * FROM user_roles ur WHERE ur.username=?";
        return jdbcTemplate.query(sql, userRoleRowMapper, user.getName());
    }

    @Override
    public void deleteRolesByUser(User user) {
        String sql="DELETE FROM user_roles WHERE username=?";
        jdbcTemplate.update(sql,user.getName());
    }

    @Override
    public void save(UserRole ur) {
        String sql="INSERT INTO user_roles(username, role) VALUES (?,?)";
        jdbcTemplate.update(sql,ur.getUser().getName(),ur.getRole());
    }
    
}

@Service
class UserRoleRowMapper implements RowMapper<UserRole>{

    @Override
    public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserRole(rs.getInt("user_role_id"), null, rs.getString("role"));
    }
        
    
    
}
