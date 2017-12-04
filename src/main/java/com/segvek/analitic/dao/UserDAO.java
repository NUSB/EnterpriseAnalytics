package com.segvek.analitic.dao;

import com.segvek.analitic.model.User;
import com.segvek.analitic.model.UserRole;
import java.util.List;


public interface UserDAO {
    
    public List<User> getAllUser();

    public void save(User user);

    public User getUserByName(String name);

    public List<UserRole> getRolesByUser(User user);

    public void update(User user);

}
