package com.segvek.analitic.model;

import java.awt.Point;
import java.util.List;

public class Acount extends GraphicElement {

    private int id;
    private String name;
    private String type = "П/А";
    private boolean group;
    private Acount parent;
    private String code;
    private String annotation;

    private List<Correspondence> debetCorrespondence;
    private List<Correspondence> creditCorrespondence;
    private List<Acount> subAcounts;

    public Acount(int id, String name, String type, boolean group, Acount parent, String code, String anotation, Point position) {
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

    public List<Correspondence> getDebetCorrespondences() {
        return debetCorrespondence;
    }

    public List<Correspondence> getCreditCorrespondences() {
        return creditCorrespondence;
    }

    public List<Acount> getSubAcounts() {
        return subAcounts;
    }

    public void setDebetCorrespondence(List<Correspondence> debetCorrespondence) {
        this.debetCorrespondence = debetCorrespondence;
    }

    public void setCreditCorrespondence(List<Correspondence> creditCorrespondence) {
        this.creditCorrespondence = creditCorrespondence;
    }

    public void setSubAcounts(List<Acount> subAcounts) {
        this.subAcounts = subAcounts;
    }
    
    
}
