package com.example.star_contractor.Controllers;

import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.UserRepository;
import com.example.star_contractor.Services.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class UserController {

    private final UserRepository userDao;
    private final EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserController(UserRepository userDao, EmailService emailService) {
        this.userDao = userDao;
        this.emailService = emailService;
    }

//    REGISTER get route
    @GetMapping("/signup")
    public String register(Model model) {
        try {
            User user = new User();
            model.addAttribute("user", user);
            return "users/signup";
        } catch (Exception e) {
            return "index/errors/exception"; // Exception occurred error page
        }
    }


    @GetMapping("/resetpassword")
    public String passwordReset() {

        return "index/resetpassword";
    }

    @PostMapping("/resetpassword")
    public String sendEmail(@RequestParam("email") String emailReset) {



        String token = "testingToken";

        String body = "http://localhost:8080/resetpassword/" + token + "/" + emailReset;

        System.out.println("Body: " + body);
        System.out.println("emailReset" + emailReset);

        User usersEmails = userDao.findByEmail(emailReset);

        if (usersEmails != null) {
            emailService.passwordResetEmail(emailReset, "Password Recovery", body);
        } else {
            System.out.println("Email does not exist!");
        }


        return "redirect:/resetpassword";
    }
    @GetMapping("/resetpassword/{token}/{emailReset}")
    public String tokenSent(@PathVariable String token, @PathVariable String emailReset, Model model) {
        User user = userDao.findByEmail(emailReset);
        model.addAttribute("user", user);
        model.addAttribute("token", token);

        return "/index/resetpassword-token";
    }

    @PostMapping("/resetpassword/{token}/{emailReset}")
    public String newPasswordSubmit(
            @PathVariable String token,
            @PathVariable String emailReset,
            @RequestParam String password,
            Model model
    ) {
        User user = userDao.findByEmail(emailReset);

        model.addAttribute("user", user);
        model.addAttribute("token", token);

        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);

        userDao.save(user);

        return "redirect:/login";
    }






    // REGISTER user post route
    @PostMapping("/signup")
    public String newRegistration(@ModelAttribute User user, Model model) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(user.getPassword());

            // Set the hashed password to the user object
            user.setPassword(hashedPassword);

            user.setProfilePic("/assets/img/default_profile_pic.png");

            userDao.save(user);

            // Add user info to the page
            User returningUser = userDao.findByEmail(user.getEmail());
            model.addAttribute("user", returningUser);
            // Then redirect to the profile page
            return "redirect:/login";
        } catch (Exception e) {
            return "index/errors/exception"; // Exception occurred error page
        }
    }

    // Remove account
    @PostMapping("/profile/delete")
    public String deleteUser(HttpServletRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userToDelete = userDao.findById(user.getId()).orElse(null);

        if (userToDelete != null) {
            // Remove the user from all the jobs they applied to
            for (Jobs job : userToDelete.getAppliedJobs()) {
                job.getApplicantList().remove(userToDelete);
            }
            for (User friend : userToDelete.getFriendsList()) {
                friend.getFriendsList().remove(userToDelete);
            }
            logoutUser(request);
            userDao.delete(userToDelete);
        }

        return "redirect:/";
    }


//    LOGOUT
    private void logoutUser(HttpServletRequest request) {
        // Invalidate the session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Clear the authentication context
        SecurityContextHolder.clearContext();
    }

}
