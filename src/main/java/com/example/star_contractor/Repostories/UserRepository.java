package com.example.star_contractor.Repostories;

import com.example.star_contractor.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);

    User getUserById(Long usersId);
//    List<User> findUsersByApplicant();
}
