package com.segvek.analitic.model;

import java.util.LinkedList;
import java.util.List;

public class User {
    
    private String name;
    private String password;
    private boolean enabled;
    private List<UserRole> roles;
    
    public User(String name, String password, boolean enabled) {
        this.name = name;
        this.password = password;
        this.enabled = enabled;
    }

    public User() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public void addRole(String role) {
        if(this.roles==null){
            roles = new LinkedList<UserRole>();
        }
        roles.add(new UserRole(this, role));
    }

    @Override
    public String toString() {
        return "User{" + "name=" + name + ", password=" + password + ", enabled=" + enabled + ", roles=" + roles + '}';
    }
    
    
}
