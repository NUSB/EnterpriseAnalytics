/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.segvek.analitic.controller.admin;

import com.segvek.analitic.dao.AcountDAO;
import com.segvek.analitic.dao.CorrespondenceDAO;
import com.segvek.analitic.dao.DocumentDAO;
import com.segvek.analitic.model.Acount;
import com.segvek.analitic.model.Correspondence;
import com.segvek.analitic.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin/correspondence")
public class CorrecpondenceController {

    @Autowired
    CorrespondenceDAO correspondenceDAO;

    @Autowired
    AcountDAO acountDAO;

    @Autowired
    DocumentDAO documentDAO;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView correspondenceList() {
        ModelAndView view = new ModelAndView("/admin/correspondence/correspondenceList");
        view.addObject("correspondences", correspondenceDAO.getAllCorrespondences());
        return view;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView correspondenceView(@PathVariable("id") Integer correspondenceId) {
        ModelAndView mav = new ModelAndView("/admin/correspondence/correspondence");
        mav.addObject("title", "Корреспонденция счетов");
        mav.addObject("acounts", acountDAO.getAllAcount());
        mav.addObject("documents", documentDAO.getAllDocument());
        mav.addObject("correspondence", correspondenceDAO.getCorrespondeceById(correspondenceId));
        mav.addObject("sendTo", "update/" + correspondenceId);
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView correspondenceAddForm() {
        ModelAndView mav = new ModelAndView("/admin/correspondence/correspondence");
        mav.addObject("title", "Новая корреспонденция счетов");
        mav.addObject("acounts", acountDAO.getAllAcount());
        mav.addObject("documents", documentDAO.getAllDocument());
        mav.addObject("correspondence", new Correspondence());
        return mav;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView correspondenceAddAction(WebRequest request) {
        Acount debet = acountDAO.getAcountByName(request.getParameter("debet"));
        Acount credit = acountDAO.getAcountByName(request.getParameter("credit"));
        Document document = documentDAO.getDocumentByName(request.getParameter("document"));
        String snnotation = request.getParameter("annotation");
        Correspondence correspondence = new Correspondence(debet, credit, document, snnotation);
        correspondenceDAO.save(correspondence);
        return correspondenceList();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteCorrespondence(@PathVariable("id") Integer correspondenceId) {
        correspondenceDAO.delete(correspondenceDAO.getCorrespondeceById(correspondenceId));
        return correspondenceList();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView updateCorrespondence(WebRequest request, @PathVariable("id") Integer correspondenceId) {
        Correspondence correspondence = correspondenceDAO.getCorrespondeceById(correspondenceId);
        correspondence.setDebet(acountDAO.getAcountByName(request.getParameter("debet")));
        correspondence.setCredit(acountDAO.getAcountByName(request.getParameter("credit")));
        correspondence.setDocument(documentDAO.getDocumentByName(request.getParameter("document")));
        correspondence.setAnnotation(request.getParameter("annotation"));
        correspondenceDAO.update(correspondence);
        return correspondenceList();
    }
}
