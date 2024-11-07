package com.warehouse.service;

import com.warehouse.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Iterable<Review> findAll();
    Optional<Review> findById(long id);
    Review save(Review review);
    int deleteById(long id);
    List<Review> findByArticleId(long id);
}
