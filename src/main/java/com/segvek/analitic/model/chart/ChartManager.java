package com.segvek.analitic.model.chart;

import com.segvek.analitic.dao.AcountDAO;
import com.segvek.analitic.dao.BisnesRoleDAO;
import com.segvek.analitic.dao.CorrespondenceDAO;
import com.segvek.analitic.dao.DocumentDAO;
import com.segvek.analitic.model.Acount;
import com.segvek.analitic.model.BisnesRole;
import com.segvek.analitic.model.Correspondence;
import com.segvek.analitic.model.Document;
import java.util.ArrayList;
import java.util.List;

public class ChartManager {

    List<ChartObject> chartObjects;

    CorrespondenceDAO correspondenceDAO;
    DocumentDAO documentDAO;
    BisnesRoleDAO bisnesRoleDAO;
    AcountDAO acountDAO;

    public ChartManager(CorrespondenceDAO correspondenceDAO, DocumentDAO documentDAO, BisnesRoleDAO bisnesRoleDAO, AcountDAO acountDAO) {
        this.correspondenceDAO = correspondenceDAO;
        this.documentDAO = documentDAO;
        this.bisnesRoleDAO = bisnesRoleDAO;
        this.acountDAO = acountDAO;
        chartObjects = new ArrayList<ChartObject>();
    }


    public void initbisnesRoleList() {
        for (BisnesRole br : bisnesRoleDAO.getAllBisnesRole()) {
            chartObjects.add(new ChartRole(br));
        }
    }

    public void initAcountList() {
        for (Acount a : acountDAO.getAllAcount()) {
            chartObjects.add(new ChartAcount(a));
        }
    }

    public void initDocumentList() {
        for (Document d : documentDAO.getAllDocument()) {
            chartObjects.add(new ChartDocument(d));
        }
    }

    public void initCorespondenceList() {
        List<ChartCorrespondence> corr = new ArrayList<ChartCorrespondence>();
        for (Correspondence c : correspondenceDAO.getAllCorrespondences()) {
            boolean flug = false;
            for (ChartCorrespondence cc : corr) {
                if (cc.addCorrespondence(c)) {
                    flug = true;
                    break;
                }
            }
            if (!flug) {
                ChartCorrespondence cc = new ChartCorrespondence(c);
                corr.add(cc);
                chartObjects.add(cc);
            }
        }
    }

    public List<ChartObject> getChartObjects() {
        return chartObjects;
    }

    public char[][] getChartIncedence() {
        char res[][] = new char[chartObjects.size()][chartObjects.size()];

        for (int i = 0; i < chartObjects.size(); i++) {
            if (chartObjects.get(i) instanceof ChartDocument) {
                ChartDocument cd = (ChartDocument) chartObjects.get(i);
                List<ChartRole> roles = cd.getChartRoles();
                for (int j = 0; j < chartObjects.size(); j++) {
                    for (ChartRole role : roles) {
                        if (chartObjects.get(j).equals(role)) {
                            res[i][j] = 'd';
                            res[j][i] = 'd';
                        }
                    }
                }
            }
            if (chartObjects.get(i) instanceof ChartAcount) {
                ChartAcount ca = (ChartAcount) chartObjects.get(i);
                ChartAcount caParent = ca.getParent();
                if (caParent == null) {
                    continue;
                }
                for (int j = 0; j < chartObjects.size(); j++) {
                    if (chartObjects.get(j).equals(caParent)) {
                        res[i][j] = 's';
                        continue;
                    }
                }
            }
            if (chartObjects.get(i) instanceof ChartRole) {
                ChartRole cr = (ChartRole) chartObjects.get(i);
                ChartRole crParent = cr.getParent();
                if (crParent == null) {
                    continue;
                }
                for (int j = 0; j < chartObjects.size(); j++) {
                    if (chartObjects.get(j).equals(crParent)) {
                        res[i][j] = 's';
                        continue;
                    }
                }
            }
            if (chartObjects.get(i) instanceof ChartCorrespondence) {
                ChartCorrespondence ccr = (ChartCorrespondence) chartObjects.get(i);
                List<ChartDocument> documents = ccr.getChartDocuments();
                ChartAcount dt = ccr.getDebet();
                ChartAcount ct = ccr.getCredit();

                for (int j = 0; j < chartObjects.size(); j++) {
                    if (chartObjects.get(j).equals(dt)) {
                        res[i][j] = 't';
                        if (ccr.isBiDirectionsl()) {
                            res[j][i] = 't';
                        }
                    }
                    if (chartObjects.get(j).equals(ct)) {
                        res[j][i] = 't';
                        if (ccr.isBiDirectionsl()) {
                            res[i][j] = 't';
                        }
                    }
                    for (ChartDocument doc : documents) {
                        if (chartObjects.get(j).equals(doc)) {
                            res[i][j] = 'd';
                            res[j][i] = 'd';
                        }
                    }
                }

            }
        }
        
        for(int i=0; i<chartObjects.size(); i++){
            for(int j=0; j<chartObjects.size(); j++){
                if(res[i][j]==0){
                    res[i][j]=' ';
                }
            }
        }

        return res;
    }

    public String toJson(){
        long time = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("{\"objects\":");
        sb.append(chartObjects.toString());
        
        char m[][] = getChartIncedence();
        sb.append(",\"incedence\":[");
        for(int i=0; i<m.length; i++){
            sb.append("[");
            for(int j=0; j<m[i].length; j++){
                sb.append("\"").append(m[i][j]).append("\"");
                if(j<m[i].length-1)
                    sb.append(",");
            }
            sb.append("]");
            if(i<m.length-1)
                sb.append(",");
        }
        sb.append("]}");
        sb.append(System.currentTimeMillis()-time);
        return sb.toString();
    }
}
