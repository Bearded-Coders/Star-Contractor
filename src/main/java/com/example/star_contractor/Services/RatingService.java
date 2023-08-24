package com.example.star_contractor.Services;

import com.example.star_contractor.Models.HostRating;
import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.HostRateRepository;
import com.example.star_contractor.Repostories.JobRepository;
import com.example.star_contractor.Repostories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingService {
    private final UserRepository userDao;
    private final HostRateRepository ratingDao;

    private final JobRepository jobDao;

    public RatingService(HostRateRepository ratingDao,
                         UserRepository userDao,
                         JobRepository jobDao) {
        this.ratingDao = ratingDao;
        this.userDao = userDao;
        this.jobDao = jobDao;
    }

    // Average Rating service
    public Short calculateHostRating(Long userId) {

        User user = userDao.getUserById(userId);

        if (user.getHostRatings().isEmpty()) {
            user.setAvgRating((short) 0); // Set avgRating to 0 if there are no ratings
            return 0;
        }

        short totalRating = 0;
        for (HostRating hostRating : user.getHostRatings()) {
            totalRating += hostRating.getRating()/user.getHostRatings().size();
        }

        return totalRating; // Return the calculated average rating
    }

    public void createHostRating(Long userId, Integer jobId, Short ratingValue) throws Exception {
            User ratingUser = userDao.getUserById(userId);;
            Jobs ratedJob = jobDao.getJobById(jobId);

            // Create the new rating
            HostRating hostRating = new HostRating();
            hostRating.setRating(ratingValue);
            hostRating.setRatingUser(ratingUser);
            hostRating.setRatedHost(ratedJob.getCreatorId());
            hostRating.setJobId(ratedJob);

            ratingDao.save(hostRating);
    }

    public boolean hasRated(Integer jobId, User user) throws Exception {
        Jobs ratedJob = jobDao.getJobById(jobId);
        User currentUser = userDao.getUserById(user.getId());

        List<String> ratedUsers = new ArrayList<>();
        for (HostRating rating : ratedJob.getRatingUsers()) {
            ratedUsers.add(rating.getRatingUser().getUsername());
        }

        if(ratedUsers.contains(currentUser.getUsername())) {
            System.out.println("User has already rated this job!!!");
            return true;
        }

        return false;
    }

}
