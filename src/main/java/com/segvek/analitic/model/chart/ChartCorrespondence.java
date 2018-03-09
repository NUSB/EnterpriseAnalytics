package com.segvek.analitic.model.chart;

import com.segvek.analitic.model.Acount;
import com.segvek.analitic.model.Correspondence;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChartCorrespondence extends ChartObject {

    private Acount debet;
    private Acount credit;
    private List<Correspondence> correspondences;
    private boolean biDirectionsl = false;

    public ChartCorrespondence(Correspondence correspondence) {
        correspondences = new LinkedList<Correspondence>();
        debet = correspondence.getDebet();
        credit = correspondence.getCredit();
        correspondences.add(correspondence);
    }

    public boolean addCorrespondence(Correspondence c) {
        for (Correspondence c1 : correspondences) {
            if ((c.getDebet().getId() == c1.getDebet().getId() && c.getCredit().getId() == c1.getCredit().getId())
                    || (c.getDebet().getId() == c1.getCredit().getId() && c.getCredit().getId() == c1.getDebet().getId())) {
                correspondences.add(c);
                if (c.getDebet().getId() == c1.getCredit().getId() && c.getCredit().getId() == c1.getDebet().getId()) {
                    biDirectionsl = true;
                }
                return true;
            }
        }
        return false;
    }

    public ChartAcount getDebet() {
        return new ChartAcount(debet);
    }

    public ChartAcount getCredit() {
        return new ChartAcount(credit);
    }

    public boolean isBiDirectionsl() {
        return biDirectionsl;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        for (Correspondence c : correspondences) {
            info.append("Дт ").append(c.getDebet().getCode()).append(":")
                    .append("Кт").append(c.getCredit().getCode())
                    .append("-").append(c.getDocument().getName()).append("  ");
        }
        int x = debet.getPosition().x - (debet.getPosition().x - credit.getPosition().x) / 2;
        int y = debet.getPosition().y - (debet.getPosition().y - credit.getPosition().y) / 2;
        StringBuilder sb = new StringBuilder("{\"type\":\"crr\", \"x\":\"").append(x)
                .append("\",  \"id\":\"").append(id)
                .append("\", \"y\":\"").append(y)
                .append("\", \"name\":\"").append("")
                .append("\", \"info\":\"").append(info.toString())
                .append("\", \"link\":\"").append("info\\\\correspondence/1");
        sb.append("\"}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChartCorrespondence) {
            ChartCorrespondence cc = (ChartCorrespondence) obj;
            return (this.debet.getId() == cc.debet.getId() && this.credit.getId() == cc.credit.getId())
                    || (this.debet.getId() == cc.credit.getId() && this.credit.getId() == cc.debet.getId());
        }
        return false;
    }

    public List<ChartDocument> getChartDocuments() {
        List<ChartDocument> res = new ArrayList<ChartDocument>();
        for(Correspondence c:correspondences){
            res.add(new ChartDocument(c.getDocument()));
        }
        return res;
    }
}
