package com.segvek.analitic.dao.lazy;

import com.segvek.analitic.dao.CorrespondenceDAO;
import com.segvek.analitic.model.Acount;
import com.segvek.analitic.model.Correspondence;
import com.segvek.analitic.model.Document;

public class CorrespondenceLazy extends Correspondence{
    
    private CorrespondenceDAO correspondenceDAO;
    
    public CorrespondenceLazy(int id, Acount debet, Acount credit, Document document, String annotation) {
        super(id, debet, credit, document, annotation);
    }

    public void setCorrespondenceDAO(CorrespondenceDAO correspondenceDAO) {
        this.correspondenceDAO = correspondenceDAO;
    }

    @Override
    public Acount getDebet() {
        if(super.getDebet()==null){
            return correspondenceDAO.getDebetAcountByCorrespondence(this);
        }
        return super.getDebet();
    }
    
    @Override
    public Acount getCredit() {
        if(super.getCredit()==null){
            return correspondenceDAO.getCreditAcountByCorrespondence(this);
        }
        return super.getCredit();
    }
    
    @Override
    public Document getDocument(){
        if(super.getDocument()==null){
            return correspondenceDAO.getDocumentByCorrespondence(this);
        }
        return super.getDocument();
    }
    
    
}
