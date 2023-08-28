package com.example.star_contractor.Controllers;

import com.example.star_contractor.DTOS.CommentDTO;
import com.example.star_contractor.DTOS.CreateJobDTO;
import com.example.star_contractor.DTOS.JobDetailsDTO;
import com.example.star_contractor.Models.Categories;
import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:8082", maxAge = 3600)
public class JobController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private JobsService jobsService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;

    User user = null;

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
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model,
            Principal principal) {

        try {
            int pageSize = 10; // Number of jobs per page

            // Create a list to store categories for each job
            List<List<Categories>> categoriesList = new ArrayList<>();

            // Pagination
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<Jobs> jobs;

            // Check if filters are present
            if(illegal != null || mining != null || combat != null || salvage != null || trading != null
            || exploring != null || bountyHunting != null || delivery != null || pve != null
            || pvp != null || rolePlay != null) {

                jobs = jobsService.findJobsByCategory(illegal,mining,combat,salvage,trading,exploring,bountyHunting,delivery,pvp,pve,rolePlay,pageable);

                // Loop through the jobs to fetch categories using CategoryService
                for (Jobs job : jobs) {
                    List<Categories> categories = categoryService.findCategoriesForJob(job);
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

            } else {
                // Otherwise we use findAll
                 jobs = jobsService.findAllJobs(pageable);

                // Loop through the jobs to fetch categories using CategoryService
                for (Jobs job : jobs) {
                    List<Categories> categories = categoryService.findCategoriesForJob(job);
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
            }
            model.addAttribute("categoriesList", categoriesList);

            return "index/jobposts";
        } catch (Exception e) {
            return "index/errors/exception"; // Exception occurred error page
        }
    }

    // Jobs filter method
    @GetMapping("/jobs/filter")
    public String filterJobs(
            @RequestParam String filter, @RequestParam(name = "page", defaultValue = "0") int page,
            Model model,
            Principal principal) {
        try {
            int pageSize = 10; // Number of jobs per page
            Pageable pageable = PageRequest.of(page, pageSize);

            // Create a list to store categories for each job
            List<List<Categories>> categoriesList = new ArrayList<>();

            Page<Jobs> jobs = jobsService.findByDescription(filter, pageable);

            // Loop through the jobs to fetch categories using CategoryService
            for (Jobs job : jobs) {
                List<Categories> categories = categoryService.findCategoriesForJob(job);
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

            return "index/jobposts";
        } catch (Exception e) {
            return "index/errors/exception";
        }
    }

    // Go to Job Details page
    @GetMapping("/jobs/{id}")
        public String getJobDetails(@PathVariable Integer id, Model model) {
            try {
                User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User user = userService.getUserById(currentUser.getId());

                Jobs singleJob = jobsService.findJobById(id);
                JobDetailsDTO jobDetails = jobsService.getJobDetails(id, user);

                model.addAttribute("jobDetails", jobDetails);
                model.addAttribute("singleJob", singleJob);

                return "index/jobdetails";
            } catch (Exception e) {
                return "index/errors/exception";
            }
        }


    // Get a users jobs
    @GetMapping("/jobs/{id}/myjobs")
    public String getMyJobs(@PathVariable Integer id, Model model) throws Exception {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserById(currentUser.getId());

        List<Jobs> myJobs = jobsService.getUsersJobs(user);
//        List<Categories> categories = catDao.findCategoriesByJobId(myJobs);

        model.addAttribute("myJobs", myJobs);
//        model.addAttribute("category", categories);
        model.addAttribute("user", user);
        return "index/myjobs";
    }

    // Get the job applicants page
    @GetMapping("/jobs/{id}/applicants")
    public String getJobApplicants(@PathVariable Integer id, @RequestParam(name = "page", defaultValue = "0") int page, Model model) throws Exception {

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            // Pagination for the applicants list
            int pageSize = 10; // Only 10 applicants per page
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<User> applicantsPage = userService.getJobApplicants(id, pageable);

            // Keep this for the page title and to check if the user was the creator of the job
            Jobs currentJob = jobsService.findJobById(id);

            // Link for the user to navigate to their profile page
            String userUrl = "/profile/" + user.getId();

            model.addAttribute("userUrl", userUrl);
            model.addAttribute("singleJob", currentJob);
            model.addAttribute("user", user);
            model.addAttribute("applicantsPage", applicantsPage);

            return "index/applicants";
        } catch (Exception e) {
            return e.toString();
        }
    }

    // Get the hired contractors page
    @GetMapping("/jobs/{id}/accepted")
    public String getHiredContractors(@PathVariable Integer id, Model model) throws Exception {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Jobs currentJob = jobsService.findJobById(id);
            List<User> acceptedList = currentJob.getAcceptedList();

            String userUrl = "/profile/" + user.getId(); // Link to the users profile

            model.addAttribute("singleJob", currentJob);
            model.addAttribute("acceptedList", acceptedList);
            model.addAttribute("user", user);
            model.addAttribute("userUrl", userUrl);

            return "index/accepted";
        } catch (Exception e) {
            return e.toString();
        }
    }

//   ****************** JOB COMMENTS *****************
    // Comment on a job
    @PostMapping("/jobs/{id}/add-comment")
    public String addComment(@PathVariable Integer id, @ModelAttribute CommentDTO commentDTO) {
        try {
            commentDTO.setJobId(id); // Set the commentDTO's job id to the passed Integer
            commentService.addComment(commentDTO); // Use the comment service to add a comment
            return "redirect:/jobs/" + id; // Redirect to the original job
        } catch (Exception e) {
            return "index/errors/exception"; // Exception occurred error page
        }
    }

    // Delete a comment
    @PostMapping("/jobs/{jobId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long jobId, @PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId); // Use the comment service to delete comment
            return "redirect:/jobs/" + jobId; // Redirect to the original job
        } catch (Exception e) {
            return "index/errors/exception"; // Exception occurred error page
        }
    }

