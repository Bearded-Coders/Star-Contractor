package com.example.star_contractor.Controllers;

import com.example.star_contractor.Models.Categories;
import com.example.star_contractor.Models.Comment;
import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.CategoriesRepository;
import com.example.star_contractor.Repostories.CommentRepository;
import com.example.star_contractor.Repostories.JobRepository;
import com.example.star_contractor.Repostories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@CrossOrigin(origins = "http://localhost:8082", maxAge = 3600)
public class JobController {

    private final JobRepository jobsRepository;
    private final CategoriesRepository catDao;
    private final UserRepository userDao;
    private final CommentRepository commentDao;

    User user = null;

//    private List<String> applicantsList = new ArrayList<>();
//
//    public List<String> getApplicantsList() {
//        return applicantsList;
//    }

    public JobController(JobRepository jobsRepository, CategoriesRepository catDao, UserRepository userDao, CommentRepository commentDao) {
        this.jobsRepository = jobsRepository;
        this.catDao = catDao;
        this.userDao = userDao;
        this.commentDao = commentDao;
    }


    // View all Jobs
    @GetMapping("/jobs")
    public String getAllJobs(
            @RequestParam(name = "illegal", required = false) Boolean illegal,
            @RequestParam(name = "mining", required = false) Boolean mining,
            @RequestParam(name = "combat", required = false) Boolean combat,
            @RequestParam(name = "salvage", required = false) Boolean salvage,
            @RequestParam(name = "trading", required = false) Boolean trading,
            @RequestParam(name = "exploring", required = false) Boolean exploring,
            @RequestParam(name = "bountyHunting", required = false) Boolean bountyHunting,
            @RequestParam(name = "delivery", required = false) Boolean delivery,
            @RequestParam(name = "pvp", required = false) Boolean pvp,
            @RequestParam(name = "pve", required = false) Boolean pve,
            @RequestParam(name = "rolePlay", required = false) Boolean rolePlay,
            Model model,
            Principal principal) {

        try {
            // Create a list to store categories for each job
            List<List<Categories>> categoriesList = new ArrayList<>();

            // Check if filters are present
            if(illegal != null || mining != null || combat != null || salvage != null || trading != null
            || exploring != null || bountyHunting != null || delivery != null || pve != null
            || pvp != null || rolePlay != null) {

                // Search jobs by provided categories
                List<Jobs> jobs = jobsRepository.findJobsByCategoryTags(
                        illegal, mining, combat, salvage, trading, exploring,
                        bountyHunting, delivery, pvp, pve, rolePlay);

                // Loop through the jobs to fetch categories for each job
                for (Jobs job : jobs) {
                    List<Categories> categories = catDao.findCategoriesByJobId(job);
                    categoriesList.add(categories);
                }

                if (principal != null) {
                    user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    model.addAttribute("user", user);
                }

                if (user != null) {
                    String userUrl = "/profile/" + user.getId();
                    model.addAttribute("userUrl", userUrl);
                }

                model.addAttribute("job", jobs);
                model.addAttribute("categoriesList", categoriesList);

            } else {

                // Otherwise we use findAll
                List<Jobs> jobs = jobsRepository.findAll();

                // Loop through the jobs to fetch categories for each job
                for (Jobs job : jobs) {
                    List<Categories> categories = catDao.findCategoriesByJobId(job);
                    categoriesList.add(categories);
                }

                if (principal != null) {
                    user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    model.addAttribute("user", user);
                }

                if (user != null) {
                    String userUrl = "/profile/" + user.getId();
                    model.addAttribute("userUrl", userUrl);
                }

                model.addAttribute("job", jobs);
                model.addAttribute("categoriesList", categoriesList);
            }

            return "index/jobposts";
        } catch (Exception e) {
            return "index/errors/exception"; // Exception occurred error page
        }
    }

    // Go to Job Details page
    @GetMapping("/jobs/{id}")
    public String getJob(@PathVariable Integer id, Model model) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jobs singleJob = jobsRepository.getJobById(id);

        List<Categories> categories = catDao.findCategoriesByJobId(singleJob);

        List<Comment> comments = commentDao.findCommentsByJob(singleJob); // Fix the method call to findCommentsByJob

