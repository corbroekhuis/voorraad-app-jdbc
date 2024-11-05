package com.warehouse.dao.review;

import com.warehouse.model.Review;

import java.util.List;

public interface ReviewDAO {

    List<Review> getReviews( Long articleId);
}
