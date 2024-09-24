package com.warehouse.repository;

import com.warehouse.model.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArticleRepository extends CrudRepository<Article,Long> {
    Optional<Article> findByEan(String ean);
    
}
