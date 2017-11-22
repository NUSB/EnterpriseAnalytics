package com.segvek.analitic.model;

import java.awt.Dimension;
import java.util.List;

public class BisnesRole extends GraphicElement {

    private int id;
    private String name;
    private String annotation;
    private List<Document> documents;

    public BisnesRole(int id, String name, String annotation, Dimension position) {
        super(position);
        this.id = id;
        this.name = name;
        this.annotation = annotation;
    }

    public BisnesRole(String name, String annotation) {
        this.name = name;
        this.annotation = annotation;
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

}
