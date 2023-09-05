package com.example.star_contractor.Services;

import com.example.star_contractor.DTOS.CreateJobDTO;
import com.example.star_contractor.DTOS.JobDetailsDTO;
import com.example.star_contractor.Models.*;
import com.example.star_contractor.Repostories.CategoriesRepository;
import com.example.star_contractor.Repostories.JobRepository;
import com.example.star_contractor.Repostories.JobResponseRepository;
import com.example.star_contractor.Repostories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobsService {
    private final CommentService commentService;
    private final CategoryService categoryService;
    private final UserRepository userDao;
    private final JobRepository jobDao;
    private final CategoriesRepository catDao;
    private final JobResponseRepository jobResDao;

    public JobsService(UserRepository userDao,
                       JobRepository jobDao,
                       CategoriesRepository catDao,
                       CommentService commentService,
                       CategoryService categoryService,
                       JobResponseRepository jobResDao) {
        this.jobDao = jobDao;
        this.catDao = catDao;
        this.userDao = userDao;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.jobResDao = jobResDao;
    }

    // Find all jobs
    public Page<Jobs> findAllJobs(Pageable pageable) {
        return jobDao.findAll(pageable);
    }

    // Find a job by id
    public Jobs findJobById(Integer id) throws Exception {
        return jobDao.getJobById(id);
    }

    // Job Details DTO, this returns the entire job with the specified parameters
    public JobDetailsDTO getJobDetails(Integer jobId, User currentUser) throws Exception {
        JobDetailsDTO jobDetails = new JobDetailsDTO(); // Create new JobDetailsDTO

        Jobs singleJob = this.findJobById(jobId); // Get the job by using the method in this service
        List<Categories> categories = categoryService.findCategoriesForJob(singleJob); // Get the categories tied to the job
        List<Comment> comments = commentService.findCommentsForJob(singleJob); // Get the commments tied to the job

        List<User> applicantsList = singleJob.getApplicantList(); // Get the applicant list
        List<User> acceptedList = singleJob.getAcceptedList(); // Get the accepted list
        List<User> firstFourApplicants = applicantsList.subList(0, Math.min(applicantsList.size(), 4)); // Get the first 4 applicants

        jobDetails.setSingleJob(singleJob); // Set Entire Job object
        jobDetails.setCategories(categories); // Set the Categories tied to the job
        jobDetails.setComments(comments); // Set the Comments tied to the job
        jobDetails.setUser(currentUser); // Set the logged in user for conditionals
        jobDetails.setApplicantsList(applicantsList); // Set the Applicants list tied to the job
        jobDetails.setAcceptedList(acceptedList); // Set the Accepted/Hired list tied to the job
        jobDetails.setFirstFourApplicants(firstFourApplicants); // Set th List of only the first 4 applicants tied to the job

        if(singleJob.getOutcome() != null) {
            this.setJobOutcome(singleJob.getId());
        }

        return jobDetails; // Return the entire DTO
    }

    // Find jobs by category tags
    public Page<Jobs> findJobsByCategory(Boolean illegal,
                                         Boolean mining,
                                         Boolean combat,
                                         Boolean salvage,
                                         Boolean trading,
                                         Boolean exploring,
                                         Boolean bountyHunting,
                                         Boolean delivery,
                                         Boolean pvp,
                                         Boolean pve,
                                         Boolean rolePlay,
                                         Pageable pageable) {
        return jobDao.findPaginatedJobsByCategoryTags(illegal, mining, combat, salvage, trading, exploring, bountyHunting, delivery, pvp, pve, rolePlay, pageable);
    }

    // Find jobs by the user typed filter and paginate it
    public Page<Jobs> findByDescription(String filter, Pageable pageable) {
        return jobDao.findByDescriptionContainingIgnoreCase(filter, pageable);
    }

    // Retrieve a specific user's list of job's
    public List<Jobs> getUsersJobs(User user) {
        return jobDao.findJobsByCreatorId(user);
    }

    // Check if the user has been accepted to the job
    public boolean jobContainsUser(Integer jobId, User user) throws Exception {
        Jobs ratedJob = jobDao.getJobById(jobId);
        User currentUser = userDao.getUserById(user.getId());

        return ratedJob.getAcceptedList().contains(currentUser);
    }

    // Create a Job
    public void createJob(CreateJobDTO createJobDTO, User user) {
        Jobs job = new Jobs();
        job.setCreatorId(user);
        job.setTitle(createJobDTO.getTitle());
        job.setDescription(createJobDTO.getDescription());
        job.setCreatedDate(LocalDateTime.now());
        job.setStartDate(createJobDTO.getStartDate());
        job.setJobStatus(createJobDTO.getJobStatus());
        job.setThreat(createJobDTO.getThreat());
        job.setPaymentPercent(createJobDTO.getPaymentPercent());
        job.setJobStatus(createJobDTO.getJobStatus());
        job.setCreatorId(user);
        job.setCreatorEmail(user.getEmail());
        job.setStartLocation(createJobDTO.getStartLocation());
        job.setDistance(createJobDTO.getDistance());

        jobDao.save(job);

        // Set the category fields
        Categories categories = new Categories();
        categories.setJobId(job);
        categories.setBounty_hunting(createJobDTO.isBounty_hunting());
        categories.setIllegal(createJobDTO.isIllegal());
        categories.setMining(createJobDTO.isMining());
        categories.setCombat(createJobDTO.isCombat());
        categories.setSalvage(createJobDTO.isSalvage());
        categories.setTrading(createJobDTO.isTrading());
        categories.setExploring(createJobDTO.isExploring());
        categories.setDelivery(createJobDTO.isDelivery());
        categories.setPvp(createJobDTO.isPvp());
        categories.setPve(createJobDTO.isPve());
        categories.setRolePlay(createJobDTO.isRolePlay());
        catDao.save(categories);
    }

    // Edit a Job
    public void editJob(Integer id, Jobs updatedJob) throws Exception {
        Jobs existingJob = jobDao.getJobById(id);

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

            if (existingJob.getOutcome() != null) {
                existingJob.setOutcome(updatedJob.getOutcome());
            }

            if(existingJob.getJobStatus().equals("")) {
                existingJob.setJobStatus("Active");
            }

            jobDao.save(existingJob); // Save the updated job
        }
    }

    // Complete Job
    public void completeJob(Jobs existingJob, Jobs completeJob, User user) throws Exception {
        this.setJobResponse(existingJob.getId(),user, completeJob.getOutcome());
        existingJob.setJobStatus(completeJob.getJobStatus()); // Set the status
        this.setJobOutcome(existingJob.getId());
        jobDao.save(existingJob); // Save it to the DB
    }

    // Delete Job
    public void deleteJob(Integer id) {
        jobDao.deleteById(id);
    }

    // Apply to job
    public void applyToJob(Jobs existingJob, User applicant) {
        existingJob.getApplicantList().add(applicant); // Add the applicant to the job
        applicant.getAppliedJobs().add(existingJob); // Add the job to the user's appliedJobs list

        // Save the updated entities in the repository
        jobDao.save(existingJob);
        userDao.save(applicant);
    }

    // Remove applicant from job
    public void removeApplicantFromJob(Integer id, Long usersId) throws Exception {
        Jobs existingJob = this.findJobById(id); // Retrieve the job
        User applicant = userDao.getUserById(usersId); // Retrieve the user

        // Remove from applicant list if not accepted yet
        if(existingJob.getApplicantList().contains(applicant)) {
            existingJob.getApplicantList().remove(applicant);
            applicant.getAppliedJobs().remove(existingJob);
        }

        // Remove from accepted list if they've been accepted
        if(existingJob.getAcceptedList().contains(applicant)) {
            existingJob.getAcceptedList().remove(applicant);
            applicant.getAcceptedJobs().remove(existingJob);
        }

        // Save the job once changes are made
        jobDao.save(existingJob);
    }

    // Accept applicant to job
    public void acceptApplicantToJob(Integer jobId, Long applicantId) throws Exception {
        User applicant = userDao.getUserById(applicantId);
        Jobs currentJob = jobDao.getJobById(jobId);

        currentJob.removeApplicant(applicant);
        currentJob.addAcceptedUser(applicant);
        applicant.getAppliedJobs().remove(currentJob);
        applicant.getAcceptedJobs().add(currentJob);

        jobDao.save(currentJob);
    }

    // Deny Applicant to job
    public void denyApplicantToJob(Integer jobId, Long applicantId) throws Exception {
        User applicant = userDao.getUserById(applicantId);
        Jobs currentJob = jobDao.getJobById(jobId);

        if(currentJob.getApplicantList().contains(applicant)){
            currentJob.removeApplicant(applicant);
            applicant.getAppliedJobs().remove(currentJob);
        }

        if(currentJob.getAcceptedList().contains(applicant)){
            currentJob.removeAcceptedUser(applicant);
            applicant.getAcceptedJobs().remove(currentJob);
        }

        jobDao.save(currentJob);
    }

    // Add jobResponse
    public void setJobResponse(Integer jobId, User user, Boolean response) throws Exception {
        Jobs job = jobDao.getJobById(jobId);

        JobResponse jobResponse = new JobResponse();
        jobResponse.setResponse(response);
        jobResponse.setJob(job);
        jobResponse.setUser(user);

        jobResDao.save(jobResponse);
    }

    public void setJobOutcome(Integer jobId) throws Exception {
        Jobs currentJob = jobDao.getJobById(jobId);
        List<JobResponse> jobResponses = jobResDao.findAllByJobId(jobId);
        List<JobResponse> trueResponses = new ArrayList<>();
        List<JobResponse> falseResponses = new ArrayList<>();

        for(JobResponse jobResponse: jobResponses) {
            if(jobResponse.getResponse()) {
                trueResponses.add(jobResponse);
            } else {
                falseResponses.add(jobResponse);
            }
        }

        currentJob.setOutcome(trueResponses.size() > falseResponses.size());
    }

    // Get and set the outcome of the job
//    public Boolean getAndSetJobOutcome(Integer jobId) throws Exception {
//        Jobs currentJob = this.findJobById(jobId);
//        List<JobResponse> jobResponses = jobResDao.findAllByJobId(jobId);
//        List<JobResponse> trueResponses = new ArrayList<>();
//        List<JobResponse> falseResponses = new ArrayList<>();
//
//        for(JobResponse jobResponse: jobResponses) {
//            if(jobResponse.getResponse()) {
//                trueResponses.add(jobResponse);
//            } else {
//                falseResponses.add(jobResponse);
//            }
//        }
//
//        currentJob.setOutcome(trueResponses.size() > falseResponses.size());
//
//        return currentJob.getOutcome();
//    }
}

