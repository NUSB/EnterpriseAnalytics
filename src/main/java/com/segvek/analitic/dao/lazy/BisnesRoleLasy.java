package com.segvek.analitic.dao.lazy;

import com.segvek.analitic.dao.BisnesRoleDAO;
import com.segvek.analitic.model.BisnesRole;
import com.segvek.analitic.model.Document;
import java.awt.Point;
import java.util.List;

public class BisnesRoleLasy extends BisnesRole {

    private BisnesRoleDAO bisnesRoleDAO;

    public BisnesRoleLasy(int id, String name, String annotation, Point position) {
        super(id, name, annotation, position);
    }

    public void setBisnesRoleDAO(BisnesRoleDAO bisnesRoleDAO) {
        this.bisnesRoleDAO = bisnesRoleDAO;
    }

    @Override
    public BisnesRole getParent() {
        if (super.getParent() == null) {
            return bisnesRoleDAO.getParentByBisnesRole(this);
        }
        return super.getParent();
    }

    @Override
    public List<Document> getDocuments() {
        if (super.getDocuments() == null) {
            return bisnesRoleDAO.getDocumentsByBisnesRole(this);
        }
        return super.getDocuments(); 
    }

    @Override
    public List<BisnesRole> getChildren() {
        if(super.getChildren()==null){
            return bisnesRoleDAO.getChildrenByBisnesRole(this);
        }
        return super.getChildren(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
