package com.anil.square.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.anil.square.Entities.model.Prousers;
import com.anil.square.Repository.ProuserRepo;




@Controller
public class MainController {

    @GetMapping("/")
    public String getMethodName() {
        return "Home";
    }


    @Autowired
    private ProuserRepo prouserRepo;

    @Autowired
    private PasswordEncoder  passwordEncoder;

    @PostMapping("/addusers")
    public String addusers(@ModelAttribute Prousers prousers, RedirectAttributes attributes, Model model) {

       if(!prousers.getPassword().equals(prousers.getConfirmpassword())){
        attributes.addFlashAttribute("error", "Password not matched");
        return "redirect:/";
       }

        Optional<Prousers> existingUser = prouserRepo.findByMobileno(prousers.getMobileno());
        if(existingUser.isPresent()){

            attributes.addFlashAttribute("error", "Mobile number already exit!");

            return "redirect:/";
        }

        prousers.setPassword(passwordEncoder.encode(prousers.getPassword()));
        prousers.setConfirmpassword(passwordEncoder.encode(prousers.getConfirmpassword()));
        prouserRepo.save(prousers);
        attributes.addFlashAttribute("success", "User Added Successfully");
        return "redirect:/";
    
       
    }

    
    
    
    
}
