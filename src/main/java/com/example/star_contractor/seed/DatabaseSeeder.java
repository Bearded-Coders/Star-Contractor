package com.example.star_contractor.seed;

import com.example.star_contractor.Models.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//THIS DOES NOT WORK RIGHT NOW, Will check into it at a later date
@Component
public class DatabaseSeeder implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        try {
            // Create and persist User entities
            User user1 = new User();
            user1.setUsername("Penelope");
            user1.setPassword("password1");
            user1.setEmail("penelope@example.com");
            user1.setStartingArea("Grim Hex");

            User user2 = new User();
            user2.setUsername("Austin");
            user2.setPassword("password2");
            user2.setEmail("austin@example.com");
            user2.setStartingArea("Grim Hex");

            entityManager.persist(user1);
            entityManager.persist(user2);

            System.out.println("Seed data added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding seed data: " + e.getMessage());
        }
    }
}







