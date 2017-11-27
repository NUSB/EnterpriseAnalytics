package com.segvek.analitic;

import com.segvek.analitic.dao.DocumentDAO;
import com.segvek.analitic.model.BisnesRole;
import com.segvek.analitic.model.Document;
import com.segvek.analitic.model.Message;
import java.util.ArrayList;
import java.util.List;
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
public class DocumentController {

    @Autowired
    DocumentDAO documentDAO;

    @RequestMapping(value = "/admin/document", method = RequestMethod.GET)
    public ModelAndView documentList() {
        ModelAndView view = new ModelAndView("admin/document/documentList");
        List<Document> documents = documentDAO.getAllDocument();
        for (Document d : documents) {
            StringBuilder sb = new StringBuilder(d.getName());
            for (BisnesRole br : d.getBisnesRoles().keySet()) {
                sb.append("   ");
                sb.append(br.getName());
                sb.append("   ");
                sb.append(br.getAnnotation());
            }
            d.setName(sb.toString());
        }

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
        view.addObject("title", "Документ № "+document.getId());
        
        List<Message> messages = new ArrayList<Message>();
        messages.add(new Message(Message.TYPE_WARNING, "Добавте списко ролей, которіе отвечают за єтот документ!!"));
        view.addObject("messages", messages);
        view.addObject("isNewDocument", false);
        view.addObject("sendTo", "edit/"+document.getId());
        return view;
    }

    
}