        model.addAttribute("singleJob", singleJob);
        model.addAttribute("category", categories);
        model.addAttribute("user", user);
        model.addAttribute("comments", comments); // Pass the comments list to the model
        return "index/jobdetails";
    }

    // Goto Job Creation Form
    @GetMapping("/jobs/createjob")
    public String jobCreation(Model model, Principal principal) {
        try {
            Jobs job = new Jobs();
            Categories category = new Categories();

            if (principal != null) {
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                job.setCreatorId(user);
                model.addAttribute("user", user);
            }

            model.addAttribute("category", category);
            model.addAttribute("job", job);
            return "index/createjob";
        } catch (Exception e) {
            return "index/errors/exception"; // Exception occurred error page
        }
    }

    // Create a Job
    @PostMapping("/jobs/createjob")
    public String addJob(@ModelAttribute Jobs job, @ModelAttribute Categories categories) {
        try {
            job.setCreatedDate(LocalDateTime.now());

//            The current start date is a value of "String", we may need to change this later or verify that it is in the correct format in the future
            // Convert the date string to LocalDateTime using DateTimeFormatter
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDateTime startDate = LocalDateTime.parse(job.getStartDate(), formatter);
//            job.setStartDate(String.valueOf(startDate));

            categories.setJobId(job);
            catDao.save(categories);
            System.out.println(categories);

//             Save the job (which will have the associated category)
            job.setJobStatus("Active");
            jobsRepository.save(job);
            System.out.println(categories.getJobId());

            return "redirect:/jobs";
        } catch (Exception e) {
            System.out.println(e);
            return "index/errors/exception"; // Exception occurred error page
        }
    }


    // Display the job editor
    @GetMapping("/jobs/editjob/{id}")
    public String updateJob(@PathVariable Integer id, Model model, Principal principal) throws Exception {
        Jobs singleJob = jobsRepository.getJobById(id);

        if (principal != null) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", user);
        }

        model.addAttribute("singleJob", singleJob);
        return "index/editjob";
    }

    // Update a Job
    @PostMapping ("/jobs/editjob/{id}")
    public String updateJob(@PathVariable Integer id, @ModelAttribute Jobs updatedJob) throws Exception {
        try {
            Jobs existingJob = jobsRepository.getJobById(id);
            if (existingJob != null) {
                existingJob.setTitle(updatedJob.getTitle());
                existingJob.setDescription(updatedJob.getDescription());
                existingJob.setThreat(updatedJob.getThreat());
                existingJob.setPaymentPercent(updatedJob.getPaymentPercent());
                existingJob.setJobStatus(updatedJob.getJobStatus());
                existingJob.setStartLocation(updatedJob.getStartLocation());
                existingJob.setDistance(updatedJob.getDistance());
                existingJob.setCreatedDate(existingJob.getCreatedDate());
                existingJob.setStartDate(updatedJob.getStartDate());

                if(existingJob.getOutcome() != null) {
                    existingJob.setOutcome(updatedJob.getOutcome());
                }

                jobsRepository.save(existingJob); // Save the updated job
                return "redirect:/jobs" + id; // Redirect to the jobs page
            } else {
                return "index/errors/jobnotfound"; // Job not found error page
            }
        } catch (Exception e) {
            return "index/errors/exception"; // Exception occurred error page
        }
    }

    // Complete a Job
    @PostMapping("/jobs/complete/{id}")
    public String completeJob(@PathVariable Integer id, @ModelAttribute Jobs completeJob) {
        try {
            Jobs existingJob = jobsRepository.getJobById(id);
            if (existingJob != null) {
                existingJob.setJobStatus(completeJob.getJobStatus());
                existingJob.setOutcome(completeJob.getOutcome());

                jobsRepository.save(existingJob);

                System.out.println("************" + completeJob.getJobStatus() + "************");
                System.out.println("************" + completeJob.getOutcome() + "************");

                return "redirect:/jobs";

            } else {
                System.out.println(new Exception().toString());
                return String.valueOf(new Exception());
            }
        } catch (Exception e) {
            System.out.println(e);
            return e.toString();
        }
    }

    // Delete a job
    @PostMapping("/jobs/delete/{id}")
    public String deleteJob(@PathVariable Integer id) {
        try {
            jobsRepository.deleteById(id);
            System.out.println("************** Removed Job! **************");
            return "redirect:/jobs";
        } catch (Exception e) {
            System.out.println(e + "****** error deleting ******");
            return e.toString();
        }
    }

    // Apply for job
    @PostMapping("/jobs/apply/{id}")
    public String applyJob(@PathVariable Integer id, @RequestParam(name = "userId") Long usersId) throws Exception {
        Jobs existingJob = jobsRepository.getJobById(id);
        User applicant = userDao.getUserById(usersId);

        existingJob.getApplicantList().add(applicant); // Add the applicant to the job
        applicant.getAppliedJobs().add(existingJob); // Add the job to the user's appliedJobs list

        // Save the updated entities in the repository
        jobsRepository.save(existingJob);
        userDao.save(applicant);

        return "redirect:/jobs/" + id;
    }

    // Remove applicant from job
    @PostMapping("/jobs/remove/{id}")
    public String removeJob(@PathVariable Integer id, @RequestParam(name = "userIdRemove") Long usersId) throws Exception {

        Jobs existingJob = jobsRepository.getJobById(id);

        // Fetch the User object corresponding to the usersId
        User applicant = userDao.getUserById(usersId);

        // Add the single applicant to the list
        existingJob.getApplicantList().remove(applicant);

        // Save the updated job in the repository (not shown in your code, but you need to do this)
        jobsRepository.save(existingJob);

        return "redirect:/jobs/" + id;
    }
}
