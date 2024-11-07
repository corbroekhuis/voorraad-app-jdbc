package com.warehouse.dao.review;

import com.warehouse.dao.review.mapper.ReviewRowMapper;
import com.warehouse.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ReviewJDBCTemplate implements ReviewDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ReviewJDBCTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Iterable<Review> findAll() {
        String selectQuery = "SELECT * FROM Reviews";
        return jdbcTemplate.query(selectQuery, new ReviewRowMapper());
    }

    @Override
    public Optional<Review> findById(long id) {

        String sql = "SELECT * FROM reviews WHERE id=?";

        Review review = jdbcTemplate.queryForObject( sql, new ReviewRowMapper(), id);
        return Optional.ofNullable( review);
    }

    @Override
    public Number create(Review review) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reviews")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("artikel_id", review.getArticleId());
        parameters.put("tekst", review.getText());
        parameters.put("sterren", review.getStars());

        Number id = jdbcInsert.executeAndReturnKey(parameters);
        return id;
    }

    @Override
    public int update(Review review) {

        String updateQuery = """
                UPDATE reviews SET 
                artikel_id = ?, 
                tekst = ?, 
                sterren = ? 
                WHERE id = ?
                """;

        Object[] values = {
                review.getArticleId(),
                review.getText(),
                review.getStars(),
                review.getId()
        };

        return jdbcTemplate.update(updateQuery, values);
    }

    @Override
    public int deleteById(long id) {
        String deleteQuery = "DELETE FROM reviews WHERE id = ?";
        return jdbcTemplate.update(deleteQuery, id);
    }

    @Override
    public List<Review> findByArticleId(long id) {

        String sql = "SELECT * FROM reviews WHERE artikel_id=?";

        List<Review> reviews = jdbcTemplate.query( sql, new ReviewRowMapper(), id);
        return reviews;
    }
}
