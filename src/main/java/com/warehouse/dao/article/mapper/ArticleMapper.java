package com.warehouse.dao.article.mapper;

import com.warehouse.model.Article;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleMapper implements RowMapper<Article> {

    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Article article = new Article();
        article.setId(rs.getLong("id"));
        article.setName(rs.getString("naam"));
        article.setDescription(rs.getString("omschrijving"));
        article.setArticleNumber(rs.getString("artikel_nummer"));
        article.setEan(rs.getString("ean"));
        article.setStock(rs.getInt( "voorraad"));
        article.setMinimumStock(rs.getInt( "minimum_voorraad"));

        return article;
    }
}
