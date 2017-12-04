package com.segvek.analitic.controller;

import com.segvek.analitic.dao.BisnesRoleDAO;
import com.segvek.analitic.dao.CorrespondenceDAO;
import com.segvek.analitic.dao.DocumentDAO;
import com.segvek.analitic.dao.mysql.AcountMysqlDao;
import com.segvek.analitic.model.chart.ChartManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerAnalitic {

    @Autowired
    AcountMysqlDao acountDAO;
    
    @Autowired
    CorrespondenceDAO correspondenceDAO;
    @Autowired
    DocumentDAO documentDAO;
    @Autowired
    BisnesRoleDAO bisnesRoleDAO;
    
    @RequestMapping(value = "/", produces={"application/json; charset=UTF-8"})
    @ResponseBody
    public String index() {
        ChartManager cm = new ChartManager(correspondenceDAO, documentDAO, bisnesRoleDAO, acountDAO);
        
       
        return cm.toJson();
    }

    @RequestMapping(value = "/admin")
    public ModelAndView admin() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Панель администратора");
        return model;
    }

    @RequestMapping(value = "/user")
    public ModelAndView user() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Аналитика");
        model.setViewName("user");
        return model;
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

}
