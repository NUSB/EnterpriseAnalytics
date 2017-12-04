package com.segvek.analitic.model.chart;

import com.segvek.analitic.model.Acount;

public class ChartAcount extends ChartObject {

    private Acount acount;

    public ChartAcount(Acount acount) {
        this.acount = acount;
        this.id = "acount;" + acount.getId();
    }

    public ChartAcount getParent() {
        Acount parent = acount.getParent();
        if (parent == null) {
            return null;
        }
        return new ChartAcount(parent);
    }

    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{\"type\":\"acc\",")
                .append(" \"id\":\"").append(id).append("\",")
                .append(" \"x\":\"").append(acount.getPosition().x).append("\",")
                .append(" \"y\":\"").append(acount.getPosition().y).append("\",")
                .append(" \"name\":\"").append(acount.getCode()).append("\",")
                .append(" \"info\":\"").append(acount.getName()).append("\",")
                .append(" \"link\":\"").append("acount\\\\").append(acount.getId()).append("\"}");
        return sb.toString();
    }
}