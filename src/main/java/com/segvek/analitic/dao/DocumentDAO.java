package com.segvek.analitic.dao;

import com.segvek.analitic.model.BisnesRole;
import com.segvek.analitic.model.Document;
import java.util.List;
import java.util.Map;

public interface DocumentDAO {

    List<Document> getAllDocument();

    public Document getDocumentByName(String name);

    public void save(Document acount);

    public void deleteDocumentById(int id);

    public Document getDocumentById(int id);

    public void update(Document acount);

    public Map<BisnesRole, String> getBisnesRolesByDocument(Document aThis);
}
