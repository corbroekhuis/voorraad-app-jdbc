package com.warehouse.dao.review;

import com.warehouse.dao.article.mapper.ArticleRowMapper;
import com.warehouse.dao.review.mapper.ReviewRowMapper;
import com.warehouse.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReviewJDBCTemplate implements ReviewDAO{

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ReviewJDBCTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Review> getReviews(Long articleId) {
        String selectQuery = "SELECT * FROM reviews WHERE artikel_id = ?";
        return jdbcTemplate.query(selectQuery, new ReviewRowMapper(), articleId);
    }
}
