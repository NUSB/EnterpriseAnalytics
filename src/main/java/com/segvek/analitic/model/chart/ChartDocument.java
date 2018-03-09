package com.segvek.analitic.model.chart;

import com.segvek.analitic.model.Document;
import com.segvek.analitic.model.ResponsibilityForDocuments;
import java.util.ArrayList;
import java.util.List;

class ChartDocument extends ChartObject {

    Document document;

    public ChartDocument(Document document) {
        this.document = document;
        this.id = "doc;" + document.getId();
    }

    public List<ChartRole> getChartRoles() {
        List<ResponsibilityForDocuments> bisnesRoles = document.getResponsibilityForDocumentses();
        List<ChartRole> res = new ArrayList<ChartRole>();
        for (ResponsibilityForDocuments rfd : bisnesRoles) {
            res.add(new ChartRole(rfd.getBisnesRole()));
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{\"type\":\"doc\",")
                .append(" \"id\":\"").append(id).append("\",")
                .append(" \"x\":\"").append(document.getPosition().x).append("\",")
                .append(" \"y\":\"").append(document.getPosition().y).append("\",")
                .append(" \"name\":\"").append(document.getName()).append("\",")
                .append(" \"info\":\"").append("").append("\",")
                .append(" \"link\":\"").append("info\\\\document\\\\").append(document.getId()).append("\"}");
        return sb.toString();
    }
    
    
}
