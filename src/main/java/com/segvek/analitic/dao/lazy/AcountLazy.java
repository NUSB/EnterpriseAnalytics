/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segvek.analitic.dao.lazy;

import com.segvek.analitic.dao.AcountDAO;
import com.segvek.analitic.model.Acount;
import java.awt.Dimension;

public class AcountLazy extends Acount {

    private AcountDAO acountDAO;

    public AcountLazy(int id, String name, String type, boolean group, Acount parent, String code, String anotation, Dimension position) {
        super(id, name, type, group, parent, code, anotation, position);
    }

    @Override
    public Acount getParent() {
        if (super.getParent() == null) {
            return acountDAO.getParentByAcount(this);
        }
        return super.getParent();
    }

    public void setAcountDao(AcountDAO aThis) {
        this.acountDAO = aThis;
    }

}
