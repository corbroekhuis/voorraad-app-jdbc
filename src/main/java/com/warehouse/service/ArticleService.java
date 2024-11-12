package com.warehouse.service;

import com.warehouse.model.Article;
import com.warehouse.model.client.ArticleSER;
import com.warehouse.model.dto.ArticleDTO;

import java.util.Optional;

public interface ArticleService {

    Iterable<ArticleDTO> findAllDTOS();
    ArticleDTO saveDTO(ArticleDTO articleDTO) throws Exception;
    Optional<ArticleDTO> findDTOByEan(String ean);
    Iterable<Article> findAll();
    void deleteByEan(String ean);
    void updateStock(String ean, int amount) throws Exception;
    void updateStock(Article article, int amount);
    Iterable<ArticleSER> findAllSERS();
    Optional<ArticleSER> findSERById( long id);
}
