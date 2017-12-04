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
    private String role;

    public UserRole(int id, User user, String role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }

    public UserRole(User user, String role) {
        this.user = user;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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

    @Override
    public String toString() {
        return "UserRole{" + "id=" + id + ", user=" + user.getName() + ", role=" + role + '}';
    }

   

    
    
    
    
}
