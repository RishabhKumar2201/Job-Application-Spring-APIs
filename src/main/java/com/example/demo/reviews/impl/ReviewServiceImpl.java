package com.example.demo.reviews.impl;

import com.example.demo.company.Company;
import com.example.demo.company.CompanyService;
import com.example.demo.reviews.Review;
import com.example.demo.reviews.ReviewRepository;
import com.example.demo.reviews.ReviewService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService){
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId); //this automatically
        // creates a method in ReviewRepository interface where it will search for the company with
        // "companyId" and then in that searched company, it will return all the reviews related to that company
        return  reviews;
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
//        reviewRepository.findByCompanyId(companyId).add(review); // can be done this way also
        Company company = companyService.getCompanyById(companyId);
        if(company != null){
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        }
        else{
            return false;
        }
    }
}
