/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segvek.analitic.model;


public class ResponsibilityForDocuments {
    private int id;
    private BisnesRole bisnesRole;
    private Document document;
    private String annotation;

    public ResponsibilityForDocuments(int id, BisnesRole bisnesRole, Document document, String annotation) {
        this.id = id;
        this.bisnesRole = bisnesRole;
        this.document = document;
        this.annotation = annotation;
    }

    public ResponsibilityForDocuments(BisnesRole bisnesRole, Document document, String annotation) {
        this.bisnesRole = bisnesRole;
        this.document = document;
        this.annotation = annotation;
    }

    public ResponsibilityForDocuments() {
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

    public BisnesRole getBisnesRole() {
        return bisnesRole;
    }

    public void setBisnesRole(BisnesRole bisnesRole) {
        this.bisnesRole = bisnesRole;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
    
    
}
