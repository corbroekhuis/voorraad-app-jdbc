package com.warehouse.service;

import com.warehouse.dao.review.ReviewDAO;
import com.warehouse.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService{

    ReviewDAO reviewDAO;

    @Autowired
    public ReviewServiceImpl(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }
    
    @Override
    public Iterable<Review> findAll() {

        List<Review> ReviewS = new ArrayList<>();
        Iterable<Review> reviews = reviewDAO.findAll();
        for( Review review: reviews){
            ReviewS.add((Review) ( review));
        }
        return ReviewS;

    }

    @Override
    public Optional<Review> findById(long id) {

        Optional<Review> review = reviewDAO.findById( id);
        return Optional.ofNullable((Review) review.get());
    }

    @Override
    public Review save(Review review) {

        if(review.getId() == null){
            // id is in review
            review = create( review);
        }else{
            int updated = reviewDAO.update( review);
            System.out.println( "Updated: " + updated + " rows");
        }
        
        return  review;
    }

    private Review create(Review review) {
        Number id = reviewDAO.create( review);
        System.out.println( "Created Review with id: " + id.toString());
        review.setId( id.longValue());
        return review;
    }

    @Override
    public int deleteById(long id) {
        return reviewDAO.deleteById( id);
    }

    @Override
    public List<Review> findByArticleId(long id) {

        List<Review> ReviewS = new ArrayList<>();
        List<Review> reviews = reviewDAO.findByArticleId( id);
        for( Review review: reviews){
            Review Review = (Review) ( review);
            ReviewS.add( Review);
        }
        return ReviewS;
    }
}
