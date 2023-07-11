package com.example.star_contractor.Controllers;

import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    private final UserRepository userDao;

    public ProfileController(UserRepository userDao) {
        this.userDao = userDao;
    }


    @GetMapping("/profile/{id}")
    public String getProfile(@PathVariable Long id, Model model) {
        User userProfile = userDao.findById(id).orElse(null);

        model.addAttribute("userProfileLink", userProfile);


        return "index/profile";
    }
}
