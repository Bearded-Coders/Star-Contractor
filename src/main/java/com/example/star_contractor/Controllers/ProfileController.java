package com.example.star_contractor.Controllers;

import com.example.star_contractor.Models.Friends;
import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.FriendRepository;
import com.example.star_contractor.Repostories.JobRepository;
import com.example.star_contractor.Repostories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
public class ProfileController {

    private final UserRepository userDao;
    private final JobRepository jobDao;
    private final FriendRepository friendDao;

    @Value("${filestack.api.key}")
    private String filestackapi;

    public ProfileController(UserRepository userDao, JobRepository jobDao, FriendRepository friendDao) {
        this.userDao = userDao;
        this.jobDao = jobDao;
        this.friendDao = friendDao;
    }

    // Get user profile
    @GetMapping("/profile/{id}")
    public String getProfile(@PathVariable Long id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User userProfile = userDao.findById(id).orElse(null);
        List<Jobs> userJobs = jobDao.findJobsByCreatorId(userDao.getReferenceById(id));
        List<Jobs> appliedJobs = jobDao.findJobsByApplicantListContains(userDao.getReferenceById(id));
        List<User> friends = user.getFriends();

        List<Friends> receivedFriendRequests = user.getReceivedFriendRequests();
        List<Friends> pendingFriendRequests = receivedFriendRequests.stream()
                .filter(request -> !request.getAccepted())
                .toList();



        model.addAttribute("userProfileLink", userProfile);
        model.addAttribute("myJobs", userJobs);
//        model.addAttribute("grabId", userId);
        model.addAttribute("user", user);
        model.addAttribute("appliedJobs", appliedJobs);
        model.addAttribute("filestackapi", filestackapi);
        model.addAttribute("friends", friends);
        model.addAttribute("pendingFriendRequests", pendingFriendRequests);
        System.out.println("************" + userProfile.getFriends() + "***********");

        if(user.getId().equals(id)) {
            // If the profile belongs to the user, we display the "profile" page
            return "index/profile";
        } else {
            // Otherwise we show the "user-profile" page
            return "index/user-profile";
        }
    }

    // Add a friend
    @PostMapping("/profile/add/{id}")
    public String addFriend(@PathVariable Long id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User targetUser = userDao.findById(id).orElse(null); // Find the user to be added as a friend

        if (currentUser != null && targetUser != null) {
            Friends friendRequest = new Friends();
            friendRequest.setSender(currentUser);
            friendRequest.setReceiver(targetUser);
            friendRequest.setRequested(true);
            friendRequest.setAccepted(false);
            friendRequest.setDenied(false);

            currentUser.getSentFriendRequests().add(friendRequest);
            targetUser.getReceivedFriendRequests().add(friendRequest);

            friendDao.save(friendRequest);
            userDao.save(currentUser);
            userDao.save(targetUser);
        }

        // Redirect to the user's profile page
        return "redirect:/profile/" + id;
    }

    // Remove a friend
    @PostMapping("/profile/remove/{id}")
    public String removeFriend(@PathVariable Long id, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User friendToRemove = userDao.findById(id).orElse(null); // Find the friend to be removed

        if (currentUser != null && friendToRemove != null) {
            // Remove the friend from each other's friend lists
            currentUser.getFriends().remove(friendToRemove);
            friendToRemove.getFriends().remove(currentUser);

            userDao.save(currentUser);
            userDao.save(friendToRemove);
        }

        // Redirect to the user's profile page
        return "redirect:/profile/" + id;
    }

    // Accept Friend Request
    @PostMapping("/profile/accept-friend-request/{requestId}")
    public String acceptFriendRequest(@PathVariable Long requestId, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Friends friendRequest = friendDao.findById(requestId).orElse(null);

        if (currentUser != null && friendRequest != null && friendRequest.getReceiver().equals(currentUser)) {
            friendRequest.setAccepted(true);

            User sender = friendRequest.getSender();
            User receiver = friendRequest.getReceiver();

            sender.getFriends().add(receiver);
            receiver.getFriends().add(sender);

            userDao.save(sender);
            userDao.save(receiver);
            friendDao.save(friendRequest);
        }

        // Redirect to the user's profile page
        return "redirect:/profile/" + currentUser.getId();
    }

    // Deny Friend request
    @PostMapping("/profile/deny-friend-request/{requestId}")
    public String denyFriendRequest(@PathVariable Long requestId, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Friends friendRequest = friendDao.findById(requestId).orElse(null);

        if (currentUser != null && friendRequest != null && friendRequest.getReceiver().equals(currentUser)) {
            friendRequest.setDenied(true);

            friendDao.save(friendRequest);
        }

        // Redirect to the user's profile page
        return "redirect:/profile/" + currentUser.getId();
    }

    // Get edit profile page
    @GetMapping("/profile/edit/{userId}")
    public String showEditProfile(@PathVariable("userId") long userId, Model model) {
        // Fetch the user from the database based on the provided user ID
        User user = userDao.getReferenceById(userId);

        // Add the user to the model
        model.addAttribute("user", user);

        return "index/editprofile";
    }

    // Submit the edit profile
    @PostMapping("/profile/edits/{userId}")
    public String editProfile(@PathVariable("userId") long userId, @ModelAttribute("user") User updatedUser, Model model) {
        User user = userDao.getReferenceById(userId);

        // Update the user object with the new values
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setStartingArea(updatedUser.getStartingArea());

        // Save the updated user to the database or perform any desired actions
        userDao.save(user);

        // Retrieve the UserDetails (UserPrincipal) from the existing authentication
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Check if the UserDetails instance is of the expected type (User class)
        if (userDetails instanceof User) {
            // Cast the UserDetails to your custom User class
            User authenticatedUser = (User) userDetails;
            // Update the authenticated user with the updated user information
            authenticatedUser.setUsername(user.getUsername());
            authenticatedUser.setEmail(user.getEmail());
            authenticatedUser.setStartingArea(user.getStartingArea());

            // Update the authentication context with the modified UserDetails (UserPrincipal)
            // Use an empty collection for authorities since you are not using them
            Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, authenticatedUser.getPassword(), Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Redirect to the profile page
        return "redirect:/profile/" + userId;
    }

    // Upload image to user profile
    @PostMapping("/profile/upload")
    public String uploadProfile(@RequestParam(name = "stashFilestackURL") String uploadedProfilePic) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user = userDao.getReferenceById((long) user.getId());
        user.setProfilePic(uploadedProfilePic);
        userDao.save(user);

        return "redirect:/profile/" + user.getId();
    }
}



//To DO
//Add delete mapping for User delete
//Add rating functionality and way(s) for users to input it


//    User currentUser = userDao.findById(id).orElse(null);
//    User friendToAdd = userDao.findById(user.getId()).orElse(null); // Replace someOtherId with the friend's ID you want to add
//
//// Check if both users exist and are valid
//        if (currentUser == null || friendToAdd == null) {
//                // Handle the case when user(s) are not found
//                return "redirect:/error"; // Redirect to an error page or handle it as per your application's requirements
//                }
//                model.addAttribute("friendToAdd", friendToAdd);
//                model.addAttribute("currentUser", currentUser);
//                // Add the users to each other's friend lists
//                currentUser.getFriendsList().add(friendToAdd);
//                friendToAdd.getFriendsList().add(currentUser);
//
//                // Save the changes to the database
//                userDao.save(currentUser);
//                userDao.save(friendToAdd);

