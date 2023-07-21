package com.example.star_contractor.Controllers;

import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.Login;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userDao;

    public UserController(UserRepository userDao) {
        this.userDao = userDao;
    }


//    LOGIN
//    @GetMapping("/login")
//    public String login(Model model) {
//        try {
//            Login login = new Login();
//            model.addAttribute("login", login);
//            return "index/login";
//        } catch (Exception e) {
//            return "index/errors/exception"; // Exception occurred error page
//        }
//    }
//    @PostMapping("/login")
//    public String userLogin(@ModelAttribute String email, String password) {
//        try {
//                User existingUser = userDao.findByEmail(email);
//
//                // Verify the password using bcrypt
//                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//                if (passwordEncoder.matches(password, existingUser.getPassword())) {
//                    // TODO: Implement security logic here, If needed
//                    return "redirect:/"; // TODO: This is not redirecting to proper page
//                } else {
//                    // Passwords don't match, handle the error accordingly
//                    return "index/errors/invalid-password"; // TODO: Create this alert or page
//                }
//        } catch (Exception e) {
//            return "index/errors/exception"; // Exception occurred error page
//        }
//    }

//    REGISTER
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

    @PostMapping("/signup")
    public String newRegistration(@ModelAttribute User user, Model model) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(user.getPassword());

            // Set the hashed password to the user object
            user.setPassword(hashedPassword);

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

//    @DeleteMapping("/removeaccount/{id}")
//    public String removeAccount(@PathVariable Integer id) {
//        userDao.deleteById(Long.valueOf(id));
//        return "redirect:/deletesuccess"; // TODO: create this page or choose another
//    }

//    @PostMapping("/profile/delete")
//    public String deleteProfile() {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        user = userDao.getReferenceById((long) user.getId());
//
//        userDao.delete(user);
//
//        return "redirect:/";
//    }

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
