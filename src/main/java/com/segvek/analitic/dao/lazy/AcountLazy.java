/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segvek.analitic.dao.lazy;

import com.segvek.analitic.dao.AcountDAO;
import com.segvek.analitic.dao.CorrespondenceDAO;
import com.segvek.analitic.model.Acount;
import com.segvek.analitic.model.Correspondence;
import java.awt.Point;
import java.util.List;

public class AcountLazy extends Acount {

    private AcountDAO acountDAO;
    private CorrespondenceDAO correspondenceDAO;
    
    public AcountLazy(int id, String name, String type, boolean group, Acount parent, String code, String anotation, Point position) {
        super(id, name, type, group, parent, code, anotation, position);
    }

    @Override
    public Acount getParent() {
        if (super.getParent() == null) {
            setParent(acountDAO.getParentByAcount(this));
        }
        return super.getParent();
    }

    public void setAcountDao(AcountDAO aThis) {
        this.acountDAO = aThis;
    }
    
    public void setCorrespondenceDao(CorrespondenceDAO cdao){
        correspondenceDAO=cdao;
    }
    
    
    @Override
    public List<Correspondence> getDebetCorrespondences() {
        if(super.getDebetCorrespondences()==null){
            setDebetCorrespondence(correspondenceDAO.getCorrespondencesByDebetAcount(this));
        }
        return super.getDebetCorrespondences();
    }

    @Override
    public List<Correspondence> getCreditCorrespondences() {
        if(super.getCreditCorrespondences()==null){
            setCreditCorrespondence(correspondenceDAO.getCorrespondencesByCreditAcount(this));
        }
        return super.getCreditCorrespondences(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Acount> getSubAcounts() {
        if(super.getSubAcounts()==null){
            setSubAcounts(acountDAO.getAcountsByParent(this));
        }
        return super.getSubAcounts(); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
}