//   ******************** JOB CREATION/DELETION/EDIT/DELETE *****************
    // Goto Job Creation Form
    @GetMapping("/jobs/createjob")
    public String jobCreation(Model model, Principal principal) {
        try {
            if (principal != null) {
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                model.addAttribute("user", user);
                String userUrl = "/profile/" + user.getId();
                model.addAttribute("userUrl", userUrl);
            }

            model.addAttribute("createJobDTO", new CreateJobDTO());

            return "index/createjob";
        } catch (Exception e) {
            return "index/errors/exception"; // Exception occurred error page
        }
    }

    // Create a Job
    @PostMapping("/jobs/createjob")
    public String addJob(@ModelAttribute CreateJobDTO createJobDTO) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // Get the logged in user
            createJobDTO.setJobStatus("Active");
            jobsService.createJob(createJobDTO, user); // Use the service to create the job
//            String body = "your created a Job with name '" + job.getTitle() + "' and a description of '" + job.getDescription() + "'.";
//            This code will email the creator of a job.
//            emailService.prepareAndSend(job, "Job Creation", body);
            return "redirect:/jobs"; // Redirect to the job board
        } catch (Exception e) {
            e.printStackTrace();
            return "index/errors/exception";
        }
    }


    // Display the job editor
    @GetMapping("/jobs/editjob/{id}")
    public String updateJob(@PathVariable Integer id, Model model, Principal principal) throws Exception {
        Jobs singleJob = jobsService.findJobById(id);

        if (principal != null) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", user);
            String userUrl = "/profile/" + user.getId();
            model.addAttribute("userUrl", userUrl);
        }

        model.addAttribute("singleJob", singleJob);
        return "index/editjob";
    }

    // Update a Job
    @PostMapping ("/jobs/editjob/{id}")
    public String updateJob(@PathVariable Integer id, @ModelAttribute Jobs updatedJob) throws Exception {
        try {
                jobsService.editJob(id, updatedJob);

                return "redirect:/jobs/" + id; // Redirect to the jobs page
        } catch (Exception e) {
            return "index/errors/exception"; // Exception occurred error page
        }
    }

    // Complete a Job
    @PostMapping("/jobs/complete/{id}")
    public String completeJob(@PathVariable Integer id, @ModelAttribute Jobs completeJob) {
        try {
            Jobs existingJob = jobsService.findJobById(id);
            if (existingJob != null) {
                jobsService.completeJob(existingJob,completeJob);
                System.out.println("************" + completeJob.getJobStatus() + "************");
                System.out.println("************" + completeJob.getOutcome() + "************");
            }

            return "redirect:/jobs/" + id;
        } catch (Exception e) {
            System.out.println(e);
            return "index/errors/exception"; // Exception occurred error page
        }
    }

    // Delete a job
    @PostMapping("/jobs/delete/{id}")
    public String deleteJob(@PathVariable Integer id) {
        try {
            jobsService.deleteJob(id);
            return "redirect:/jobs";
        } catch (Exception e) {
            System.out.println(e + "****** error deleting ******");
            return e.toString();
        }
    }


//    ************** APPLY/LEAVE/ACCEPT/DENY APPLICANTS ***************
    // Apply for job
    @PostMapping("/jobs/apply/{id}")
    public String applyJob(@PathVariable Integer id, @RequestParam(name = "userId") Long usersId) throws Exception {
        try {
            Jobs existingJob = jobsService.findJobById(id);
            User applicant = userService.getUserById(usersId);

            jobsService.applyToJob(existingJob, applicant);

            return "redirect:/jobs/" + id;
        } catch (Exception e) {
            System.out.println(e.toString());
            return "index/errors/exception";
        }
    }

    // Remove applicant from job
    @Transactional
    @PostMapping("/jobs/remove/{id}")
    public String removeJob(@PathVariable Integer id, @RequestParam(name = "userIdRemove") Long usersId) throws Exception {
        try {
            jobsService.removeApplicantFromJob(id, usersId); // Use the job service to remove the user
            return "redirect:/jobs/" + id;
        } catch (Exception e) {
            System.out.println(e.toString());
            return "index/errors/exception";
        }
    }

    // Accept applicant
    @PostMapping("/jobs/{jobId}/accept/{applicantId}")
    public String acceptApplicant(@PathVariable Integer jobId, @PathVariable Long applicantId) {
        try {
            jobsService.acceptApplicantToJob(jobId, applicantId); // Use the job
            return "redirect:/jobs/" + jobId;
        } catch (Exception e) {
            return e.toString();
        }
    }

    // Deny applicant
    @PostMapping("/jobs/{jobId}/deny/{applicantId}")
    public String denyApplicant(@PathVariable Integer jobId, @PathVariable Long applicantId) {
        // We turned this into a multi functional route to remove applicants and accepted users
        try {
            jobsService.denyApplicantToJob(jobId, applicantId);
            return "redirect:/jobs/" + jobId;
        } catch (Exception e) {
            return e.toString();
        }
    }
}
