/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segvek.analitic.model;

/**
 *
 * @author Panas
 */
public class UserRole {
    private int id;
    private User user; 
    private UserRolesEnum role;

    public UserRole(int id, User user, UserRolesEnum role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }

    public UserRole(User user, UserRolesEnum role) {
        this.user = user;
        this.role = role;
    }

    public UserRolesEnum getRole() {
        return role;
    }

    public void setRole(UserRolesEnum role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
