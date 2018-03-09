package com.segvek.analitic.model.chart;

import com.segvek.analitic.model.BisnesRole;

public class ChartRole extends ChartObject {

    private BisnesRole bisnesRole;

    public ChartRole(BisnesRole bisnesRole) {
        this.bisnesRole = bisnesRole;
        this.id = "role;" + bisnesRole.getId();
    }

    public ChartRole getParent() {
        BisnesRole parent = bisnesRole.getParent();
        if (parent == null) {
            return null;
        }
        return new ChartRole(parent);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{\"type\":\"role\",")
                .append(" \"id\":\"").append(id).append("\",")
                .append(" \"x\":\"").append(bisnesRole.getPosition().x).append("\",")
                .append(" \"y\":\"").append(bisnesRole.getPosition().y).append("\",")
                .append(" \"name\":\"").append(bisnesRole.getName()).append("\",")
                .append(" \"info\":\"").append("").append("\",")
                .append(" \"link\":\"").append("info\\\\bisnesRole\\\\").append(bisnesRole.getId()).append("\"}");
        return sb.toString();
    }
    
    
}