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

    @Override
    public Review getReview(Long companyId, Long reviewId) {
        //firstly find the list of reviews for that company and then filter the list to find that review using reviewId
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews.stream().filter(review -> review.getId().equals(reviewId)).findFirst().orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review updatedReview) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        if(reviews != null){
            Review reviewToBeUpdated = reviews.stream().filter(review1 -> review1.getId().equals(reviewId)).findFirst().orElse(null);
            if(reviewToBeUpdated != null){
                reviewToBeUpdated.setTitle(updatedReview.getTitle());
                reviewToBeUpdated.setDescription(updatedReview.getDescription());
                reviewToBeUpdated.setRating(updatedReview.getRating());
                reviewRepository.save(reviewToBeUpdated);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        if(companyService.getCompanyById(companyId) != null && reviewRepository.existsById(reviewId)){

            Review review = reviewRepository.findById(reviewId).orElse(null);
            Company company = review.getCompany();
            company.getReviews().remove(review);
            review.setCompany(null);
            companyService.updateCompany(company, companyId);
            reviewRepository.deleteById(reviewId);
            return true;
        }
        else {
            return false;
        }
    }
}
