package com.warehouse.controller;

import com.warehouse.model.Review;
import com.warehouse.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api")
public class ReviewController {

    ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // GET: http:/<port>/api/review
    @GetMapping( value = "/review", produces = "application/json")
    public ResponseEntity<Iterable<Review>> findAll(){

        Iterable<Review> review = reviewService.findAll();

        return ResponseEntity.ok(review);
    }

    // POST: http:/<port>/api/review
    @PostMapping( value = "/review", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Review> saveReview(@RequestBody Review Review){

        Review saved = reviewService.save( Review);

        return ResponseEntity.ok(saved);
    }

    // GET: http:/<port>/api/review/2
    @GetMapping( value = "/review/{id}", produces = "application/json")
    public ResponseEntity<Review> findById(@PathVariable long id){
        Optional<Review> review = reviewService.findById( id);

        if(review.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else {
            return ResponseEntity.ok(review.get());
        }
    }

    // DELETE: http:/<port>/api/review?ean=123456789
    @DeleteMapping( value="/review")
    public ResponseEntity<String> deleteById( @RequestParam long id){

        reviewService.deleteById( id);
        return ResponseEntity.ok("deleted");
    }
}
