package com.example.star_contractor.Controllers;

import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserRepository userDao;

    public UserController(UserRepository userDao) {
        this.userDao = userDao;
    }


//    REGISTER user get route
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
            for (User friend : userToDelete.getFriends()) {
                friend.getFriends().remove(userToDelete);
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
