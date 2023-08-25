package com.example.star_contractor.DTOS;

public class CommentDTO {
    private Integer jobId;
    private String content;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
