package com.anil.square.Controllers;

import java.lang.ProcessBuilder.Redirect;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


    public boolean authenticateProusers(String email, String password, HttpServletRequest request){
        Prousers prousers  = prouserRepo.findByEmail(email);
        if(prousers != null && passwordEncoder.matches(password, prousers.getPassword())){
            request.getSession().setAttribute("loggedInUser", prousers);
            return true;
        }
        return false;

    }


       @GetMapping("/autoauth/{email}/{password}")
    public String myloginredirect(HttpServletRequest request, @PathVariable(value = "email") String email,
            @PathVariable(value = "password") String password, RedirectAttributes attributes) {
      if(authenticateProusers(email, password, request)) {
        return "redirect:/dash";
      }
      attributes.addFlashAttribute("error", "Error AutoAuth");
      return "redirect:/";
    }

    @RequestMapping("/login")
    public String logins(@RequestParam String email, @RequestParam String password, HttpServletRequest request, Model model, RedirectAttributes attributes) {
       
      

        if(authenticateProusers(email, password, request)){
          
            // request.getSession().setAttribute("loggedInUser", prousers);
            return "redirect:/dash";
        }

          Prousers prousers = prouserRepo.findByEmail(email);

        attributes.addFlashAttribute("error", prousers == null ? "User not found ": "Invalid Password");
        return "redirect:/";

       
    }
    

    
}
