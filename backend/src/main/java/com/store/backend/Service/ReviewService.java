package com.store.backend.Service;

import com.store.backend.Entity.Review;

import java.util.List;

public interface ReviewService {
    boolean addReview(Review review);
    List<Review> getByUserID(long id);
    List<Review> getByProductID(long id);
}
