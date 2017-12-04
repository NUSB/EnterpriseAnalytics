/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segvek.analitic.dao.lazy;

import com.segvek.analitic.dao.UserDAO;
import com.segvek.analitic.model.User;
import com.segvek.analitic.model.UserRole;
import java.util.List;

/**
 *
 * @author Panas
 */
public class UserLazy extends User{

    private UserDAO userDAO;
    
    public UserLazy(String name, String password, boolean enabled) {
        super(name, password, enabled);
    }

    @Override
    public List<UserRole> getRoles() {
        if(super.getRoles()==null){
            return userDAO.getRolesByUser(this);
        }
        return super.getRoles(); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    
    
}
