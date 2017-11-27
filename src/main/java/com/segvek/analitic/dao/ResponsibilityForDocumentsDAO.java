package com.segvek.analitic.dao;

import com.segvek.analitic.model.BisnesRole;
import com.segvek.analitic.model.Document;
import com.segvek.analitic.model.ResponsibilityForDocuments;
import java.util.List;

public interface ResponsibilityForDocumentsDAO {

    public List<ResponsibilityForDocuments> getResponsibilityForDocumentsByDocument(Document document);

    public Document getDocumentByResponsibilityForDocuments(ResponsibilityForDocuments rfd);

    public BisnesRole getBisnesRoleByResponsibilityForDocuments(ResponsibilityForDocuments rfd);
}
