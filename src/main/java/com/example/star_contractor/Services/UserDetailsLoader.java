package com.example.star_contractor.Services;

import com.example.star_contractor.Models.User;
import com.example.star_contractor.Models.UserWithRoles;
import com.example.star_contractor.Repostories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsLoader implements UserDetailsService {
    private final UserRepository users;

    public UserDetailsLoader(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check if the usernameOrEmail is an email
        if (username.contains("@")) {
            User user = users.findByEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with email: " + username);
            }
            return new UserWithRoles(user);
        } else {
            User user = users.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            return new UserWithRoles(user);
        }
    }
}
