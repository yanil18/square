package com.anil.square.Controllers;

import java.lang.ProcessBuilder.Redirect;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.anil.square.Entities.model.Prousers;
import com.anil.square.Repository.ProuserRepo;


@Controller
public class LoginController {


    @Autowired
    private ProuserRepo prouserRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/login")
    public String logins(@RequestParam String email, @RequestParam String password, HttpServletRequest request, Model model, RedirectAttributes attributes) {
       
        Prousers prousers = prouserRepo.findByEmail(email);

        if(prousers != null && passwordEncoder.matches(password, prousers.getPassword())){
          
            request.getSession().setAttribute("loggedInUser", prousers);
            return "redirect:/dash";
        }

        attributes.addFlashAttribute("error", prousers == null ? "User not found ": "Invalid Password");
        return "redirect:/";



     
       
    }
    

    
}
