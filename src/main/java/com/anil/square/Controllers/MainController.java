package com.anil.square.Controllers;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
    
    String encryption_key = "CAg8AMIICCgKCAgEAv3SWf07W0jarHY59m";

    	@PostMapping("/keys")
	public ResponseEntity<Map<String, String>> getKeys() {
		HybridController hc = new HybridController();
		Map<String, String> keys = new HashMap<>();
		keys.put("HarshKey", Base64.getEncoder().encodeToString(encryption_key.getBytes(StandardCharsets.UTF_8)));
		keys.put("AbhiKey", Base64.getEncoder().encodeToString(hc.publicKeyPEM.getBytes(StandardCharsets.UTF_8)));
		return ResponseEntity.ok(keys);
	}


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
  
   @RequestMapping ("/test")
    public @ResponseBody String dashboard(HttpServletRequest request, Model model,@RequestParam String d) {

        		String decrStr = Hybriddecrypt(d.replaceAll(" ", "+"));

                System.out.println("decstr  "+decrStr);
        Prousers loggedInUser = (Prousers) request.getSession().getAttribute("loggedInUser");
        if(loggedInUser == null){
            return "redirect:/";
        }
        model.addAttribute("user", loggedInUser);
        return "admin";
    }

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
