package com.warehouse.dao.article;

import com.warehouse.dao.article.mapper.ArticleRowMapper;
import com.warehouse.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ArticleJDBCTemplate implements ArticleDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ArticleJDBCTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Iterable<Article> findAll() {
        String selectQuery = "SELECT * FROM artikelen";
        return jdbcTemplate.query(selectQuery, new ArticleRowMapper());
    }

    @Override
    public Optional<Article> findById(long id) {

        String sql = "SELECT * FROM artikelen WHERE id=?";

        try {
            Article article = jdbcTemplate.queryForObject( sql, new ArticleRowMapper(), id);
            return Optional.ofNullable( article);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public Number create(Article article) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("Artikelen")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("naam", article.getName());
        parameters.put("omschrijving", article.getDescription());
        parameters.put("ean", article.getEan());
        parameters.put("artikel_nummer", article.getArticleNumber());
        parameters.put("voorraad", article.getStock());
        parameters.put("minimum_voorraad", article.getMinimumStock());

        Number id = jdbcInsert.executeAndReturnKey(parameters);
        return id;
    }

    @Override
    public int update(Article article) {

        String updateQuery = """
                UPDATE Artikelen SET 
                naam = ?, 
                omschrijving = ?, 
                ean = ?, 
                artikel_nummer = ?, 
                voorraad = ?, 
                minimum_voorraad = ? 
                WHERE id = ?
                """;

        Object[] values = {
                article.getName(),
                article.getDescription(),
                article.getEan(),
                article.getArticleNumber(),
                article.getStock(),
                article.getMinimumStock(),
                article.getId()
        };

        return jdbcTemplate.update(updateQuery, values);
    }

    @Override
    public Optional<Article> findByEan(String ean) {

        String sql = "SELECT * FROM artikelen WHERE ean=?";

        try {
            Article article = jdbcTemplate.queryForObject( sql, new ArticleRowMapper(), ean);
            return Optional.ofNullable( article);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int deleteById(long id) {
        String deleteQuery = "DELETE FROM artikelen WHERE id = ?";
        return jdbcTemplate.update(deleteQuery, id);
    }

    @Override
    public Optional<Article> findByArticleNumber(String articleNumber) {
        String sql = "SELECT * FROM artikelen WHERE artikel_nummer=?";

        Article article = jdbcTemplate.queryForObject( sql, new ArticleRowMapper(), articleNumber);
        return Optional.ofNullable( article);
    }
}
