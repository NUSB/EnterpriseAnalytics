package com.segvek.analitic;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerAnalitic {
    
    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }
    
    @RequestMapping(value = "/admin")
    public String admin(){
        return "admin";
    }
    
    @RequestMapping(value = "/user")
    public String user(){
        return "user";
    }
    
     @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }
    
    @RequestMapping(value = "/admin/userList")
    public String userList(){
        return "userList";
    }
}
