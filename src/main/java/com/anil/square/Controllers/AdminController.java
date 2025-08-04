package com.anil.square.Controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anil.square.Entities.model.Prousers;




@Controller
public class AdminController {
    


	public static HybridController hybridController = new HybridController();

	public static String Hybriddecrypt(String strToDecrypt) {
		try {
			return hybridController.Hybrid_Data_Decryption(strToDecrypt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return null;
	}
  
   @RequestMapping("/dash")
    public String dashboard(HttpServletRequest request, Model model, String d) {

        		//String decrStr = Hybriddecrypt(d.replaceAll(" ", "+"));

        Prousers loggedInUser = (Prousers) request.getSession().getAttribute("loggedInUser");
        if(loggedInUser == null){
            return "redirect:/";
        }
        model.addAttribute("user", loggedInUser);
        return "admin";
    }
    
}
