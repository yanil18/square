package com.anil.square.Controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    public boolean authenticateProusers(String email, String password, HttpServletRequest request) {
        Prousers prousers = prouserRepo.findByEmail(email);
        if (prousers != null && passwordEncoder.matches(password, prousers.getPassword())) {
            request.getSession().setAttribute("loggedInUser", prousers);
            return true;
        }
        return false;

    }

    @GetMapping("/autoauth/{email}/{password}")
    public String myloginredirect(HttpServletRequest request, @PathVariable(value = "email") String email,
            @PathVariable(value = "password") String password, RedirectAttributes attributes) {
        if (authenticateProusers(email, password, request)) {
            return "redirect:/dash";
        }
        attributes.addFlashAttribute("error", "Error AutoAuth");
        return "redirect:/";
    }

    @GetMapping("/captcha")
    protected void captcha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Max-Age", 0);
        String captcha = generateCaptcha(5);
        int width = 160, height = 50;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setColor(new Color(255, 255, 255));
        graphics.fillRect(0, 0, width, height);
        java.awt.geom.AffineTransform shear = java.awt.geom.AffineTransform.getShearInstance(0.7, 0.0);
        graphics.setTransform(shear);
        for (int i = 0; i < 50; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            graphics.setColor(new Color(255, 0, 0));
            graphics.drawRect(x, y, 1, 1);
        }
        graphics.setColor(new Color(0, 0, 0));
        graphics.setFont(new Font("Arial", Font.PLAIN, 24));
        graphics.drawString(captcha, 10, 35);
        HttpSession session = request.getSession(true);
        session.setAttribute("captcha", captcha);
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, "jpeg", outputStream);
        outputStream.close();
    }

    private String generateCaptcha(int captchaLength) {
        String captcha = "abcdefghjmABCDEFGHJK234npqrstuvwxyz56789MNPQRSTUVWXYZ";
        StringBuffer captchaBuffer = new StringBuffer();
        Random random = new Random();
        while (captchaBuffer.length() < captchaLength) {
            int index = (int) (random.nextFloat() * captcha.length());
            captchaBuffer.append(captcha.substring(index, index + 1));
        }
        return captchaBuffer.toString();
    }

    @RequestMapping("/login")
    public String logins(@RequestParam String email, @RequestParam String password, @RequestParam String captcha,
            HttpServletRequest request, Model model, RedirectAttributes attributes) {

                //captcha bypass
        // try {
        //     HttpSession session = request.getSession();
        //     String sessionCaptcha = (String) session.getAttribute("captcha");

        //     // Validate CAPTCHA
        //     if (sessionCaptcha == null || !captcha.equalsIgnoreCase(sessionCaptcha)) {
        //         attributes.addFlashAttribute("error", "Invalid CAPTCHA");
        //         attributes.addFlashAttribute("email", email);
        //         return "redirect:/";
        //     }

            Prousers prousers = prouserRepo.findByEmail(email);

            if (authenticateProusers(email, password, request)) {
                request.getSession().setAttribute("loggedInUser", prousers);
                return "redirect:/dash";
            } else {
                attributes.addFlashAttribute("error", "Invalid Password");
                return "redirect:/";
            }
        // }

        // catch (Exception e) {
        //     e.printStackTrace(); // You might want to log this instead
        //     attributes.addFlashAttribute("error", "An error occurred, please try again later");
        //     return "redirect:/";
        // }

    }

}
