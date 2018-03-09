package com.segvek.analitic.controller.admin;

import com.segvek.analitic.dao.UserDAO;
import com.segvek.analitic.model.User;
import com.segvek.analitic.model.UserRole;
import com.segvek.analitic.model.UserRolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller@RequestMapping(value = "/admin/user")
public class UserController {

    @Autowired
    UserDAO userDAO;

    @RequestMapping(value = "")
    public ModelAndView userList() {
        ModelAndView view = new ModelAndView("/admin/user/userList");
        view.addObject("users", userDAO.getAllUser());
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView userAddForm() {
        ModelAndView view = new ModelAndView("admin/user/user");
        view.addObject("title", "Новый пользователь");
        view.addObject("user", new User());
        view.addObject("sendTo", "add");
        return view;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView acountAddAction(WebRequest request,@ModelAttribute("user") User user, BindingResult result) {
        if (request.getParameter(UserRolesEnum.ROLE_SUBORDINATION) != null) {
            user.addRole(UserRolesEnum.ROLE_SUBORDINATION);
            user.addRole(UserRolesEnum.ROLE_USER);
        }
        if (request.getParameter(UserRolesEnum.ROLE_DOCUMENTS) != null) {
            user.addRole(UserRolesEnum.ROLE_DOCUMENTS);
        }
        if (request.getParameter(UserRolesEnum.ROLE_ACOUNTING) != null) {
            user.addRole(UserRolesEnum.ROLE_ACOUNTING);
        }
        if (request.getParameter(UserRolesEnum.ROLE_ADMIN) != null) {
            user.addRole(UserRolesEnum.ROLE_ADMIN);
        }
        userDAO.save(user);
        return userList();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ModelAndView viewUser(@PathVariable("username") String name) {
        ModelAndView view = new ModelAndView("admin/user/user");
        User user = userDAO.getUserByName(name);
        for (UserRole ur : user.getRoles()) {
            if (ur.getRole().equals(UserRolesEnum.ROLE_ADMIN)) {
                view.addObject("role_admin", ur.getRole());
            }
            if (ur.getRole().equals(UserRolesEnum.ROLE_ACOUNTING)) {
                view.addObject("role_acounting", ur.getRole());
            }
            if (ur.getRole().equals(UserRolesEnum.ROLE_DOCUMENTS)) {
                view.addObject("role_document", ur.getRole());
            }
            if (ur.getRole().equals(UserRolesEnum.ROLE_SUBORDINATION )
            || ur.getRole().equals(UserRolesEnum.ROLE_USER)) {
                view.addObject("role_user", ur.getRole());
            }
        }
        view.addObject("sendTo", user.getName());
        view.addObject("user", user);
        return view;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.POST)
    public ModelAndView updateUser(WebRequest request,
            @ModelAttribute("user") User user, BindingResult bindingResult,
            @PathVariable("username") String name) {
        if (request.getParameter(UserRolesEnum.ROLE_SUBORDINATION) != null) {
            user.addRole(UserRolesEnum.ROLE_SUBORDINATION);
            user.addRole(UserRolesEnum.ROLE_USER);
        }
        if (request.getParameter(UserRolesEnum.ROLE_DOCUMENTS) != null) {
            user.addRole(UserRolesEnum.ROLE_DOCUMENTS);
        }
        if (request.getParameter(UserRolesEnum.ROLE_ACOUNTING) != null) {
            user.addRole(UserRolesEnum.ROLE_ACOUNTING);
        }
        if (request.getParameter(UserRolesEnum.ROLE_ADMIN) != null) {
            user.addRole(UserRolesEnum.ROLE_ADMIN);
        }
        userDAO.update(user);

        return userList();
    }

    @RequestMapping(value = "/delete/{username}",method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable("username") String name){
        User user = userDAO.getUserByName(name);
        userDAO.delete(user);
        return userList();
    }
}
