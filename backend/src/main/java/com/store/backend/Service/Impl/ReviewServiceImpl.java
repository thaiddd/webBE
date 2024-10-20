package com.store.backend.Service.Impl;

import com.store.backend.Entity.Review;
import com.store.backend.Repository.ReviewRepository;
import com.store.backend.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public boolean addReview(Review review) {
        try {
            reviewRepository.save(review);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Review> getByUserID(long id) {
        return reviewRepository.findByUser_Id(id);
    }

    @Override
    public List<Review> getByProductID(long id) {
        return reviewRepository.findByProduct_Id(id);
    }
}
