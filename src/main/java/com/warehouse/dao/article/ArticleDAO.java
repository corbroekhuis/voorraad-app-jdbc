package com.warehouse.dao.article;

import com.warehouse.model.Article;

import java.util.Optional;

public interface ArticleDAO {

    Iterable<Article> findAll();
    Optional<Article> findById(long id);
    Number create(Article article);
    int update(Article article);
    Optional<Article> findByEan(String ean);
    int deleteById(long id);
    Optional<Article> findByArticleNumber(String articleNumber);
}
