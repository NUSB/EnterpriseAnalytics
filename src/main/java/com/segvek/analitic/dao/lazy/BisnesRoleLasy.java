package com.segvek.analitic.dao.lazy;

import com.segvek.analitic.dao.BisnesRoleDAO;
import com.segvek.analitic.model.BisnesRole;
import java.awt.Point;


public class BisnesRoleLasy extends BisnesRole{
    
    private BisnesRoleDAO bisnesRoleDAO;
    
    public BisnesRoleLasy(int id, String name, String annotation, Point position) {
        super(id, name, annotation, position);
    }

    public void setBisnesRoleDAO(BisnesRoleDAO bisnesRoleDAO) {
        this.bisnesRoleDAO = bisnesRoleDAO;
    }

    @Override
    public BisnesRole getParent() {
        if(super.getParent()==null){
            return bisnesRoleDAO.getParentByBisnesRole(this);
        }
        return super.getParent();
    }

    
}
