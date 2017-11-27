package com.segvek.analitic;

import com.segvek.analitic.dao.BisnesRoleDAO;
import com.segvek.analitic.dao.DocumentDAO;
import com.segvek.analitic.dao.ResponsibilityForDocumentsDAO;
import com.segvek.analitic.model.BisnesRole;
import com.segvek.analitic.model.Document;
import com.segvek.analitic.model.Message;
import com.segvek.analitic.model.ResponsibilityForDocuments;
import java.util.ArrayList;
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

@Controller
public class DocumentController {

    @Autowired
    DocumentDAO documentDAO;

    @Autowired
    BisnesRoleDAO bisnesRoleDAO;
    
    @Autowired
    ResponsibilityForDocumentsDAO responsibilityForDocumentsDAO;

    @RequestMapping(value = "/admin/document", method = RequestMethod.GET)
    public ModelAndView documentList() {
        ModelAndView view = new ModelAndView("admin/document/documentList");
        List<Document> documents = documentDAO.getAllDocument();

        view.addObject("documents", documents);
        return view;
    }

    @RequestMapping(value = "/admin/document/add", method = RequestMethod.GET)
    public ModelAndView documentAddForm() {
        ModelAndView view = new ModelAndView("admin/document/document");
        view.addObject("title", "Новый документ");
        view.addObject("isNewDocument", true);
        view.addObject("document", new Document());
        view.addObject("sendTo", "add");
        return view;
    }

    @RequestMapping(value = "/admin/document/add", method = RequestMethod.POST)
    public ModelAndView documentAddAction(WebRequest request, @ModelAttribute("document") Document document,
            BindingResult result, ModelMap model) {
        ModelAndView view = new ModelAndView("admin/document/document");
        documentDAO.save(document);
        view.addObject("title", "Документ № " + document.getId());

        List<Message> messages = new ArrayList<Message>();
        messages.add(new Message(Message.TYPE_WARNING, "Добавте списко ролей, которіе отвечают за єтот документ!!"));
        view.addObject("messages", messages);
        view.addObject("isNewDocument", false);
        model.addAttribute("bisnesRoles", bisnesRoleDAO.getAllBisnesRole());
        view.addObject("sendTo", "edit/" + document.getId());
        return view;
    }

    @RequestMapping(value = "/admin/document/{id}", method = RequestMethod.GET)
    public String documentView(Model model, @PathVariable Integer id) {

        Document document = documentDAO.getDocumentById(id);
        if (document == null) {
            return "error";
        }
        model.addAttribute("title", "Докуент № " + document.getId());
        model.addAttribute("isNewDocument", false);
        model.addAttribute("document", document);
        model.addAttribute("bisnesRoles", bisnesRoleDAO.getAllBisnesRole());
        model.addAttribute("sendTo", "update/" + document.getId());
        model.addAttribute("responsibilityForDocuments", new ResponsibilityForDocuments());
        return "admin/document/document";
    }

    @RequestMapping(value = "/admin/document/update/{id}", method = RequestMethod.POST)
    public ModelAndView acountUpdateAction(WebRequest request, @ModelAttribute("document") Document document,
            BindingResult result, ModelMap model, @PathVariable Integer id) {
        document.setId(id);
        documentDAO.update(document);
        return documentList();
    }

    @RequestMapping(value = "/admin/document/{docId}/responsibilityForDocuments/add", method = RequestMethod.POST)
    public ModelAndView addBisnesRoleToDocument(WebRequest request
            ,@ModelAttribute("responsibilityForDocuments") ResponsibilityForDocuments responsibilityForDocuments
            ,BindingResult result,@PathVariable Integer docId) {
        
        Document doc = documentDAO.getDocumentById(docId);
        responsibilityForDocuments.setDocument(doc);
        
        BisnesRole bisnesRole = bisnesRoleDAO.getBisnesRoleByName(request.getParameter("role"));
        responsibilityForDocuments.setBisnesRole(bisnesRole);
        
        responsibilityForDocumentsDAO.save(responsibilityForDocuments);
        
        ModelAndView model = new ModelAndView("admin/document/document");
        
        model.addObject("title", "Докуент № " + doc.getId());
        model.addObject("isNewDocument", false);
        model.addObject("document", doc);
        model.addObject("bisnesRoles", bisnesRoleDAO.getAllBisnesRole());
        model.addObject("sendTo", "update/" + doc.getId());
        model.addObject("responsibilityForDocuments", new ResponsibilityForDocuments());
        return model;
    }
//    
//    @RequestMapping(value = "/admin/document/{docId}/bisnesRole/update/{idRole}",method = RequestMethod.POST)
//    public ModelAndView updateBisnesRoleToDocument(){
//        
//        return null;
//    }
//    
//    @RequestMapping(value = "/admin/document/{docId}/bisnesRole/delete/{idRole}",method = RequestMethod.POST)
//    public ModelAndView deleteBisnesRoleToDocument(){
//        
//        return null;
//    }
}
