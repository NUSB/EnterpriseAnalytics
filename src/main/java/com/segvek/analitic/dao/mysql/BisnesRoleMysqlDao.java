package com.segvek.analitic.dao.mysql;

import com.segvek.analitic.dao.BisnesRoleDAO;
import com.segvek.analitic.dao.DocumentDAO;
import com.segvek.analitic.dao.lazy.BisnesRoleLasy;
import com.segvek.analitic.model.BisnesRole;
import com.segvek.analitic.model.Document;
import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service(value = "bisnesRoleDAO")
public class BisnesRoleMysqlDao implements BisnesRoleDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    BisnesRoleRowMapper bisnesRoleRowMapper;
    @Autowired
    DocumentDAO documentDAO;

    @Override
    public List<BisnesRole> getAllBisnesRole() {
        String sql = "SELECT * FROM bisnes_roles;";
        List<BisnesRoleLasy> acounts = jdbcTemplate.query(sql, bisnesRoleRowMapper);
        List<BisnesRole> res = new ArrayList<BisnesRole>();
        for (BisnesRoleLasy a : acounts) {
            a.setBisnesRoleDAO(this);
            res.add(a);
        }
        return res;
    }

    @Override
    public BisnesRole getParentByBisnesRole(BisnesRole bisnesRole) {
        String sql = "SELECT a1.* FROM bisnes_roles a INNER JOIN bisnes_roles a1 ON a.parent = a1.id WHERE a.id=?";
        BisnesRoleLasy a = DataAccessUtils.singleResult(jdbcTemplate.query(sql, bisnesRoleRowMapper, bisnesRole.getId()));
        if (a != null) {
            a.setBisnesRoleDAO(this);
        }
        return a;
    }

    @Override
    public BisnesRole getBisnesRoleByName(String name) {
        String sql = "SELECT * FROM bisnes_roles a WHERE a.name=?";
        BisnesRoleLasy a = jdbcTemplate.queryForObject(sql, bisnesRoleRowMapper, name);
        a.setBisnesRoleDAO(this);
        return a;
    }

    @Override
    public void save(BisnesRole bisnesRole) {
        String sql = "INSERT INTO bisnes_roles (name,annotation,parent,positionX,positionY) VALUES (?,?,?,?,?);";
        jdbcTemplate.update(sql, bisnesRole.getName(),
                bisnesRole.getAnnotation(),
                bisnesRole.getParent() != null ? bisnesRole.getParent().getId() : null,
                bisnesRole.getPosition().x,
                bisnesRole.getPosition().y);
    }

    @Override
    public BisnesRole getBisnesRoleById(int id) {
        String sql = "Select * FROm bisnes_roles where id=?";
        BisnesRoleLasy a = DataAccessUtils.singleResult(jdbcTemplate.query(sql, bisnesRoleRowMapper, id));
        a.setBisnesRoleDAO(this);
        return a;
    }

    @Override
    public void update(BisnesRole bisnes_role) {
        String sql = "UPDATE bisnes_roles SET name=?,annotation=?, positionX=?, positionY=?,parent=? WHERE id=?;";
        jdbcTemplate.update(sql, bisnes_role.getName(),
                bisnes_role.getAnnotation(),
                bisnes_role.getPosition().x,
                bisnes_role.getPosition().y,
                bisnes_role.getParent() == null ? null : bisnes_role.getParent().getId(),
                bisnes_role.getId());
    }

    @Override
    public void deleteBisnesRole(BisnesRole bisnesRole) {
        String sql = "DELETE FROM bisnes_roles WHERE id=?";
        jdbcTemplate.update(sql, bisnesRole.getId());
    }

    @Override
    public List<Document> getDocumentsByBisnesRole(BisnesRole bisnesRole) {
        return documentDAO.getDocumentsByBisnesRole(bisnesRole);
    }

    @Override
    public List<BisnesRole> getChildrenByBisnesRole(BisnesRole bisnesRole) {
        String sql = "SELECT * FROM bisnes_roles br WHERE br.parent=?";
        List<BisnesRoleLasy> acounts = jdbcTemplate.query(sql, bisnesRoleRowMapper, bisnesRole.getId());
        List<BisnesRole> res = new ArrayList<BisnesRole>();
        for (BisnesRoleLasy a : acounts) {
            a.setBisnesRoleDAO(this);
            res.add(a);
        }
        return res;
    }

}

@Service
class BisnesRoleRowMapper implements RowMapper<BisnesRoleLasy> {

    @Override
    public BisnesRoleLasy mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new BisnesRoleLasy(rs.getInt("id"), rs.getString("name"), rs.getString("annotation"),
                new Point(rs.getInt("positionX"), rs.getInt("positionY")));
    }

}
