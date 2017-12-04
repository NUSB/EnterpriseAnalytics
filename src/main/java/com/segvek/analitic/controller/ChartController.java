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
import com.segvek.analitic.model.User;
import com.segvek.analitic.model.UserRole;
import com.segvek.analitic.model.UserRolesEnum;
import com.segvek.analitic.model.chart.ChartManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping(value = "/chart", produces = {"application/json; charset=UTF-8"})
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
        }

        return cm.toJson();
    }
}
