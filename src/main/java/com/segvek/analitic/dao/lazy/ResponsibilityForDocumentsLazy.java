
package com.segvek.analitic.dao.lazy;

import com.segvek.analitic.dao.ResponsibilityForDocumentsDAO;
import com.segvek.analitic.model.BisnesRole;
import com.segvek.analitic.model.Document;
import com.segvek.analitic.model.ResponsibilityForDocuments;


public class ResponsibilityForDocumentsLazy extends ResponsibilityForDocuments{
    
    private ResponsibilityForDocumentsDAO responsibilityForDocumentsDAO;
    
    public ResponsibilityForDocumentsLazy(int id, BisnesRole bisnesRole, Document document, String annotation) {
        super(id, bisnesRole, document, annotation);
    }

    @Override
    public Document getDocument() {
        if(super.getDocument()==null)
            return responsibilityForDocumentsDAO.getDocumentByResponsibilityForDocuments(this);
        return super.getDocument();
    }

    @Override
    public BisnesRole getBisnesRole() {
        if(super.getBisnesRole()==null){
            return responsibilityForDocumentsDAO.getBisnesRoleByResponsibilityForDocuments(this);
        }
        return super.getBisnesRole(); 
    }
    
    

    public void setResponsibilityForDocumentsDAO(ResponsibilityForDocumentsDAO responsibilityForDocumentsDAO) {
        this.responsibilityForDocumentsDAO = responsibilityForDocumentsDAO;
    }
    
}
