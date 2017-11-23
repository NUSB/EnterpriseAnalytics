package com.segvek.analitic;

import com.segvek.analitic.dao.AcountDAO;
import com.segvek.analitic.model.Acount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AcountController {

    @Autowired
    AcountDAO acountDAO;

    @RequestMapping(value = "/admin/acount", method = RequestMethod.GET)
    public ModelAndView acountList() {
        ModelAndView view = new ModelAndView("admin/acount/acountList");
        view.addObject("acounts", acountDAO.getAllAcount());
        return view;
    }

    @RequestMapping(value = "/admin/acount/add", method = RequestMethod.GET)
    public ModelAndView acountAddForm() {
        ModelAndView view = new ModelAndView("admin/acount/acount");
        view.addObject("title", "Создать счет");
        view.addObject("acount", new Acount());
        view.addObject("acounts", acountDAO.getAllAcount());
        return view;
    }

    @RequestMapping(value = "/admin/acount/add", method = RequestMethod.POST)
    public ModelAndView acountAddAction(WebRequest request,@ModelAttribute("acount") Acount acount,
            BindingResult result, ModelMap model) {
        String parentName = request.getParameter("parent");
        if(parentName!=null && parentName.length()>0){
            Acount parent =acountDAO.getAcountByName(parentName);
            acount.setParent(parent);
        }
        acountDAO.save(acount);
        return acountList();
    }
}
