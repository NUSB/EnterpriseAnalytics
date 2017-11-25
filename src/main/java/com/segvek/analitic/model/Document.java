package com.segvek.analitic.model;

import java.awt.Point;
import java.util.Map;

public class Document extends GraphicElement{
    private int id;
    private String name;
    private String annotation;
    
    private Map<BisnesRole,String> bisnesRoles; 

    public Document() {
    }

    public Map<BisnesRole, String> getBisnesRoles() {
        return bisnesRoles;
    }

    public void setBisnesRoles(Map<BisnesRole, String> bisnesRoles) {
        this.bisnesRoles = bisnesRoles;
    }

 
    public Document(int id, String name, String annotation,Point position) {
        super(position);
        this.id = id;
        this.name = name;
        this.annotation = annotation;
    }

    public Document(String name, String annotation) {
        this.name = name;
        this.annotation = annotation;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
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
    
    
}
