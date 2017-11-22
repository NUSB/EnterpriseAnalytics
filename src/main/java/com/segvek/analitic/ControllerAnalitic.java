package com.segvek.analitic;

import com.segvek.analitic.dao.mysql.AcountMysqlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerAnalitic {

    @Autowired
    AcountMysqlDao acountMysqlDao;
    
    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/admin")
    public ModelAndView admin() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Панель администратора");
        model.addObject("acounts", acountMysqlDao.getAllAcount());
        model.setViewName("admin");
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

    @RequestMapping(value = "/admin/userList")
    public String userList() {
        return "userList";
    }
}
