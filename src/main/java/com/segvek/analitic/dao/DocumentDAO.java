package com.segvek.analitic.dao;

import com.segvek.analitic.model.Document;
import com.segvek.analitic.model.ResponsibilityForDocuments;
import java.util.List;

public interface DocumentDAO {

    public List<Document> getAllDocument();

    public Document getDocumentByName(String name);

    public void save(Document acount);

    public void deleteDocumentById(int id);

    public Document getDocumentById(int id);

    public void update(Document acount);

    public List<ResponsibilityForDocuments> getResponsibilityForDocument(Document document);
}
