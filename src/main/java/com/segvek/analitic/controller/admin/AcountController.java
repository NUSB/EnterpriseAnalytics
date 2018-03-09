package com.segvek.analitic.controller.admin;

import com.segvek.analitic.dao.AcountDAO;
import com.segvek.analitic.model.Acount;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller@RequestMapping(value = "/admin/acount")
public class AcountController {
    
    @Autowired
    AcountDAO acountDAO;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView acountList() {
        ModelAndView view = new ModelAndView("admin/acount/acountList");
        view.addObject("acounts", acountDAO.getAllAcount());
        return view;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView acountAddForm() {
        ModelAndView view = new ModelAndView("admin/acount/acount");
        view.addObject("title", "Создать счет");
        view.addObject("acount", new Acount());
        List <Acount> l = new LinkedList<Acount>();
        for(Acount a:acountDAO.getAllAcount()){
            if(a.isGroup())
                l.add(a);
        }
        view.addObject("groupAcounts", l);
        view.addObject("sendTo","add");
        return view;
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView acountAddAction(WebRequest request, @ModelAttribute("acount") Acount acount,
            BindingResult result, ModelMap model) {
        String parentName = request.getParameter("parentGroup");
        if (parentName != null && parentName.length() > 0) {
            Acount parent = acountDAO.getAcountByName(parentName);
            acount.setParent(parent);
        }
        acountDAO.save(acount);
        return acountList();
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView acountDeleteAction(@PathVariable Integer id) {
        acountDAO.deleteAcountById(id);
        return acountList();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String acountView(Model model,@PathVariable Integer id) {
        
        Acount acount = acountDAO.getAcountById(id);
        if (acount == null) {
//            view.setViewName("error");
        }
        model.addAttribute("title", "Счет № "+acount.getCode());
        model.addAttribute("acount", acount);
        model.addAttribute("acounts", acountDAO.getAllAcount());
        model.addAttribute("sendTo","update/"+acount.getId());
        return "admin/acount/acount";
    }
    
    
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView acountUpdateAction(WebRequest request, @ModelAttribute("acount") Acount acount,
            BindingResult result, ModelMap model,@PathVariable Integer id) {
        String parentName = request.getParameter("parentGroup");
        if (parentName != null && parentName.length() > 0) {
            Acount parent = acountDAO.getAcountByName(parentName);
            acount.setParent(parent);
        }
        acount.setId(id);
        acountDAO.update(acount);
        return acountList();
    }
    
    
}
