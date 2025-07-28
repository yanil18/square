package com.anil.square.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class MainController {

    @GetMapping("/")
    public String getMethodName() {
        return "Home";
    }
    
    
}
