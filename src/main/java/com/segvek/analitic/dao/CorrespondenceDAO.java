
package com.segvek.analitic.dao;

import com.segvek.analitic.model.Acount;
import com.segvek.analitic.model.Correspondence;
import com.segvek.analitic.model.Document;
import java.util.List;


public interface CorrespondenceDAO {
    
    public List<Correspondence> getAllCorrespondences();
    
    public Correspondence getCorrespondeceById(int id);

    public Acount getDebetAcountByCorrespondence(Correspondence correspondence);

    public Acount getCreditAcountByCorrespondence(Correspondence correspondence);

    public Document getDocumentByCorrespondence(Correspondence document);
}
