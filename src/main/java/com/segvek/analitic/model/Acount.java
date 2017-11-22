package com.segvek.analitic.model;

public class Acount {
    private int id;
    private String name;
    private String type;
    private boolean group;
    private Acount parent;
    private String code;
    private String anotation;

    public Acount(int id, String name, String type, boolean group, Acount parent, String code, String anotation) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.group = group;
        this.parent = parent;
        this.code = code;
        this.anotation = anotation;
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
        return anotation;
    }

    public void setAnotation(String anotation) {
        this.anotation = anotation;
    }
   
    
        
}
