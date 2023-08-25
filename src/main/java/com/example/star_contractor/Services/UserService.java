package com.example.star_contractor.Services;

import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userDao;

    public UserService(UserRepository userDao) {
        this.userDao = userDao;
    }

    // Get user By Id
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }
}
