package com.warehouse.dao.review.mapper;

import com.warehouse.model.Review;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {

    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {

        Review review = new Review();

        review.setId( rs.getLong("id"));
        review.setArticleId( rs.getLong("artikel_id"));
        review.setText( rs.getString("tekst"));
        review.setStars( rs.getInt("sterren"));

        return review;
    }
}
