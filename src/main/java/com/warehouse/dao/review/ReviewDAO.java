package com.warehouse.dao.review;

import com.warehouse.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDAO {

    Iterable<Review> findAll();
    Optional<Review> findById(long id);
    Number create(Review article);
    int update(Review article);
    int deleteById(long id);
    List<Review> findByArticleId(long id);
}
