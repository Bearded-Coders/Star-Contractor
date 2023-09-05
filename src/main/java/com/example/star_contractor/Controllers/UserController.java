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

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Controller
public class UserController {

    private final UserRepository userDao;
    private final EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private Map<String, Instant> tokenCreationTimes = new HashMap<>();
    private Map<String, Instant> emailVerifyTokenCreationTimes = new HashMap<>();


    public UserController(UserRepository userDao, EmailService emailService) {
        this.userDao = userDao;
        this.emailService = emailService;
    }


//    REGISTER get route


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

    @PostMapping("/resetpassword")
    public String sendEmail(@RequestParam("email") String emailReset) {

        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        random.nextBytes(tokenBytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);

        // Store the token creation time
        Instant tokenCreationTime = Instant.now();
        tokenCreationTimes.put(token, tokenCreationTime);


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
        // Check if the token is valid and not expired
        Instant tokenCreationTime = tokenCreationTimes.get(token);
        if (tokenCreationTime == null || Instant.now().isAfter(tokenCreationTime.plusSeconds(3600))) {
            System.out.println("Your token has expired!");
        } else {
            // Token is valid and not expired, proceed with the reset process
            User user = userDao.findByEmail(emailReset);
            model.addAttribute("user", user);
            model.addAttribute("token", token);

            // Remove the token entry from the map to mark it as used
            tokenCreationTimes.remove(token);
        }

        return "/index/resetpassword-token";
    }

    @PostMapping("/resetpassword/{token}/{emailReset}")
    public String newPasswordSubmit(@PathVariable String token, @PathVariable String emailReset, @RequestParam String password, Model model) {
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
    public String newRegistration(@ModelAttribute User user, Model model, @RequestParam("email") String email) {
        User userObject = userDao.findByEmail(email);
        try {

            SecureRandom random = new SecureRandom();
            byte[] tokenBytes = new byte[32];
            random.nextBytes(tokenBytes);
            String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);


            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(user.getPassword());



            // Set the hashed password to the user object
            user.setVerified(false);
            user.setAdmin(false);
            user.setPassword(hashedPassword);

            user.setProfilePic("/assets/img/default_profile_pic.png");

            userDao.save(user);
//            String body = "http://localhost:8080/emailverify/" + token + "/" + user.getUsername();
//            System.out.println(user.getEmail());
            // Add user info to the page
            User returningUser = userDao.findByEmail(user.getEmail());
            model.addAttribute("user", returningUser);
            // Generate unique email verification token
            String emailVerifyToken = generateUniqueToken();

            String emailVerifyLink = "http://localhost:8080/emailverify/" + emailVerifyToken + "/" + user.getUsername();

            // Send verification email
            emailService.emailVerify(user.getEmail(), "Verify Email", emailVerifyLink);

            return "redirect:/login";
        } catch (Exception e) {
            return "index/errors/exception"; // Exception occurred error page
        }
    }

    @GetMapping("/emailverify/{token}/{username}")
    public String emailVerify(@PathVariable String token, @PathVariable String username, Model model) {
        // Token is valid, proceed with verification
        User user = userDao.findByUsername(username);
        model.addAttribute("user", user);

        user.setVerified(true);
        userDao.save(user);

        return "users/login"; // Replace with the actual name of your confirmation page
    }

    // Utility method to generate unique tokens
    private String generateUniqueToken() {
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        random.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
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
