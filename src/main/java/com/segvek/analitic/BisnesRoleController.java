package com.segvek.analitic;

import com.segvek.analitic.dao.BisnesRoleDAO;
import com.segvek.analitic.model.BisnesRole;
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
public class BisnesRoleController {

    @Autowired
    BisnesRoleDAO bisnesRoleDAO;

    @RequestMapping(value = "/admin/bisnesRole", method = RequestMethod.GET)
    public ModelAndView bisnesRoleList() {
        ModelAndView view = new ModelAndView("admin/bisnesRole/bisnesRoleList");
        view.addObject("bisnesRoles", bisnesRoleDAO.getAllBisnesRole());
        return view;
    }

    @RequestMapping(value = "/admin/bisnesRole/add", method = RequestMethod.GET)
    public ModelAndView bisnesRoleAddForm() {
        ModelAndView view = new ModelAndView("admin/bisnesRole/bisnesRole");
        view.addObject("title", "Создать бизнес-роль");
        view.addObject("bisnesRole", new BisnesRole());
        view.addObject("bisnesRoles", bisnesRoleDAO.getAllBisnesRole());
        view.addObject("sendTo", "add");
        return view;
    }

    @RequestMapping(value = "/admin/bisnesRole/add", method = RequestMethod.POST)
    public ModelAndView bisnsRoleAddAction(WebRequest request, @ModelAttribute("bisnesRole") BisnesRole bisnesRole,
            BindingResult result, ModelMap model) {
        String parentName = request.getParameter("subordination");
        if (parentName != null && parentName.length() > 0) {
            BisnesRole parent = bisnesRoleDAO.getBisnesRoleByName(parentName);
            bisnesRole.setParent(parent);
        }
        bisnesRoleDAO.save(bisnesRole);
        return bisnesRoleList();
    }

    @RequestMapping(value = "/admin/bisnesRole/{id}", method = RequestMethod.GET)
    public String bisnesRoleView(Model model, @PathVariable Integer id) {

        BisnesRole bisnesRole = bisnesRoleDAO.getBisnesRoleById(id);
        if (bisnesRole == null) {
            return "error";
        }
        model.addAttribute("title", "Бизнес-роль № " + bisnesRole.getId());
        model.addAttribute("bisnesRole", bisnesRole);
        model.addAttribute("bisnesRoles", bisnesRoleDAO.getAllBisnesRole());
        model.addAttribute("sendTo", "update/" + bisnesRole.getId());
        return "admin/bisnesRole/bisnesRole";
    }

    @RequestMapping(value = "/admin/bisnesRole/update/{id}", method = RequestMethod.POST)
    public ModelAndView bisnesRoleUpdateAction(WebRequest request, @ModelAttribute("bisnesRole") BisnesRole bisnesRole,
            BindingResult result, ModelMap model, @PathVariable Integer id) {
        String parentName = request.getParameter("subordination");
        if (parentName != null && parentName.length() > 0) {
            BisnesRole parent = bisnesRoleDAO.getBisnesRoleByName(parentName);
            bisnesRole.setParent(parent);
        }
        bisnesRole.setId(id);
        bisnesRoleDAO.update(bisnesRole);
        return bisnesRoleList();
    }
    
     @RequestMapping(value = "/admin/bisnesRole/delete/{id}", method = RequestMethod.GET)
    public ModelAndView bisnesRoleDeleteAction(@PathVariable Integer id) {
        bisnesRoleDAO.deleteBisnesRoleById(id);
        return bisnesRoleList();
    }
    

}
