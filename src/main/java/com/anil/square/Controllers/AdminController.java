package com.anil.square.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class AdminController {
    

  
   @RequestMapping ("/dash")
    public String dashboard() {
        return "admin";
    }
    
}
