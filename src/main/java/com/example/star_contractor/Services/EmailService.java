package com.example.star_contractor.Services;

import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("mailService")
public class EmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Value("${spring.mail.from}")
    private String from;

    public void prepareAndSend(Jobs job, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);

        // Check if the creatorId and email are not null before setting the "To" address
        User user = job.getCreatorId();
        String userEmail = job.getCreatorEmail();
//        String userEmailAgain = job.get;
        if (userEmail != null) {
            msg.setTo(userEmail);
        } else {
            System.err.println("CreatorId or email is null. Cannot send the email.");
            return; // Abort the email sending if creatorId or email is null
        }

        msg.setSubject(subject);
        msg.setText(body);

        try {
            this.emailSender.send(msg);
        } catch (MailException ex) {
            System.err.println("Error sending email: " + ex.getMessage());
        }
    }

    public void passwordRecovery(Jobs job, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);

        // Check if the creatorId and email are not null before setting the "To" address
        User user = job.getCreatorId();
        String userEmail = job.getCreatorEmail();
//        String userEmailAgain = job.get;
        if (userEmail != null) {
            msg.setTo(userEmail);
        } else {
            System.err.println("CreatorId or email is null. Cannot send the email.");
            return; // Abort the email sending if creatorId or email is null
        }

        msg.setSubject(subject);
        msg.setText(body);

        try {
            this.emailSender.send(msg);
        } catch (MailException ex) {
            System.err.println("Error sending email: " + ex.getMessage());
        }
    }

    public void emailVerify(Jobs job, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);

        // Check if the creatorId and email are not null before setting the "To" address
        User user = job.getCreatorId();
        String userEmail = job.getCreatorEmail();
//        String userEmailAgain = job.get;
        if (userEmail != null) {
            msg.setTo(userEmail);
        } else {
            System.err.println("CreatorId or email is null. Cannot send the email.");
            return; // Abort the email sending if creatorId or email is null
        }

        msg.setSubject(subject);
        msg.setText(body);

        try {
            this.emailSender.send(msg);
        } catch (MailException ex) {
            System.err.println("Error sending email: " + ex.getMessage());
        }
    }
}
