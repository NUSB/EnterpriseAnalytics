package com.segvek.analitic;

import com.segvek.analitic.dao.AcountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AcountController {
    
    @Autowired
    AcountDAO acountMysqlDao;
    
    @RequestMapping(value = "/admin/acount", method = RequestMethod.GET)
    public ModelAndView acountList(){
        ModelAndView view = new ModelAndView("admin/acount/acountList");
        view.addObject("acounts",acountMysqlDao.getAllAcount() );
        return view;
    }
}
