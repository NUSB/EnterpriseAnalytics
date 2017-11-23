
package com.segvek.analitic;

import com.segvek.analitic.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    
    @Autowired
    UserDAO userMysqlDao;
    
    @RequestMapping(value = "/admin/user")
    public ModelAndView userList(){
        ModelAndView view = new ModelAndView("/admin/user/userList");
        view.addObject("users", userMysqlDao.getAllUser());
        return view;
    }
    
    
}
