package com.example.star_contractor.Controllers;

import com.example.star_contractor.Models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getModels(Model model, Principal principal) {

        User user = null;
        if (principal != null) {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", user);
        }

        if (user != null) {
            String userUrl = "/profile/" + user.getId();
            model.addAttribute("userUrl", userUrl);
            System.out.println("HOME: START: " + user.getStartingArea());
            System.out.println("USERNAME " + user.getUsername());
        }


        return "index/home";
    }
}