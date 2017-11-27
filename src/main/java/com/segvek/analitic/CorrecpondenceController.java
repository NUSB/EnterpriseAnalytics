/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segvek.analitic;

import com.segvek.analitic.dao.CorrespondenceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CorrecpondenceController {
    
    @Autowired
    CorrespondenceDAO correspondenceDAO;
    
    @RequestMapping(value = "/admin/correspondence")
    public ModelAndView userList() {
        ModelAndView view = new ModelAndView("/admin/correspondence/correspondenceList");
        view.addObject("correspondences", correspondenceDAO.getAllCorrespondences());
        return view;
    }
}
