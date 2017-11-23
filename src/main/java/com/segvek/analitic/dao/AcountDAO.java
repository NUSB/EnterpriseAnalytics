package com.segvek.analitic.dao;

import com.segvek.analitic.model.Acount;
import java.util.List;

public interface AcountDAO {
    
    List<Acount> getAllAcount();

    public Acount getParentByAcount(Acount acount);
}
