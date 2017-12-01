package com.segvek.analitic.model;

import java.awt.Point;
import java.util.List;

public class BisnesRole extends GraphicElement {

    private int id;
    private String name;
    private String annotation;
    private List<Document> documents;
    private BisnesRole parent;
    private List<BisnesRole> children;

    public BisnesRole(int id, String name, String annotation, Point position) {
        super(position);
        this.id = id;
        this.name = name;
        this.annotation = annotation;
    }

    public BisnesRole(String name, String annotation) {
        this.name = name;
        this.annotation = annotation;
    }

    public BisnesRole() {
        
    }

    public List<Document> getDocuments() {
        return documents;
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

    public BisnesRole getParent() {
        return parent;
    }

    public void setParent(BisnesRole parent) {
        this.parent = parent;
    }

    public List<BisnesRole> getChildren() {
        return children;
    }
}
