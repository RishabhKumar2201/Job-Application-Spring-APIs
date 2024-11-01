package com.example.demo.job.impl;

import com.example.demo.job.Job;
import com.example.demo.job.JobService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

//    private List<Job> jobs = new ArrayList<>();
    JpaRepository jobRepository;
    private Long nextId = 1L;

    public JobServiceImpl(JpaRepository jobRepository){
        this.jobRepository = jobRepository;
    }


    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public void createJob(Job job) {
        job.setId(nextId++);
        jobRepository.save(job);
    }

    public Job getJobById(Long id){
        return (Job)jobRepository.findById(id).orElse(null);
    }

    public boolean deleteJob(Long id){
        try {
            jobRepository.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean updateJob(Long id, Job updateJob){
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()){
            Job job = jobOptional.get();
            if(job.getId().equals(id)){
                job.setTitle(job.getTitle());
                job.setDescription(job.getDescription());
                job.setMaxSalary(job.getMaxSalary());
                job.setMinSalary(job.getMinSalary());
                job.setLocation(job.getLocation());
                jobRepository.save(job);
                return true;
            }
        }

        return false;
    }
}
