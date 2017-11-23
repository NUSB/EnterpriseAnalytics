package com.segvek.analitic;

import com.segvek.analitic.dao.UserDAO;
import com.segvek.analitic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    UserDAO userMysqlDao;

    @RequestMapping(value = "/admin/user")
    public ModelAndView userList() {
        ModelAndView view = new ModelAndView("/admin/user/userList");
        view.addObject("users", userMysqlDao.getAllUser());
        return view;
    }

    @RequestMapping(value = "/admin/user/add", method = RequestMethod.GET)
    public ModelAndView userAddForm() {
        ModelAndView view = new ModelAndView("admin/user/user");
        view.addObject("title", "Новый пользователь");
        view.addObject("user", new User());
        view.addObject("sendTo", "add");
        return view;
    }

    @RequestMapping(value = "/admin/uesr/add", method = RequestMethod.POST)
    public ModelAndView acountAddAction( @ModelAttribute("user") User user,BindingResult result) {
        userMysqlDao.save(user);
        return userList();
    }
    
    

}
