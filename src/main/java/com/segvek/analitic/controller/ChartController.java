/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segvek.analitic.controller;

import com.segvek.analitic.dao.BisnesRoleDAO;
import com.segvek.analitic.dao.CorrespondenceDAO;
import com.segvek.analitic.dao.DocumentDAO;
import com.segvek.analitic.dao.UserDAO;
import com.segvek.analitic.dao.mysql.AcountMysqlDao;
import com.segvek.analitic.model.Acount;
import com.segvek.analitic.model.BisnesRole;
import com.segvek.analitic.model.Document;
import com.segvek.analitic.model.User;
import com.segvek.analitic.model.UserRole;
import com.segvek.analitic.model.UserRolesEnum;
import com.segvek.analitic.model.chart.ChartManager;
import java.awt.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChartController {

    @Autowired
    AcountMysqlDao acountDAO;

    @Autowired
    CorrespondenceDAO correspondenceDAO;
    @Autowired
    DocumentDAO documentDAO;
    @Autowired
    BisnesRoleDAO bisnesRoleDAO;
    @Autowired
    UserDAO userDAO;

    @RequestMapping(value = "/chart", method = {RequestMethod.GET}, produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public String getChartModel(Authentication authentication) {

        User user = userDAO.getUserByName(authentication.getName());

        ChartManager cm = new ChartManager(correspondenceDAO, documentDAO, bisnesRoleDAO, acountDAO);
        for (UserRole ur : user.getRoles()) {
            if (ur.getRole().equals(UserRolesEnum.ROLE_USER)) {
                cm.initbisnesRoleList();
            }
            if (ur.getRole().equals(UserRolesEnum.ROLE_DOCUMENTS)) {
                cm.initDocumentList();
            }
            if (ur.getRole().equals(UserRolesEnum.ROLE_ACOUNTING)) {
                cm.initAcountList();
                cm.initCorespondenceList();
            }
            if(ur.getRole().equals(UserRolesEnum.ROLE_ADMIN)){
                cm.setEditable(true);
            }
        }

        return cm.toJson();
    }

    @RequestMapping(value = "/chart", method = {RequestMethod.POST})
    @ResponseBody
    public String saveChartModel(@RequestBody String request) {
        String temp[] = request.split("#");
        for (int i = 0; i < temp.length; i++) {
            String co[] = temp[i].split("_");
            String typeid[] = co[0].split(";");
            if (typeid[0].equals("acount")) {
                Acount a = acountDAO.getAcountById(Integer.parseInt(typeid[1]));
                a.setPosition(new Point(Integer.parseInt(co[1]), Integer.parseInt(co[2])));
                acountDAO.update(a);
                continue;
            }
            if (typeid[0].equals("role")) {
                BisnesRole br = bisnesRoleDAO.getBisnesRoleById(Integer.parseInt(typeid[1]));
                br.setPosition(new Point(Integer.parseInt(co[1]), Integer.parseInt(co[2])));
                bisnesRoleDAO.update(br);
                continue;
            }
            if (typeid[0].equals("doc")) {
                Document d = documentDAO.getDocumentById(Integer.parseInt(typeid[1]));
                d.setPosition(new Point(Integer.parseInt(co[1]), Integer.parseInt(co[2])));
                documentDAO.update(d);
                continue;
            }
        }
        return request;
    }
}
