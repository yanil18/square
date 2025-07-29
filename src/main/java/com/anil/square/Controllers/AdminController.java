package com.anil.square.Controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.anil.square.Entities.model.Prousers;




@Controller
public class AdminController {
    

  
   @RequestMapping ("/dash")
    public String dashboard(HttpServletRequest request, Model model) {

        Prousers loggedInUser = (Prousers) request.getSession().getAttribute("loggedInUser");
        if(loggedInUser == null){
            return "redirect:/";
        }
        model.addAttribute("user", loggedInUser);
        return "admin";
    }
    
}
