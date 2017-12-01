package com.segvek.analitic.controller.admin;

import com.segvek.analitic.dao.BisnesRoleDAO;
import com.segvek.analitic.model.BisnesRole;
import com.segvek.analitic.model.Document;
import com.segvek.analitic.model.Message;
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

@Controller
@RequestMapping(value = "/admin/bisnesRole")
public class BisnesRoleController {

    @Autowired
    BisnesRoleDAO bisnesRoleDAO;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView bisnesRoleList() {
        ModelAndView view = new ModelAndView("admin/bisnesRole/bisnesRoleList");
        view.addObject("bisnesRoles", bisnesRoleDAO.getAllBisnesRole());
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView bisnesRoleAddForm() {
        ModelAndView view = new ModelAndView("admin/bisnesRole/bisnesRole");
        view.addObject("title", "Создать бизнес-роль");
        view.addObject("bisnesRole", new BisnesRole());
        view.addObject("allbisnesRoles", bisnesRoleDAO.getAllBisnesRole());
        view.addObject("sendTo", "add");
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView bisnsRoleAddAction(WebRequest request, @ModelAttribute("bisnesRole") BisnesRole bisnesRole,
            BindingResult result, ModelMap model) {
        String parentName = request.getParameter("subordination1");
        if (parentName != null && parentName.length() > 0) {
            BisnesRole parent = bisnesRoleDAO.getBisnesRoleByName(parentName);
            bisnesRole.setParent(parent);
        }
        bisnesRoleDAO.save(bisnesRole);
        return bisnesRoleList();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String bisnesRoleView(Model model, @PathVariable Integer id) {

        BisnesRole bisnesRole = bisnesRoleDAO.getBisnesRoleById(id);
        if (bisnesRole == null) {
            return "error";
        }
        model.addAttribute("title", "Бизнес-роль № " + bisnesRole.getId());
        model.addAttribute("bisnesRole", bisnesRole);
        model.addAttribute("allbisnesRoles", bisnesRoleDAO.getAllBisnesRole());
        model.addAttribute("sendTo", "update/" + bisnesRole.getId());
        return "admin/bisnesRole/bisnesRole";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView bisnesRoleUpdateAction(WebRequest request, @ModelAttribute("bisnesRole") BisnesRole bisnesRole,
            BindingResult result, ModelMap model, @PathVariable Integer id) {
        String parentName = request.getParameter("subordination1");
        if (parentName != null && parentName.length() > 0) {
            BisnesRole parent = bisnesRoleDAO.getBisnesRoleByName(parentName);
            bisnesRole.setParent(parent);
        }
        bisnesRole.setId(id);
        bisnesRoleDAO.update(bisnesRole);
        return bisnesRoleList();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView bisnesRoleDeleteAction(WebRequest request, @PathVariable Integer id) {
        ModelAndView view = new ModelAndView("admin/bisnesRole/bisnesRoleList");
        BisnesRole bisnesRole = bisnesRoleDAO.getBisnesRoleById(id);
        List<Message> messages = deleteValidation(bisnesRole, request);
        if (messages.isEmpty()) {
            bisnesRoleDAO.deleteBisnesRole(bisnesRole);
        } else {
            view.addObject("messages", messages);
        }
        view.addObject("bisnesRoles", bisnesRoleDAO.getAllBisnesRole());
        return view;
    }

    private List<Message> deleteValidation(BisnesRole bisnesRole, WebRequest request) {
        List<Message> messages = new LinkedList<Message>();

        List<Document> documents = bisnesRole.getDocuments();
        if (!documents.isEmpty()) {
            StringBuilder sb = new StringBuilder("На данную роль ссылаются слудующие документы: ");
            for (Document d : documents) {
                sb.append("<br>").append("<a href=\"")
                        .append(request.getContextPath())
                        .append("/admin/document/")
                        .append(d.getId()).append("\">").append(d.getName()).append("</a>");
            }
            messages.add(new Message(Message.TYPE_ERROR, sb.toString()));
        }
        List<BisnesRole> children = bisnesRole.getChildren();
        if (!children.isEmpty()) {
            StringBuilder sb = new StringBuilder("Даня роль имеет в подчинении следующие бизнес сущности: ");
            for (BisnesRole br : children) {
                sb.append("<br>").append("<a href=\"")
                        .append(request.getContextPath())
                        .append("/admin/bisnesRole/")
                        .append(br.getId()).append("\">").append(br.getName()).append("</a>");
            }
            messages.add(new Message(Message.TYPE_ERROR, sb.toString()));
        }
        return messages;
    }

}
