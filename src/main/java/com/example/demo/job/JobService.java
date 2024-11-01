package com.example.demo.job;

import java.util.List;

public interface JobService {
    abstract List<Job> findAll();
    abstract void createJob(Job job);
    Job getJobById(Long id);
    boolean deleteJob(Long id);
    boolean updateJob(Long id, Job job);
}
