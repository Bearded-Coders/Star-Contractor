package com.example.star_contractor.Repostories;

import com.example.star_contractor.Models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String username);

    User getUserById(Long usersId);

    // Grab all the users with appliedJob Id's match the argument of jobId
    @Query("SELECT u FROM User u JOIN u.appliedJobs a WHERE a.id = :jobId")
    Page<User> findApplicantsByJobId(@Param("jobId") Integer jobId, Pageable pageable);

    List<User> findUsersByFriendsContaining(User id);
//    List<User> findUsersByApplicant();
}
