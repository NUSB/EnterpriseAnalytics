package com.segvek.analitic.dao;

import com.segvek.analitic.model.BisnesRole;
import java.util.List;


public interface BisnesRoleDAO {
    
    List<BisnesRole> getAllBisnesRole();

    public BisnesRole getParentByBisnesRole(BisnesRole acount);

    public BisnesRole getBisnesRoleByName(String name);

    public void save(BisnesRole acount);

    public void deleteBisnesRoleById(int id);

    public BisnesRole getBisnesRoleById(int id);

    public void update(BisnesRole acount);
}
