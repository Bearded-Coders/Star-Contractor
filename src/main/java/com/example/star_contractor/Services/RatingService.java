package com.example.star_contractor.Services;

import com.example.star_contractor.Models.HostRating;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.HostRateRepository;
import com.example.star_contractor.Repostories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    private final UserRepository userDao;
    private final HostRateRepository ratingDao;

    public RatingService(HostRateRepository ratingDao, UserRepository userDao) {
        this.ratingDao = ratingDao;
        this.userDao = userDao;
    }

    // Average Rating service
    public Short calculateAverageRating(Long userId) {

        User user = userDao.getUserById(userId);

        if (user.getHostRatings().isEmpty()) {
            user.setAvgRating((short) 0); // Set avgRating to 0 if there are no ratings
            return 0;
        }

        int totalRating = 0;
        for (HostRating hostRating : user.getHostRatings()) {
            totalRating += hostRating.getRating();
        }

        Short avgRating = (short) (totalRating/user.getHostRatings().size());

        user.setAvgRating(avgRating);

        return user.getAvgRating(); // Return the calculated average rating
    }

    public void createHostRating(HostRating hostRating) {
        // You can implement further validation and business logic here
          ratingDao.save(hostRating);
    }

}
