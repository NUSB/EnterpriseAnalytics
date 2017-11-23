package com.segvek.analitic.model;

import java.awt.Dimension;

public class Acount extends GraphicElement{
    protected int id;
    protected String name;
    protected String type = "П/А";
    protected boolean group;
    protected Acount parent;
    protected String code;
    protected String annotation;

    public Acount(int id, String name, String type, boolean group, Acount parent, String code, String anotation, Dimension position) {
        super(position);
        this.id = id;
        this.name = name;
        this.type = type;
        this.group = group;
        this.parent = parent;
        this.code = code;
        this.annotation = anotation;
    }
 
     
    
    public Acount(String name, String type, boolean group, Acount parent, String code, String anotation) {
        this.name = name;
        this.type = type;
        this.group = group;
        this.parent = parent;
        this.code = code;
        this.annotation = anotation;
    }

    public Acount() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public Acount getParent() {
        return parent;
    }

    public void setParent(Acount parent) {
        this.parent = parent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAnotation() {
        return annotation;
    }

    public void setAnotation(String anotation) {
        this.annotation = anotation;
    }   

    @Override
    public String toString() {
        return name; //To change body of generated methods, choose Tools | Templates.
    }
        
    
    
}
