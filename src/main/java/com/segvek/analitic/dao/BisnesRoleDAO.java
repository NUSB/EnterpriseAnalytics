package com.segvek.analitic.dao;

import com.segvek.analitic.model.BisnesRole;
import com.segvek.analitic.model.Document;
import java.util.List;


public interface BisnesRoleDAO {
    
    List<BisnesRole> getAllBisnesRole();

    public BisnesRole getParentByBisnesRole(BisnesRole acount);
//todo: dfgfd
    public BisnesRole getBisnesRoleByName(String name);

    public void save(BisnesRole acount);

    public BisnesRole getBisnesRoleById(int id);

    public void update(BisnesRole acount);

    public void deleteBisnesRole(BisnesRole bisnesRoleById);

    public List<Document> getDocumentsByBisnesRole(BisnesRole bisnesRole);

    public List<BisnesRole> getChildrenByBisnesRole(BisnesRole bisnesRole);
}
