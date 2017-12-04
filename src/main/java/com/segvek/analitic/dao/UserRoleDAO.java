/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segvek.analitic.dao;

import com.segvek.analitic.model.User;
import com.segvek.analitic.model.UserRole;
import java.util.List;

/**
 *
 * @author Panas
 */
public interface UserRoleDAO {

    public List<UserRole> getRolesBuUser(User user);

    public void deleteRolesByUser(User user);

    public void save(UserRole ur);
    
}
