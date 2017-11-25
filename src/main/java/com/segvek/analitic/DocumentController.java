package com.segvek.analitic;

import com.segvek.analitic.dao.DocumentDAO;
import com.segvek.analitic.model.BisnesRole;
import com.segvek.analitic.model.Document;
import com.segvek.analitic.model.Message;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DocumentController {
    
    @Autowired
    DocumentDAO documentDAO;
    
    @RequestMapping(value = "/admin/document", method = RequestMethod.GET)
    public ModelAndView documentList() {
        ModelAndView view = new ModelAndView("admin/document/documentList");
        List<Message> messages = new ArrayList<Message>();
        messages.add(new Message("", "Сообщение которое не важно"));
        messages.add(new Message(Message.TYPE_WARNING, "Сообщение Очень очень важно"));
        view.addObject("messages", messages);
        List<Document> documents = documentDAO.getAllDocument();
        for(Document d:documents){
            StringBuilder sb = new StringBuilder(d.getName());
            for(BisnesRole br:d.getBisnesRoles().keySet()){
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
        List<Message> messages = new ArrayList<Message>();
        messages.add(new Message("", "Сообщение которое не важно"));
        messages.add(new Message(Message.TYPE_WARNING, "Сообщение Очень очень важно"));
        view.addObject("messages", messages);
        view.addObject("document", new Document());
        return view;
    }
    
    
}
