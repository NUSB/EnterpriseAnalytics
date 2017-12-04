package com.segvek.analitic.dao.mysql;

import com.segvek.analitic.dao.UserDAO;
import com.segvek.analitic.dao.UserRoleDAO;
import com.segvek.analitic.dao.lazy.UserLazy;
import com.segvek.analitic.model.User;
import com.segvek.analitic.model.UserRole;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMysqlDao implements UserDAO {

    @Autowired
    UserRowMapper userRowMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRoleDAO userRoleDAO;

    @Override
    public List<User> getAllUser() {
        String sql = "SELECT * FROM users";
        List<User> res = new LinkedList<User>();
        List<UserLazy> uls = jdbcTemplate.query(sql, userRowMapper);
        for (UserLazy ul : uls) {
            ul.setUserDAO(this);
            res.add(ul);
        }
        return res;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users(username, password, enabled)VALUES (?,?,?);";
        jdbcTemplate.update(sql, user.getName(), user.getPassword(), user.isEnabled());
    }

    @Override
    public User getUserByName(String name) {
        String sql = "SELECT * FROM users u WHERE u.username=?";
        UserLazy ul = jdbcTemplate.queryForObject(sql, userRowMapper, name);
        ul.setUserDAO(this);
        return ul;
    }

    @Override
    public List<UserRole> getRolesByUser(User user) {
        return userRoleDAO.getRolesBuUser(user);
    }

    @Override
    public void update(User user) {
        userRoleDAO.deleteRolesByUser(user);
        String sql = "UPDATE users u  SET u.password=?, u.enabled=?";
        jdbcTemplate.update(sql, user.getPassword(), user.isEnabled());
        if (user.getRoles() != null) {
            for (UserRole ur : user.getRoles()) {
                userRoleDAO.save(ur);
            }
        }
    }

}

@Service
class UserRowMapper implements RowMapper<UserLazy> {

    @Override
    public UserLazy mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserLazy(rs.getString("username"), rs.getString("password"), rs.getBoolean("enabled"));
    }

}
