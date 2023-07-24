package com.example.star_contractor.Controllers;

import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.JobRepository;
import com.example.star_contractor.Repostories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProfileController {

    private final UserRepository userDao;
    private final JobRepository jobDao;

//    @Value("${filestack.api.key}")
//    private String filestackapi;

    public ProfileController(UserRepository userDao, JobRepository jobDao) {
        this.userDao = userDao;
        this.jobDao = jobDao;
    }


    @GetMapping("/profile/{id}")
    public String getProfile(@PathVariable Long id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        long userId = user.getId();



        User userProfile = userDao.findById(id).orElse(null);
        List<Jobs> userJobs = jobDao.findJobsByCreatorId(userDao.getReferenceById(id));
        List<Jobs> appliedJobs = jobDao.findJobsByApplicantListContains(userDao.getReferenceById(id));

        model.addAttribute("userProfileLink", userProfile);
        model.addAttribute("myJobs", userJobs);
//        model.addAttribute("grabId", userId);
        model.addAttribute("user", user);
        model.addAttribute("appliedJobs", appliedJobs);
//        model.addAttribute("filestackapi", filestackapi);
        System.out.println(userProfile.getFriendsList().contains(user));





        return "index/profile";
    }

    @GetMapping("/profile/edit/{userId}")
    public String showEditProfile(@PathVariable("userId") long userId, Model model) {
        // Fetch the user from the database based on the provided user ID
        User user = userDao.getReferenceById(userId);

        // Add the user to the model
        model.addAttribute("user", user);

        return "index/editprofile";
    }

    @PostMapping("/profile/add/{id}")
    public String addFriend(@PathVariable Long id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Find the current user and the user to be added as a friend
        User currentUser = userDao.findById(id).orElse(null);
        User friendToAdd = userDao.findById(user.getId()).orElse(null); // Replace someOtherId with the friend's ID you want to add

        // Check if both users exist and are valid
        if (currentUser == null || friendToAdd == null) {
            // Handle the case when user(s) are not found
            return "redirect:/error"; // Redirect to an error page or handle it as per your application's requirements
        }
        model.addAttribute("friendToAdd", friendToAdd);
        model.addAttribute("currentUser", currentUser);
        // Add the users to each other's friend lists
        currentUser.getFriendsList().add(friendToAdd);
        friendToAdd.getFriendsList().add(currentUser);

        // Save the changes to the database
        userDao.save(currentUser);
        userDao.save(friendToAdd);

        // Redirect to the user's profile page

        return "redirect:/profile/" + id;
    }
    @PostMapping("/profile/remove/{id}")
    public String removeFriend(@PathVariable Long id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Find the current user and the user to be added as a friend
        User currentUser = userDao.findById(id).orElse(null);
        User friendToRemove = userDao.findById(user.getId()).orElse(null); // Replace someOtherId with the friend's ID you want to add

        // Check if both users exist and are valid
        if (currentUser == null || friendToRemove == null) {
            // Handle the case when user(s) are not found
            return "redirect:/error"; // Redirect to an error page or handle it as per your application's requirements
        }
        model.addAttribute("friendToAdd", friendToRemove);
        model.addAttribute("currentUser", currentUser);
        // Add the users to each other's friend lists
        currentUser.getFriendsList().remove(friendToRemove);
        friendToRemove.getFriendsList().remove(currentUser);

        // Save the changes to the database
        userDao.save(currentUser);
        userDao.save(friendToRemove);

        // Redirect to the user's profile page

        return "redirect:/profile/" + id;
    }

//    @PostMapping("/profile/edit")
//    public String editProfile(@RequestParam("newUsername") String newUsername, @RequestParam("newEmail") String newEmail, @RequestParam("newHome") String newHome) {
//
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        user = userDao.getReferenceById((long) user.getId());
//
//
//
//        user.setUsername(newUsername);
//        user.setEmail(newEmail);
//        user.setStartingArea(newHome);
//
//        // Save the updated user to the database or perform any desired actions
//        userDao.save(user);
//
//        // Add a success message or any other necessary information to the model
////        model.addAttribute("message", "Profile updated successfully!");
//
//        // Redirect to the profile page or return a view
//        return "redirect:/profile/" + user.getId();
//
//    }


    @PostMapping("/profile/edits/{userId}")
    public String editProfile(@PathVariable("userId") long userId, @ModelAttribute("user") User updatedUser, Model model) {
        User user = userDao.getReferenceById(userId);

        // Update the user object with the new values
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setStartingArea(updatedUser.getStartingArea());

        // Save the updated user to the database or perform any desired actions
        userDao.save(user);

        // Redirect to the profile page
        return "redirect:/profile/" + userId;
    }

    @PostMapping("profile/upload")
    public String uploadProfile(@RequestParam(name = "stashFilestackURL") String uploadedProfilePic, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        user = userDao.getReferenceById((long) user.getId());


        user = userDao.getReferenceById((long) user.getId());
        user.setProfilePic(uploadedProfilePic);
        userDao.save(user);


        return "redirect:/profile/" + user.getId();
    }
}



//To DO
//Add delete mapping for User delete
//Add rating functionality and way(s) for users to input it

