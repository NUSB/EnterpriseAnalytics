package com.segvek.analitic.model;

public class Correspondence {

    int id;
    Acount debet;
    Acount credit;
    Document document;
    String annotation;

    public Correspondence(int id, Acount debet, Acount credit, Document document, String annotation) {
        this.id = id;
        this.debet = debet;
        this.credit = credit;
        this.document = document;
        this.annotation = annotation;
    }

    public Correspondence(Acount debet, Acount credit, Document document, String annotation) {
        this.debet = debet;
        this.credit = credit;
        this.document = document;
        this.annotation = annotation;
    }

    public Correspondence() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Acount getDebet() {
        return debet;
    }

    public void setDebet(Acount debet) {
        this.debet = debet;
    }

    public Acount getCredit() {
        return credit;
    }

    public void setCredit(Acount credit) {
        this.credit = credit;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }
}
