/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segvek.analitic.controller.info;

import com.segvek.analitic.dao.AcountDAO;
import com.segvek.analitic.model.Acount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/info/acount")
public class InfoAcountController {

    @Autowired
    AcountDAO acountDAO;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String acountView(Model model, @PathVariable Integer id) {

        Acount acount = acountDAO.getAcountById(id);
        if (acount == null) {
//            view.setViewName("error");
        }
        model.addAttribute("acount", acount);
        model.addAttribute("debet",acount.getDebetCorrespondences());
        model.addAttribute("credit",acount.getCreditCorrespondences());
        
        
        return "info/acount";
    }

}
