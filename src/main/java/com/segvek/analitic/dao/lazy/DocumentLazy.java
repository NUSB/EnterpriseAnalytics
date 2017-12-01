/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segvek.analitic.dao.lazy;

import com.segvek.analitic.dao.DocumentDAO;
import com.segvek.analitic.model.Document;
import com.segvek.analitic.model.ResponsibilityForDocuments;
import java.awt.Point;
import java.util.List;

public class DocumentLazy extends Document {

    private DocumentDAO documentDAO;

    public DocumentLazy(int id, String name, String annotation, Point position) {
        super(id, name, annotation, position);
    }

    public void setDocumentDAO(DocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }

    @Override
    public List<ResponsibilityForDocuments> getResponsibilityForDocumentses() {
        if (super.getResponsibilityForDocumentses() == null) {
            return documentDAO.getResponsibilityForDocument(this);
        }
        return super.getResponsibilityForDocumentses();
    }
}
